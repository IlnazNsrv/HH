package com.example.hh.main.presentation

import com.example.hh.core.LiveDataWrapper

interface VacanciesUiState : LiveDataWrapper.UiState {
    fun handle(viewModel: LoadVacancies) = Unit
    fun show(updateVacanciesRecyclerView: UpdateVacanciesRecyclerView) = Unit

    fun chooseFavorite(vacancyUi: VacancyUi) : VacanciesUiState = this

    object Load : VacanciesUiState {

        override fun handle(viewModel: LoadVacancies) {
            viewModel.loadVacancies()
        }
    }
}