package com.example.hh.main.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.hh.core.RunAsync
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.data.MainVacanciesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class VacanciesViewModel(
    private val mainVacanciesRepository: MainVacanciesRepository,
    private val runAsync: RunAsync,
    private val mapper: LoadVacanciesResult.Mapper,
    private val liveDataWrapper: VacanciesLiveDataWrapper,
) : AbstractViewModel<VacanciesUiState>(), LoadVacancies {

    private val KEY_FOR_BUNDLE = "key"

    interface Mapper {
        fun map(
            vacanciesViewModel: VacanciesViewModel,
            liveDataWrapper: VacanciesLiveDataWrapper
        )
    }

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            loadVacancies()
    }

    fun init(mapper: Mapper) {
        mapper.map(
            this,
            liveDataWrapper
        )
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("inz", "CLEARED VacanciesVM")
    }

    override fun clickFavorite(vacancyUi: VacancyUi) {
        liveDataWrapper.clickFavorite(vacancyUi)
    }

    override fun clickRespond(vacancyUi: VacancyUi) {
    }

    override fun retry() {
        loadVacancies()
    }

    fun save(bundleWrapper: BundleWrapper.Save<VacanciesUiState>) {
        liveDataWrapper.save(bundleWrapper.saveWithKey(KEY_FOR_BUNDLE))
    }

    fun restore(bundleWrapper: BundleWrapper.Restore<VacanciesUiState>) {
        val vacancyState = bundleWrapper.restoreWithKey(KEY_FOR_BUNDLE).restore()
        liveDataWrapper.update(vacancyState)
    }

    override fun loadVacancies() {
        mapper.mapProgress()
        runAsync.runAsync(viewModelScope, {
            mainVacanciesRepository.vacancies()
        }) {
            it.map(mapper)
        }
    }

    override fun liveData(tag: String): LiveData<VacanciesUiState> = liveDataWrapper.liveData()
}

interface LoadVacancies {
    fun loadVacancies() = Unit
    fun loadVacanciesWithQuery(query: String) = Unit
}

