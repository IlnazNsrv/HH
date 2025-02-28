package com.example.hh.main.buttonactions

import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesUiState
import com.example.hh.views.button.HandleButtonAction
import kotlinx.coroutines.CoroutineScope

class RetryButtonAction(private val vacanciesLiveDataWrapper: VacanciesLiveDataWrapper) :
    HandleButtonAction {

    override fun handle(viewModelScope: CoroutineScope) {
        vacanciesLiveDataWrapper.update(VacanciesUiState.Load)
    }
}