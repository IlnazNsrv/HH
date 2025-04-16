package com.example.hh.main.presentation

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.hh.core.UiState
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.filters.presentation.ButtonsUiState
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import com.example.hh.filters.presentation.FiltersAdapter
import com.example.hh.main.presentation.adapter.UpdateItems
import com.example.hh.main.presentation.adapter.VacanciesAdapter
import com.example.hh.vacancydetails.presentation.screen.NavigateToVacancyDetails

class CustomRecyclerView<T : ItemsUi, U : AbstractViewModel<UiState>> : RecyclerView,
    UpdateItemsRecyclerView<T>, VacanciesUiStateHandler {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var adapter: Adapter<out ViewHolder>

    fun init(
        viewModel: U,
        liveDataWrapper: VacanciesLiveDataWrapper,
        navigate: NavigateToVacancyDetails,
        backStackName: String,
    ) {

        adapter = VacanciesAdapter(clickActions = viewModel, liveDataWrapper = liveDataWrapper)
        setAdapter(adapter)

        viewModel.liveData("tag").observe(findViewTreeLifecycleOwner()!!) { uiState ->
            (uiState as VacanciesUiState).show(this as UpdateItemsRecyclerView<VacancyUi>)
            uiState.navigate(this, navigate, backStackName)
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

    override fun update(list: List<T>) {
        (adapter as UpdateItems<T>).updateItems(list)
    }

    override fun handle(
        navigate: NavigateToVacancyDetails,
        vacancyId: String,
        backStackName: String
    ) {
        navigate.navigateToVacancyDetails(vacancyId, backStackName)
    }
}

interface UpdateItemsRecyclerView<T : ItemsUi> {
    fun update(list: List<T>)
}

interface VacanciesUiStateHandler {
    fun handle(navigate: NavigateToVacancyDetails, vacancyId: String, backStackName: String)
}