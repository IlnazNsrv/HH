package com.example.hh.main.presentation

import com.example.hh.main.data.LoadVacanciesResult

class VacanciesResultMapper(
    private val vacanciesLiveDataWrapper: VacanciesLiveDataWrapper
) : LoadVacanciesResult.Mapper {

    override fun mapSuccess(list: List<VacancyUi>) {
        vacanciesLiveDataWrapper.update(VacanciesUiState.Show(list))
    }

    override fun mapError(message: String) {
        val list = message.map { VacancyUi.Error(message) }
        vacanciesLiveDataWrapper.update(VacanciesUiState.Error(list))
    }

    override fun mapProgress() {
        vacanciesLiveDataWrapper.update(VacanciesUiState.Progress(listOf(VacancyUi.Progress)))
    }
}