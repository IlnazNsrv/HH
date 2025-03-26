package com.example.hh.search.presentation

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.filters.areafilters.presentation.screen.AreaFragment
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.filters.presentation.FiltersViewModel.Mapper
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel
import java.io.Serializable

class SearchViewModel(
    private val searchParams: VacanciesSearchParams.Builder,
    private val chosenFiltersCache: ChosenFiltersCache,
    private val customAreaButtonViewModel: CustomAreaButtonViewModel,
    private val lastTimeButtonClicked: LastTimeButtonClicked
) : ViewModel() {

   // private var searchParams = VacanciesSearchParams.Builder()

    fun inputSearch(text: String, navigate: NavigateToLoadVacancies) {
        searchParams.setSearchText(text)
        searchParams.setArea(chosenFiltersCache.read().area)
        chosenFiltersCache.save(searchParams.build())
        navigate.navigateToLoadVacancies()
    }

    interface Mapper {
        fun map(
            customAreaButtonViewModel: CustomAreaButtonViewModel,
        )
    }

    fun init(mapper: Mapper) {
        mapper.map(
            customAreaButtonViewModel,
        )
    }

    fun openAreaDialogFragment(fragmentManager: FragmentManager) {

        if (lastTimeButtonClicked.timePassed()) {
            val dialogFragment = AreaFragment()
            dialogFragment.show(fragmentManager, AreaFragment.AREA_FRAGMENT_TAG)
        }
    }

}

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
                if (vacancySearchField.isEmpty())
                    return
                else {
                    this.vacancySearchField = mutableListOf(vacancySearchField)
                    return
                }
            }

            if (this.vacancySearchField!!.contains(vacancySearchField)) {
                this.vacancySearchField!!.remove(vacancySearchField)
            } else if (vacancySearchField.isNotEmpty())
                this.vacancySearchField!!.add(vacancySearchField)

            if (this.vacancySearchField!!.isEmpty() || vacancySearchField.isEmpty()) {
                this.vacancySearchField = null
            }
        }

        fun setExperience(experienceValue: String) {
            if (experience == null) {
                if (experienceValue.isEmpty())
                    return
                else {
                    experience = mutableListOf(experienceValue)
                    return
                }
            }
            if (experience!!.contains(experienceValue)) {
                experience!!.remove(experienceValue)
            } else if (experienceValue.isNotEmpty())
                experience!!.add(experienceValue)

            if (experience!!.isEmpty() || experienceValue.isEmpty()) {
                experience = null
            }
        }

        fun setEmployment(employmentValue: String) {
            if (employment == null) {
                if (employmentValue.isEmpty())
                    return
                else {
                    employment = mutableListOf(employmentValue)
                    return
                }
            }

            if (employment!!.contains(employmentValue)) {
                employment!!.remove(employmentValue)
            } else if (employmentValue.isNotEmpty())
                employment!!.add(employmentValue)

            if (employment!!.isEmpty() || employmentValue.isEmpty()) {
                employment = null
            }
        }

        fun setSchedule(scheduleValue: String) {
            if (schedule == null) {
                if (scheduleValue.isEmpty()) {
                    return
                } else {
                    schedule = mutableListOf(scheduleValue)
                    return
                }
            }
            if (schedule!!.contains(scheduleValue)) {
                schedule!!.remove(scheduleValue)
            } else if (scheduleValue.isNotEmpty()) {
                schedule!!.add(scheduleValue)
            }

            if (schedule!!.isEmpty() || scheduleValue.isEmpty()) {
                schedule = null
            }
        }

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