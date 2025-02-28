package com.example.hh.main.presentation

import com.example.hh.core.UiState
import java.io.Serializable

interface VacanciesUiState : UiState, Serializable {
    fun handle(viewModel: LoadVacancies) = Unit
    fun show(updateVacanciesRecyclerView: UpdateVacanciesRecyclerView) = Unit

    fun chooseFavorite(vacancyUi: VacancyUi) : VacanciesUiState = this

    object Load : VacanciesUiState {

        override fun handle(viewModel: LoadVacancies) {
            viewModel.loadVacancies()
        }
    }

    data class Error(private val value: List<VacancyUi.Error>) : VacanciesUiState {
        override fun show(updateVacanciesRecyclerView: UpdateVacanciesRecyclerView) {
            updateVacanciesRecyclerView.update(value)
        }
    }

    data class Progress(private val value: List<VacancyUi.Progress>) : VacanciesUiState {
        override fun show(updateVacanciesRecyclerView: UpdateVacanciesRecyclerView) {
            updateVacanciesRecyclerView.update(value)
        }
    }

    data class Show(private val list: List<VacancyUi>) : VacanciesUiState {

        override fun show(updateVacanciesRecyclerView: UpdateVacanciesRecyclerView) {
            updateVacanciesRecyclerView.update(list)
        }

        override fun chooseFavorite(vacancyUi: VacancyUi): VacanciesUiState {
            val newList = list.toMutableList()
            val item = list.find { it.id() == vacancyUi.id() }!!
            val index = list.indexOf(item)
            newList[index] = item.changeFavoriteChosen()
            return Show(newList)
        }
    }

    object Hide : VacanciesUiState {

        override fun show(updateVacanciesRecyclerView: UpdateVacanciesRecyclerView) {
            updateVacanciesRecyclerView.update(emptyList())
        }
    }
}