package com.example.hh.search.presentation

import androidx.lifecycle.ViewModel
import com.example.hh.core.RunAsync
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.main.presentation.VacanciesResultMapper
import com.example.hh.search.data.VacanciesRepository

class SearchViewModel(
    private val runAsync: RunAsync,
    private val repository: VacanciesRepository,
    private val resultMapper: VacanciesResultMapper
) : ViewModel() {


//    override fun loadVacanciesWithQuery(query: String) {
//        resultMapper.mapProgress()
//        runAsync.runAsync(viewModelScope, {
//            repository.vacancies(query)
//        }) {
//            it.map(resultMapper)
//        }
//    }

    private fun navigate(navigate: NavigateToLoadVacancies) = navigate.navigateToLoadVacancies()

    fun clickSearch(text: String) {
        // add fun for navigation and transport text
    }
}

data class VacanciesSearchParams1(
    val searchText: String? = null,
    val vacancySearchField: List<String>? = null,
    val experience: List<String>? = null,
    val employment: List<String>? = null,
    val schedule: List<String>? = null,
    val area: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean? = null
)

class VacanciesSearchParams private constructor(
    val searchText: String,
    val vacancySearchField: List<String>?,
    val experience: List<String>?,
    val employment: List<String>?,
    val schedule: List<String>?,
    val area: String,
    val salary: Int?,
    val onlyWithSalary: Boolean
) {
    class Builder {
        private var searchText: String = ""
        private var vacancySearchField: MutableList<String>? = null
        private var experience: MutableList<String>? = null
        private var employment: MutableList<String>? = null
        private var schedule: MutableList<String>? = null
        private var area: String = "1"
        private var salary: Int? = null
        private var onlyWithSalary: Boolean = false

//        fun setListTest(text: String) = apply {
//            if (testListMutable == null)
//                testListMutable = mutableListOf()
//            testListMutable?.add(text)
//        }


        fun setSearchText(searchText: String) = apply { this.searchText = searchText }

        fun setVacancySearchField(vacancySearchField: String) = apply {
            if (this.vacancySearchField == null)
                this.vacancySearchField = mutableListOf()
            this.vacancySearchField?.add(vacancySearchField)
        }

        fun setExperience(experienceValue: String) = apply {
            if (experience == null)
                experience = mutableListOf()
            experience?.add(experienceValue)
        }

        fun setEmployment(employmentValue: String) = apply {
            if (employment == null)
                employment = mutableListOf()
            employment?.add(employmentValue)
        }

        fun setSchedule(scheduleValue: String) = apply {
            if (schedule == null)
                schedule = mutableListOf()
            schedule?.add(scheduleValue)
        }

        fun setArea(areaValue: String) = apply { this.area = areaValue }

        fun setSalary(salary: Int) = apply { this.salary = salary }

        fun setOnlyWithSalary(onlyWithSalary: Boolean) = apply { this.onlyWithSalary = onlyWithSalary }

        fun build() = VacanciesSearchParams(
            searchText, vacancySearchField, experience, employment, schedule, area, salary, onlyWithSalary
        )

    }
}