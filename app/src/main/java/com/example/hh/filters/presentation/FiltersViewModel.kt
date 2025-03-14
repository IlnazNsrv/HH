package com.example.hh.filters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.search.presentation.VacanciesSearchParams

class FiltersViewModel(
    private val experienceButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper,
    private val scheduleButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper,
    private val employmentButtonLiveDataWrapper: FilterButtonsLiveDataWrapper,
    private val searchFieldButtonLiveDataWrapper: FilterButtonsLiveDataWrapper,
    private val filtersRepository: FiltersRepository,
    private val filtersCreate: CreateFilters
) : ViewModel(), ChooseButton, LiveDataWrapper.GetLiveDataWithTag<ButtonsUiState> {

    interface Mapper {
        fun map(
            filtersViewModel: FiltersViewModel,
            experienceButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper,
            scheduleButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper,
            employmentButtonLiveDataWrapper: FilterButtonsLiveDataWrapper,
            searchFieldButtonLiveDataWrapper: FilterButtonsLiveDataWrapper,
        )
    }

    fun init() {
        experienceFilters = filtersCreate.initExperience()
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

    private var experienceFilters = listOf<FilterButtonUi>()

    private val searchParams = VacanciesSearchParams.Builder()

    fun clickExperienceButton(text: String) {
        searchParams.setSearchText(text)
    }

    fun clickSearchVacanciesButton(navigate: NavigateToLoadVacancies) {
        filtersRepository.saveParams(searchParams.build())
        navigate(navigate)
    }

    override fun choose(buttonUi: FilterButtonUi) {
        when (buttonUi.listId()) {
            CreateFilters.EXPERIENCE_TAG -> {
                experienceButtonsLiveDataWrapper.clickButton(buttonUi)
                searchParams.setExperience(buttonUi.query())
            }

            CreateFilters.SCHEDULE_TAG -> {
                scheduleButtonsLiveDataWrapper.clickButton(buttonUi)
                searchParams.setSchedule(buttonUi.query())
            }

            CreateFilters.SEARCH_FIELD_TAG -> {
                searchFieldButtonLiveDataWrapper.clickButton(buttonUi)
                searchParams.setVacancySearchField(buttonUi.query())
            }

            CreateFilters.EMPLOYMENT_TAG -> {
                employmentButtonLiveDataWrapper.clickButton(buttonUi)
                searchParams.setEmployment(buttonUi.query())
            }

            else -> throw IllegalArgumentException("Unknown list ID: ${buttonUi.listId()}")
        }
    }

    private fun navigate(navigate: NavigateToLoadVacancies) = navigate.navigateToLoadVacancies()

    fun liveDataExperience(): LiveData<ButtonsUiState> {
        return experienceButtonsLiveDataWrapper.liveData()
    }

    fun liveDataSchedule(): LiveData<ButtonsUiState> {
        return scheduleButtonsLiveDataWrapper.liveData()
    }

    fun liveDataSearchField(): LiveData<ButtonsUiState> {
        return searchFieldButtonLiveDataWrapper.liveData()
    }

    fun liveDataEmployment(): LiveData<ButtonsUiState> {
        return employmentButtonLiveDataWrapper.liveData()
    }

    override fun liveData(tag: String): LiveData<ButtonsUiState> {
        return when (tag) {
            CreateFilters.EXPERIENCE_TAG -> experienceButtonsLiveDataWrapper.liveData()
            CreateFilters.SCHEDULE_TAG -> scheduleButtonsLiveDataWrapper.liveData()
            CreateFilters.SEARCH_FIELD_TAG -> searchFieldButtonLiveDataWrapper.liveData()
            CreateFilters.EMPLOYMENT_TAG -> employmentButtonLiveDataWrapper.liveData()

            else -> throw IllegalArgumentException("Unknown $tag")
        }
    }


}