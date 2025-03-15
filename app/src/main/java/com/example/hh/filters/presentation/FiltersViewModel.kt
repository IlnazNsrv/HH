package com.example.hh.filters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.search.presentation.VacanciesSearchParams

class FiltersViewModel(
    private val experienceButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
    private val scheduleButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
    private val employmentButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
    private val searchFieldButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
    private val filtersRepository: FiltersRepository,
    private val filtersCreate: CreateFilters
) : ViewModel(), ChooseButton, LiveDataWrapper.GetLiveDataWithTag<ButtonsUiState<FilterButtonUi>> {

    interface Mapper {
        fun map(
            filtersViewModel: FiltersViewModel,
            experienceButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
            scheduleButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
            employmentButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
            searchFieldButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
        )
    }

    fun init() {
        experienceButtonsLiveDataWrapper.update(ButtonsUiState.Show(filtersCreate.initExperience()))
        scheduleButtonsLiveDataWrapper.update(ButtonsUiState.Show(filtersCreate.initSchedule()))
        employmentButtonLiveDataWrapper.update(ButtonsUiState.Show(filtersCreate.initEmployment()))
        searchFieldButtonLiveDataWrapper.update(ButtonsUiState.Show(filtersCreate.initSearchField()))
    }

    fun init(mapper: Mapper) {
        mapper.map(
            this,
            experienceButtonsLiveDataWrapper,
            scheduleButtonsLiveDataWrapper,
            employmentButtonLiveDataWrapper,
            searchFieldButtonLiveDataWrapper
        )
    }

    private val searchParams = VacanciesSearchParams.Builder()

    fun clickSearchVacanciesButton(navigate: NavigateToLoadVacancies) {
        filtersRepository.saveParams(searchParams.build())
        navigate(navigate)
    }

    override fun choose(buttonUi: FilterButtonUi) = with(CreateFilters) {
        when (buttonUi.listId()) {
            EXPERIENCE_TAG -> {
                experienceButtonsLiveDataWrapper.clickButton(buttonUi)
                searchParams.setExperience(buttonUi.query())
            }

            SCHEDULE_TAG -> {
                scheduleButtonsLiveDataWrapper.clickButton(buttonUi)
                searchParams.setSchedule(buttonUi.query())
            }

            SEARCH_FIELD_TAG -> {
                searchFieldButtonLiveDataWrapper.clickButton(buttonUi)
                searchParams.setVacancySearchField(buttonUi.query())
            }

            EMPLOYMENT_TAG -> {
                employmentButtonLiveDataWrapper.clickButton(buttonUi)
                searchParams.setEmployment(buttonUi.query())
            }

            else -> throw IllegalArgumentException("Unknown list ID: ${buttonUi.listId()}")
        }
    }

    private fun navigate(navigate: NavigateToLoadVacancies) = navigate.navigateToLoadVacancies()

    override fun liveData(tag: String): LiveData<ButtonsUiState<FilterButtonUi>> {
        return when (tag) {
            CreateFilters.EXPERIENCE_TAG -> experienceButtonsLiveDataWrapper.liveData()
            CreateFilters.SCHEDULE_TAG -> scheduleButtonsLiveDataWrapper.liveData()
            CreateFilters.SEARCH_FIELD_TAG -> searchFieldButtonLiveDataWrapper.liveData()
            CreateFilters.EMPLOYMENT_TAG -> employmentButtonLiveDataWrapper.liveData()

            else -> throw IllegalArgumentException("Unknown $tag")
        }
    }


}