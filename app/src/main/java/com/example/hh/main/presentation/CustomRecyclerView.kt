package com.example.hh.main.presentation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.hh.core.UiState
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.filters.presentation.ButtonsUiState
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import com.example.hh.filters.presentation.FiltersAdapter
import com.example.hh.main.data.BundleWrapper

class CustomRecyclerView<T : ItemsUi, V : UiState, U : AbstractViewModel<UiState>> : RecyclerView, UpdateItemsRecyclerView<T> {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var adapter: Adapter<out ViewHolder>


    fun init(viewModel: U, liveDataWrapper: VacanciesLiveDataWrapper, tag: String = "") {

        adapter = VacanciesAdapter(clickActions = viewModel, liveDataWrapper = liveDataWrapper)
        setAdapter(adapter)

        viewModel.liveData(tag).observe(findViewTreeLifecycleOwner()!!) {
            (it as VacanciesUiState).handle(viewModel as LoadVacancies)
            it.show(this as UpdateItemsRecyclerView<VacancyUi>)

        }
    }

    fun initButtons(
        viewModel: U,
        liveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
        type: String
    ) {

        adapter = FiltersAdapter(clickListener = viewModel, liveDataWrapper = liveDataWrapper)
        setAdapter(adapter)

        viewModel.liveData(type).observe(findViewTreeLifecycleOwner()!!) {
            (it as ButtonsUiState<T>).show(this)
        }
    }

    fun save(bundle: Bundle) {
        (adapter as SaveItems<V>).save(BundleWrapper.Base(bundle))
    }

    fun restore(bundle: Bundle) {
        (adapter as SaveItems<V>).restore(BundleWrapper.Base(bundle))
    }

    override fun update(list: List<T>) {
        (adapter as UpdateItems<T>).updateItems(list)
    }
}

interface UpdateItemsRecyclerView<T : ItemsUi> {

    fun update(list: List<T>)
    //fun update(list: List<FilterButtonUi>)
}