package com.example.hh.main.presentation

import com.example.hh.core.UiState

interface VacanciesUiState : UiState {
    fun handle(viewModel: LoadVacancies) = Unit
    fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<VacancyUi>) = Unit

    fun chooseFavorite(vacancyUi: VacancyUi) : VacanciesUiState = this

    object Load : VacanciesUiState {

        private fun readResolve(): Any = Load

        override fun handle(viewModel: LoadVacancies) {
            viewModel.loadVacancies()
        }
    }

    data class Error(private val value: List<VacancyUi.Error>) : VacanciesUiState {
        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<VacancyUi>) {
            updateItemsRecyclerView.update(value)
        }
    }

    data class Progress(private val value: List<VacancyUi.Progress>) : VacanciesUiState {
        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<VacancyUi>) {
            updateItemsRecyclerView.update(value)
        }
    }

    data class EmptyVacancyCache(private val value: List<VacancyUi.EmptyFavoriteCache>) : VacanciesUiState {
        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<VacancyUi>) {
            updateItemsRecyclerView.update(value)
        }
    }

    data class Show(private val list: List<VacancyUi>) : VacanciesUiState {

        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<VacancyUi>) {
            updateItemsRecyclerView.update(list)
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

        private fun readResolve(): Any = Hide

        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<VacancyUi>) {
            updateItemsRecyclerView.update(emptyList())
        }
    }
}