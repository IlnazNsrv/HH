package com.example.hh.loadvacancies.presentation

import android.util.Log
import com.example.hh.core.ClearViewModel
import com.example.hh.core.RunAsync
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.loadvacancies.data.VacanciesRepository
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesUiState
import com.example.hh.main.presentation.VacancyUi
import com.example.hh.search.presentation.VacanciesSearchParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class LoadVacanciesViewModel(
    private val clearViewModel: ClearViewModel,
    private val filtersCache: ChosenFiltersCache,
    private val liveDataWrapper: VacanciesLiveDataWrapper,
    private val runAsync: RunAsync,
    private val repository: VacanciesRepository,
    private val mapper: LoadVacanciesResult.Mapper
) : AbstractViewModel<VacanciesUiState>() {

    interface Mapper {
        fun map(
            loadVacanciesViewModel: LoadVacanciesViewModel,
            liveDataWrapper: VacanciesLiveDataWrapper
        )
    }

    fun init(mapper: Mapper) {
        mapper.map(
            this,
            liveDataWrapper
        )
    }

    fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            searchWithFilters()
        else
            vacanciesFromDatabase()
    }

    fun clearVacancies() {
        runAsync.runAsync(viewModelScope, {
            repository.clearVacancies()
        }) {
        }
        clearViewModel.clear(LoadVacanciesViewModel::class.java.simpleName)
        Log.d("inz", "repository cleared")
    }

    override fun retry() {
        searchWithFilters()
    }

    private fun vacanciesFromDatabase() {
        runAsync.runAsync(viewModelScope, {
            repository.vacanciesFromCache()
        }) {
            it.map(mapper)
        }
    }

    private fun searchWithFilters() {
        val cacheFilters = filtersCache.read()
        loadVacanciesWithQuery(cacheFilters)
    }

    fun inputSearch(text: String) {
        val cacheFilters = filtersCache.read()
        searchParams.setSearchText(text)
        loadVacanciesWithQuery(searchParams.build())
    }

    private var searchParams = VacanciesSearchParams.Builder()

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)


    private fun loadVacanciesWithQuery(vacanciesSearchParams: VacanciesSearchParams) {
        mapper.mapProgress()
        runAsync.runAsync(viewModelScope, {
           repository.vacanciesWithCache(vacanciesSearchParams)
        }) {
            it.map(mapper)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("inz", "Load Vacancies VM cleared")
    }

    override fun liveData(tag: String) = liveDataWrapper.liveData()

    override fun clickFavorite(vacancyUi: VacancyUi) {
        runAsync.runAsync(viewModelScope, {
            repository.updateFavoriteStatus(vacancyUi)
        }, {
            liveDataWrapper.clickFavorite(vacancyUi)
        })

    }

}