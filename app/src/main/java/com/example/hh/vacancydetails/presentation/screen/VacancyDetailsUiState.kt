package com.example.hh.vacancydetails.presentation.screen

import com.example.hh.core.UiState
import com.example.hh.databinding.FragmentVacancyDetailsBinding
import com.example.hh.vacancydetails.presentation.VacancyDetailsUi

interface VacancyDetailsUiState : UiState {

    fun show(binding: FragmentVacancyDetailsBinding) = Unit

    data class Show(val vacancyDetailsUi: VacancyDetailsUi) : VacancyDetailsUiState {

        override fun show(binding: FragmentVacancyDetailsBinding) {
            vacancyDetailsUi.show(binding)
        }
    }

    data class Error(val vacancyErrorUi: VacancyDetailsUi.Error) : VacancyDetailsUiState {

        override fun show(binding: FragmentVacancyDetailsBinding) {
            vacancyErrorUi.show(binding)
        }
    }
}