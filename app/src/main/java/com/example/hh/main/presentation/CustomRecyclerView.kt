package com.example.hh.main.presentation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.example.hh.core.UiState
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import com.example.hh.filters.presentation.FiltersAdapter
import com.example.hh.filters.presentation.FiltersViewModel
import com.example.hh.loadvacancies.presentation.LoadVacanciesViewModel
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


    fun init(viewModel: U, liveDataWrapper: VacanciesLiveDataWrapper) {

        adapter = VacanciesAdapter(clickActions = viewModel, liveDataWrapper = liveDataWrapper)
        setAdapter(adapter)

        viewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            (it as VacanciesUiState).handle(viewModel as LoadVacancies)
            it.show(this as UpdateItemsRecyclerView<VacancyUi>)
        }
    }

    fun initSearchFragment(
        viewModel: LoadVacanciesViewModel,
        liveDataWrapper: VacanciesLiveDataWrapper
    ) {
        adapter = VacanciesAdapter(clickActions = viewModel, liveDataWrapper = liveDataWrapper)
        setAdapter(adapter)

        viewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            // it.handle(viewModel)
            it.show(this as UpdateItemsRecyclerView<VacancyUi>)
        }
    }

    fun initButtons(
        viewModel: FiltersViewModel,
        liveDataWrapper: FilterButtonsLiveDataWrapper<T>,
        type: String
    ) {
        adapter = FiltersAdapter(clickListener = viewModel)
        setAdapter(adapter)

        val animator: ItemAnimator = object : DefaultItemAnimator() {
            override fun animateRemove(holder: ViewHolder): Boolean {
                return false // Отключаем анимацию удаления
            }

            override fun animateAdd(holder: ViewHolder): Boolean {
                return false // Отключаем анимацию добавления
            }
        }

        this.itemAnimator = animator

        viewModel.liveData(type).observe(findViewTreeLifecycleOwner()!!) {
            it.show(this as UpdateItemsRecyclerView<FilterButtonUi>)
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