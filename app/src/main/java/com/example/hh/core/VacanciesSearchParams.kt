package com.example.hh.core

import java.io.Serializable

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

        private var area: Pair<AreaId, AreaValue>? = null
        private var salary: Int? = null
        private var onlyWithSalary: Boolean = false


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

typealias AreaValue = String

typealias AreaId = String