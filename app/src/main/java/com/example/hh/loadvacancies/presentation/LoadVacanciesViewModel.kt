package com.example.hh.loadvacancies.presentation

import com.example.hh.R
import com.example.hh.core.ClearViewModel
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.ProvideResource
import com.example.hh.core.RunAsync
import com.example.hh.core.VacanciesSearchParams
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.loadvacancies.data.VacanciesRepository
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesUiState
import com.example.hh.main.presentation.VacancyUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class LoadVacanciesViewModel(
    provideResource: ProvideResource,
    private val lastTimeButtonClicked: LastTimeButtonClicked,
    private val clearViewModel: ClearViewModel,
    private val filtersCache: ChosenFiltersCache,
    private val liveDataWrapper: VacanciesLiveDataWrapper,
    private val runAsync: RunAsync,
    private val repository: VacanciesRepository,
    private val mapper: LoadVacanciesResult.Mapper
) : AbstractViewModel<VacanciesUiState>() {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val filterItems = provideResource.array(R.array.simple_items)

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

    private var cachedSelectedItem: String = filterItems[0]

    fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            searchWithFilters()
        else
            clickFilters(cachedSelectedItem)
    }

    fun clearVacancies() {
        runAsync.runAsync(viewModelScope, {
            repository.clearVacancies()
        }) {
        }
        clearViewModel.clear(LoadVacanciesViewModel::class.java.simpleName)
    }

    override fun retry() {
        searchWithFilters()
    }

    private fun vacanciesFromDatabase() {
        mapper.mapProgress()
        runAsync.runAsync(viewModelScope, {
            repository.vacanciesFromCache()
        }) {
            it.map(mapper)
        }
    }

    private fun vacanciesWithDecreaseFilter() {
        mapper.mapProgress()
        runAsync.runAsync(viewModelScope, {
            repository.vacanciesWithDecreaseFilter()
        }) {
            it.map(mapper)
        }
    }

    private fun vacanciesWithIncreaseFilter() {
        mapper.mapProgress()
        runAsync.runAsync(viewModelScope, {
            repository.vacanciesWithIncreaseFilters()
        }) {
            it.map(mapper)
        }
    }

    private fun searchWithFilters() {
        val cacheFilters = filtersCache.read()
        loadVacanciesWithQuery(cacheFilters)
    }

    private fun loadVacanciesWithQuery(vacanciesSearchParams: VacanciesSearchParams) {
        mapper.mapProgress()
        runAsync.runAsync(viewModelScope, {
           repository.vacanciesWithCache(vacanciesSearchParams)
        }) {
            it.map(mapper)
        }
    }

    override fun clickVacancy(vacancyUi: VacancyUi) {
        liveDataWrapper.clickVacancy(vacancyUi)
    }

    override fun liveData(tag: String) = liveDataWrapper.liveData()

    override fun clickFavorite(vacancyUi: VacancyUi) {
        if (lastTimeButtonClicked.timePassed()) {
            runAsync.runAsync(viewModelScope, {
                repository.updateFavoriteStatus(vacancyUi)
            }, {
                liveDataWrapper.clickFavorite(vacancyUi)
            })
        }
    }

    fun clickFilters(item: String) {
        cachedSelectedItem = item
        when(item) {
            filterItems[0] -> vacanciesFromDatabase()
            filterItems[1] -> vacanciesWithDecreaseFilter()
            filterItems[2] -> vacanciesWithIncreaseFilter()
        }
    }
}