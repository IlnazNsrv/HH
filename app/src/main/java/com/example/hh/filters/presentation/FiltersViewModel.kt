package com.example.hh.filters.presentation

import androidx.lifecycle.LiveData
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.main.data.BundleWrapper
import com.example.hh.search.presentation.VacanciesSearchParams
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel

class FiltersViewModel(
    private val searchParams: VacanciesSearchParams.Builder,
    private val experienceButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
    private val scheduleButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
    private val employmentButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
    private val searchFieldButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
    private val filtersRepository: FiltersRepository,
    private val filtersCreate: CreateFilters,
    private val customAreaButtonViewModel: CustomAreaButtonViewModel,

) : AbstractViewModel<ButtonsUiState<FilterButtonUi>>() {

    private var cachedQuery: String = ""
    private var cachedSalary: Int? = null


    interface Mapper {
        fun map(
            filtersViewModel: FiltersViewModel,
            experienceButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
            scheduleButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
            employmentButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
            searchFieldButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
            customAreaButtonViewModel: CustomAreaButtonViewModel,

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
            searchFieldButtonLiveDataWrapper,
            customAreaButtonViewModel,

        )
    }

    private fun buildSearchParams() {
        searchParams.setSearchText(cachedQuery)
        searchParams.setSalary(cachedSalary)
        searchParams.setArea(filtersRepository.restoreArea())
        filtersRepository.saveParams(searchParams.build())
    }

    fun searchVacancies(text: String, number: Int?, navigate: NavigateToLoadVacancies) {
        cachedQuery = text
        cachedSalary = number
        buildSearchParams()
        navigate(navigate)
    }

    fun salaryInput(value: Int?) {
       searchParams.setSalary(value)
    }

    fun clickSearchVacanciesButton(navigate: NavigateToLoadVacancies) {
        filtersRepository.saveParams(searchParams.build())
        navigate(navigate)
    }

    fun switchSalaryParams(isChecked: Boolean) {
        searchParams.setOnlyWithSalary(isChecked)
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

    fun save(bundleWrapper: BundleWrapper.Save<ButtonsUiState<FilterButtonUi>>) {
        experienceButtonsLiveDataWrapper.save(bundleWrapper.saveWithKey(CreateFilters.EXPERIENCE_TAG))
        employmentButtonLiveDataWrapper.save(bundleWrapper.saveWithKey(CreateFilters.EMPLOYMENT_TAG))
        scheduleButtonsLiveDataWrapper.save(bundleWrapper.saveWithKey(CreateFilters.SCHEDULE_TAG))
        searchFieldButtonLiveDataWrapper.save(bundleWrapper.saveWithKey(CreateFilters.SEARCH_FIELD_TAG))
    }

    fun restore(bundleWrapper: BundleWrapper.Restore<ButtonsUiState<FilterButtonUi>>) {
        employmentButtonLiveDataWrapper.update(bundleWrapper.restoreWithKey(CreateFilters.EMPLOYMENT_TAG).restore())
        experienceButtonsLiveDataWrapper.update(bundleWrapper.restoreWithKey(CreateFilters.EXPERIENCE_TAG).restore())
        scheduleButtonsLiveDataWrapper.update(bundleWrapper.restoreWithKey(CreateFilters.SCHEDULE_TAG).restore())
        searchFieldButtonLiveDataWrapper.update(bundleWrapper.restoreWithKey(CreateFilters.SEARCH_FIELD_TAG).restore())
    }
}