package com.example.hh.filters.areafilters.presentation

import androidx.lifecycle.LiveData
import com.example.hh.core.RunAsync
import com.example.hh.core.VacanciesSearchParams
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.filters.areafilters.data.AreasRepository
import com.example.hh.filters.areafilters.data.LoadAreasResult
import com.example.hh.filters.presentation.ButtonsUiState
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import com.example.hh.views.button.CustomButtonLiveDataWrapper
import com.example.hh.views.button.areabutton.CustomAreaButtonUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class AreaViewModel(
    private val customAreaButtonLiveDataWrapper: CustomButtonLiveDataWrapper<CustomAreaButtonUiState>,
    private val buttonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
    private val runAsync: RunAsync,
    private val repository: AreasRepository,
    private val mapper: LoadAreasResult.Mapper
) : AbstractViewModel<ButtonsUiState<FilterButtonUi>>() {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val searchParams = VacanciesSearchParams.Builder()

    interface Mapper {
        fun map(
            viewModel: AreaViewModel,
            buttonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>
        )
    }

    fun map(mapper: Mapper) {
        mapper.map(this, buttonLiveDataWrapper)
    }

    fun loadAreas(text: String) {
        runAsync.runAsync(viewModelScope, {
            repository.areas(text)
        }) {
            it.map(mapper)
        }
    }

    fun saveArea() {
        repository.saveArea(searchParams.build())
        customAreaButtonLiveDataWrapper.update(CustomAreaButtonUiState.Show(repository.readArea()))
    }

    override fun choose(buttonUi: FilterButtonUi) {
        buttonLiveDataWrapper.clickButton(buttonUi)
        if (buttonUi.chosen()) {
            searchParams.setArea(null)
        } else {
            searchParams.setArea(areaValue = Pair(buttonUi.id(), buttonUi.query()))
        }
    }

    override fun liveData(tag: String): LiveData<ButtonsUiState<FilterButtonUi>> {
        return buttonLiveDataWrapper.liveData()
    }
}