package com.example.hh.vacancydetails.presentation

import androidx.lifecycle.LiveData
import com.example.hh.core.ClearViewModel
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.RunAsync
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.main.data.BundleWrapper
import com.example.hh.vacancydetails.data.LoadVacancyDetailsResult
import com.example.hh.vacancydetails.data.VacancyDetailsRepository
import com.example.hh.vacancydetails.presentation.screen.VacancyDetailsUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay

class VacancyDetailsViewModel(
    private val clearViewModel: ClearViewModel,
    private val lastTimeButtonClicked: LastTimeButtonClicked,
    private val vacancyDetailsLiveDataWrapper: VacancyDetailsLiveDataWrapper,
    private val mapper: LoadVacancyDetailsResult.Mapper,
    private val repository: VacancyDetailsRepository,
    private val runAsync: RunAsync
) : AbstractViewModel<VacancyDetailsUiState>() {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var cachedVacancyId: String? = null

    fun init(isFirstRun: Boolean, vacancyId: String) {
        if (isFirstRun) {
            cachedVacancyId = vacancyId
            loadVacancyDetails()
        }
    }

    fun loadVacancyDetails() {
        vacancyDetailsLiveDataWrapper.update(VacancyDetailsUiState.Progress(VacancyDetailsUi.Progress))
        runAsync.runAsync(viewModelScope, {
            delay(1000)
            repository.vacancyDetails(cachedVacancyId!!)
        }) {
            it.map(mapper)
        }
    }

    fun save(bundleWrapper: BundleWrapper.Save<VacancyDetailsUiState>) {
        vacancyDetailsLiveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore<VacancyDetailsUiState>) {
        vacancyDetailsLiveDataWrapper.update(bundleWrapper.restore())
    }

    override fun clickFavorite() {
        if (lastTimeButtonClicked.timePassed())
            runAsync.runAsync(viewModelScope, {
                repository.updateFavoriteStatus(cachedVacancyId!!)
            }, {
                vacancyDetailsLiveDataWrapper.clickFavorite()
            })
    }

    fun clearViewModel() {
        clearViewModel.clear(this::class.java.simpleName)
    }

    override fun liveData(tag: String): LiveData<VacancyDetailsUiState> =
        vacancyDetailsLiveDataWrapper.liveData()
}