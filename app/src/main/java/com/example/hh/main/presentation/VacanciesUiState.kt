package com.example.hh.main.presentation

import com.example.hh.core.UiState
import com.example.hh.vacancydetails.presentation.screen.NavigateToVacancyDetails

interface VacanciesUiState : UiState {
    fun handle(viewModel: LoadVacancies) = Unit
    fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<VacancyUi>) = Unit

    fun clickVacancy(vacancyId: String): VacanciesUiState = this

    fun navigatedToVacancy() = Unit

    fun chooseFavorite(vacancyUi: VacancyUi): VacanciesUiState = this

    fun navigate(
        vacanciesUiStateHandler: VacanciesUiStateHandler,
        navigate: NavigateToVacancyDetails,
        backStackName: String
    ) = Unit

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

    data class EmptyVacancyCache(private val value: List<VacancyUi.EmptyFavoriteCache>) :
        VacanciesUiState {
        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<VacancyUi>) {
            updateItemsRecyclerView.update(value)
        }
    }

    data class Show(
        private val list: List<VacancyUi>,
        private var navigateToVacancyWithId: String? = null
    ) : VacanciesUiState {

        override fun navigate(
            vacanciesUiStateHandler: VacanciesUiStateHandler,
            navigate: NavigateToVacancyDetails,
            backStackName: String
        ) {
            navigateToVacancyWithId?.let {
                vacanciesUiStateHandler.handle(navigate, navigateToVacancyWithId!!, backStackName)
                navigatedToVacancy()
            }
        }

        override fun clickVacancy(vacancyId: String): VacanciesUiState {
            navigateToVacancyWithId = vacancyId
            return this
        }

        override fun navigatedToVacancy() {
            navigateToVacancyWithId = null
        }

        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<VacancyUi>) {
            updateItemsRecyclerView.update(list)
        }

        override fun chooseFavorite(vacancyUi: VacancyUi): VacanciesUiState {
            val newList = list.toMutableList()
            val item = list.find { it.id() == vacancyUi.id() }!!
            val index = list.indexOf(item)
            newList[index] = item.changeFavoriteChosen()
            return Show(newList, navigateToVacancyWithId)
        }
    }
}