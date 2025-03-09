package com.example.hh.search.buttonactions

import com.example.hh.core.RunAsync
import com.example.hh.main.presentation.VacanciesResultMapper
import com.example.hh.search.data.VacanciesRepository
import com.example.hh.views.input.HandleInputAction
import kotlinx.coroutines.CoroutineScope

class InputSearchAction(
    private val runAsync: RunAsync,
    private val repository: VacanciesRepository,
    private val resultMapper: VacanciesResultMapper

) : HandleInputAction {

    override fun handle(text: String, viewModelScope: CoroutineScope) {
        resultMapper.mapProgress()
        runAsync.runAsync(viewModelScope, {
            repository.vacancies()
        }) {
            it.map(resultMapper)
        }
    }
}