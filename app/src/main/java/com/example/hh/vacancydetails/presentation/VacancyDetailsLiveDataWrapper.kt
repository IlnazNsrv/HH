package com.example.hh.vacancydetails.presentation

import com.example.hh.core.LiveDataWrapper
import com.example.hh.vacancydetails.presentation.screen.VacancyDetailsUiState

interface VacancyDetailsLiveDataWrapper : LiveDataWrapper.Mutable<VacancyDetailsUiState> {

    class Base : LiveDataWrapper.Abstract<VacancyDetailsUiState>(), VacancyDetailsLiveDataWrapper
}