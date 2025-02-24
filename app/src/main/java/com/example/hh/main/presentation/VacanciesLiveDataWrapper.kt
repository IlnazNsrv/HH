package com.example.hh.main.presentation

import com.example.hh.core.LiveDataWrapper

interface VacanciesLiveDataWrapper : LiveDataWrapper.Mutable<VacanciesUiState> {

    fun choose(vacancyUi: VacancyUi)

    class Base : LiveDataWrapper.Abstract<VacanciesUiState>(), VacanciesLiveDataWrapper {

        override fun choose(vacancyUi: VacancyUi) {
            TODO("Not yet implemented")
        }
    }
}