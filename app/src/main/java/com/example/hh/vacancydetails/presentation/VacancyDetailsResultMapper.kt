package com.example.hh.vacancydetails.presentation

import com.example.hh.vacancydetails.data.LoadVacancyDetailsResult
import com.example.hh.vacancydetails.presentation.screen.VacancyDetailsUiState

class VacancyDetailsResultMapper(
    private val vacancyDetailsLiveDataWrapper: VacancyDetailsLiveDataWrapper
) : LoadVacancyDetailsResult.Mapper {

    override fun mapSuccess(vacancyDetailsUi: VacancyDetailsUi) {
        vacancyDetailsLiveDataWrapper.update(VacancyDetailsUiState.Show(vacancyDetailsUi))
    }

    override fun mapError(vacancyUi: VacancyDetailsUi.Error) {
        vacancyDetailsLiveDataWrapper.update(VacancyDetailsUiState.Error(vacancyUi))
    }
}