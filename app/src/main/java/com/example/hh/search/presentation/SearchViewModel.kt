package com.example.hh.search.presentation

import androidx.lifecycle.ViewModel
import com.example.hh.core.RunAsync
import com.example.hh.loadvacancies.data.VacanciesRepository
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.main.presentation.VacanciesResultMapper
import java.io.Serializable

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
typealias AreaId = String
typealias AreaValue = String

class VacanciesSearchParams private constructor(
    val searchText: String,
    val vacancySearchField: List<String>?,
    val experience: List<String>?,
    val employment: List<String>?,
    val schedule: List<String>?,
    val area: Pair<AreaId, AreaValue>?,
    val salary: Int?,
    val onlyWithSalary: Boolean
) : Serializable {
    class Builder {
        private var searchText: String = ""
        private var vacancySearchField: MutableList<String>? = null
        private var experience: MutableList<String>? = null
        private var employment: MutableList<String>? = null
        private var schedule: MutableList<String>? = null

        // private var area: String? = null
        private var area: Pair<AreaId, AreaValue>? = null
        private var salary: Int? = null
        private var onlyWithSalary: Boolean = false

//        fun setListTest(text: String) = apply {
//            if (testListMutable == null)
//                testListMutable = mutableListOf()
//            testListMutable?.add(text)
//        }


        fun setSearchText(searchText: String) = apply { this.searchText = searchText }

        fun setVacancySearchField(vacancySearchField: String) {
            if (this.vacancySearchField == null) {
                this.vacancySearchField = mutableListOf(vacancySearchField)
                return
            }

            if (this.vacancySearchField!!.contains(vacancySearchField)) {
                this.vacancySearchField!!.remove(vacancySearchField)
            } else
                this.vacancySearchField!!.add(vacancySearchField)

            if (this.vacancySearchField!!.isEmpty()) {
                this.vacancySearchField = null
            }
        }

        fun setExperience(experienceValue: String) {
            if (experience == null) {
                experience = mutableListOf(experienceValue)
                return
            }
            if (experience!!.contains(experienceValue)) {
                experience!!.remove(experienceValue)
            } else
                experience!!.add(experienceValue)

            if (experience!!.isEmpty()) {
                experience = null
            }
        }

        fun setEmployment(employmentValue: String) {
            if (employment == null) {
                employment = mutableListOf(employmentValue)
                return
            }

            if (employment!!.contains(employmentValue)) {
                employment!!.remove(employmentValue)
            } else
                employment!!.add(employmentValue)

            if (employment!!.isEmpty()) {
                employment = null
            }
        }

        fun setSchedule(scheduleValue: String) {
            if (schedule == null) {
                schedule = mutableListOf()
                return
            }
            if (schedule!!.contains(scheduleValue)) {
                schedule!!.remove(scheduleValue)
            } else
                schedule!!.add(scheduleValue)

            if (schedule!!.isEmpty()) {
                schedule = null
            }
        }

       // fun setArea(areaValue: String?) = apply { this.area = areaValue }
        fun setArea(areaValue: Pair<AreaId, AreaValue>?) = apply { this.area = areaValue }

        fun setSalary(salary: Int?) = apply { this.salary = salary }

        fun setOnlyWithSalary(onlyWithSalary: Boolean) =
            apply { this.onlyWithSalary = onlyWithSalary }

        fun build() = VacanciesSearchParams(
            searchText,
            vacancySearchField,
            experience,
            employment,
            schedule,
            area,
            salary,
            onlyWithSalary
        )

    }
}