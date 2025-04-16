package com.example.hh.main.presentation

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.RunAsync
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.filters.presentation.screen.NavigateToFilters
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.data.MainVacanciesRepository
import com.example.hh.search.presentation.screen.SearchFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class VacanciesViewModel(
    private val lastTimeButtonClicked: LastTimeButtonClicked,
    private val mainVacanciesRepository: MainVacanciesRepository,
    private val runAsync: RunAsync,
    private val mapper: LoadVacanciesResult.Mapper,
    private val liveDataWrapper: VacanciesLiveDataWrapper,
) : AbstractViewModel<VacanciesUiState>(), LoadVacancies {

    companion object {
        private const val KEY_FOR_BUNDLE = "key"
    }

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

    fun navigateToFilters(navigate: NavigateToFilters) {
        if (lastTimeButtonClicked.timePassed())
            navigate.navigateToFilters()
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
        if (lastTimeButtonClicked.timePassed()) {
            runAsync.runAsync(viewModelScope, {
                mainVacanciesRepository.updateFavoriteStatus(vacancyUi)
            }, {
                liveDataWrapper.clickFavorite(vacancyUi)
            })
        }
    }


    override fun clickVacancy(vacancyUi: VacancyUi) {
        liveDataWrapper.clickVacancy(vacancyUi)
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

    fun openSearchDialogFragment(fragmentManager: FragmentManager) {
        if (lastTimeButtonClicked.timePassed()) {
            val dialogFragment = SearchFragment()
            dialogFragment.show(fragmentManager, SearchFragment.TAG)
        }
    }

    override fun liveData(tag: String): LiveData<VacanciesUiState> = liveDataWrapper.liveData()
}

interface LoadVacancies {
    fun loadVacancies() = Unit
}

