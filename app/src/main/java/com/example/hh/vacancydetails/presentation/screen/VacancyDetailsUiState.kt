package com.example.hh.vacancydetails.presentation.screen

import com.example.hh.core.UiState
import com.example.hh.databinding.FragmentVacancyDetailsBinding
import com.example.hh.vacancydetails.presentation.VacancyDetailsUi

interface VacancyDetailsUiState : UiState {

    fun show(binding: FragmentVacancyDetailsBinding) = Unit
    fun clickFavorite(): VacancyDetailsUiState = this
    fun save(position: Int) = Unit

    data class Show(
        private val vacancyDetailsUi: VacancyDetailsUi,
        var scrollViewPosition: Int = 0
    ) : VacancyDetailsUiState {

        override fun show(binding: FragmentVacancyDetailsBinding) {
            vacancyDetailsUi.show(binding)
        }

        override fun clickFavorite(): VacancyDetailsUiState {
            vacancyDetailsUi.changeFavoriteChosen()
            return Show(vacancyDetailsUi)
        }

        override fun save(position: Int) {
            scrollViewPosition = position
        }
    }

    data class Progress(val vacancyProgressUi: VacancyDetailsUi.Progress) : VacancyDetailsUiState {

        override fun show(binding: FragmentVacancyDetailsBinding) {
            vacancyProgressUi.show(binding)
        }
    }

    data class Error(val vacancyErrorUi: VacancyDetailsUi.Error) : VacancyDetailsUiState {

        override fun show(binding: FragmentVacancyDetailsBinding) {
            vacancyErrorUi.show(binding)
        }
    }
}