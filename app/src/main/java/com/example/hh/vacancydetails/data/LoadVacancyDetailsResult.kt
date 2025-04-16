package com.example.hh.vacancydetails.data

import com.example.hh.vacancydetails.presentation.VacancyDetailsUi

interface LoadVacancyDetailsResult {

    fun map(mapper: Mapper)

    interface Mapper {
        fun mapSuccess(vacancyDetailsUi: VacancyDetailsUi)
        fun mapError(vacancyUiError: VacancyDetailsUi.Error)
    }

    data class Success(private val vacancyUi: VacancyDetailsUi) : LoadVacancyDetailsResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(vacancyUi)
        }

        fun vacancyDetails() = vacancyUi
    }

    data class Error(private val vacancyUi: VacancyDetailsUi.Error) : LoadVacancyDetailsResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(vacancyUi)
        }

        fun error() = vacancyUi
    }
}