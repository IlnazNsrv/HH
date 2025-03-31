package com.example.hh.favorite.presentation

import androidx.lifecycle.LiveData
import com.example.hh.core.RunAsync
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.favorite.data.FavoriteRepository
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesUiState
import com.example.hh.main.presentation.VacancyUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class FavoriteVacanciesViewModel(
    private val runAsync: RunAsync,
    private val liveDataWrapper: VacanciesLiveDataWrapper,
    private val favoriteRepository: FavoriteRepository,
    private val mapper: LoadVacanciesResult.Mapper
) : AbstractViewModel<VacanciesUiState>() {

    interface Mapper {
        fun map(
            favoriteVacanciesViewModel: FavoriteVacanciesViewModel,
            liveDataWrapper: VacanciesLiveDataWrapper
        )
    }

    fun init(mapper: Mapper) {
        mapper.map(
            this,
            liveDataWrapper
        )
    }

    fun init() {
        runAsync.runAsync(viewModelScope, {
            favoriteRepository.favoriteVacancies()
        }, {
            it.map(mapper)
        })
    }

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun clickFavorite(vacancyUi: VacancyUi) {
        runAsync.runAsync(viewModelScope, {
            favoriteRepository.clickFavorite(vacancyUi)
        }, {
 //           it.map(mapper)
            liveDataWrapper.clickFavorite(vacancyUi)
        })
    }

    override fun clickVacancy(vacancyUi: VacancyUi) {
        liveDataWrapper.clickVacancy(vacancyUi)
    }


    override fun liveData(tag: String): LiveData<VacanciesUiState> {
        return liveDataWrapper.liveData()
    }
}