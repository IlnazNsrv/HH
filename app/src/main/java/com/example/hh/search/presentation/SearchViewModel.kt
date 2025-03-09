package com.example.hh.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import com.example.hh.core.RunAsync
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesUiState

class SearchViewModel(
    private val vacanciesRepository: VacanciesRepository,
    private val runAsync: RunAsync,
    private val vacanciesLiveDataWrapper: VacanciesLiveDataWrapper
) : ViewModel(), LiveDataWrapper.GetLiveData<VacanciesUiState>, ChooseCityAnd {

    override fun liveData(): LiveData<VacanciesUiState> {
        vacanciesRepository.save()
    }

}