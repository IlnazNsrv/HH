package com.example.hh.loadvacancies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import com.example.hh.core.RunAsync
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.presentation.ClickActions
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesUiState
import com.example.hh.main.presentation.VacancyUi
import com.example.hh.search.data.VacanciesRepository
import com.example.hh.search.presentation.VacanciesSearchParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class LoadVacanciesViewModel(
    private val filtersCache: ChosenFiltersCache,
    private val liveDataWrapper: VacanciesLiveDataWrapper,
    private val runAsync: RunAsync,
    private val repository: VacanciesRepository,
    private val mapper: LoadVacanciesResult.Mapper
) : ViewModel(), LiveDataWrapper.GetLiveData<VacanciesUiState>, ClickActions {

    interface Mapper {
        fun map(
            //customInputViewModel: CustomInputViewModel,
            loadVacanciesViewModel: LoadVacanciesViewModel,
            liveDataWrapper: VacanciesLiveDataWrapper
        )
    }

    fun init(mapper: Mapper) {
        mapper.map(
            this,
            //searchViewModule.inputViewModel,
            liveDataWrapper
        )
    }

    fun searchWithFilters() {
        val cacheFilters = filtersCache.read()
        loadVacanciesWithQuery(cacheFilters)
    }

    fun inputSearch(text: String) {
        val cacheFilters = filtersCache.read()
        searchParams.setSearchText(text)
        loadVacanciesWithQuery(searchParams.build())
    }

    fun chooseFilterButton(text: String) {
    }

   private var searchParams = VacanciesSearchParams.Builder()

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private fun loadVacanciesWithQuery(vacanciesSearchParams: VacanciesSearchParams) {
        mapper.mapProgress()
        runAsync.runAsync(viewModelScope, {
            repository.vacancies(vacanciesSearchParams)
        }) {
            it.map(mapper)
        }
    }

    override fun liveData(): LiveData<VacanciesUiState> = liveDataWrapper.liveData()

    override fun clickFavorite(vacancyUi: VacancyUi) {
        liveDataWrapper.clickFavorite(vacancyUi)
    }


}