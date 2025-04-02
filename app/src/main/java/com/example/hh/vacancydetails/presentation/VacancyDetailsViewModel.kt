package com.example.hh.vacancydetails.presentation

import androidx.lifecycle.LiveData
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.RunAsync
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.main.data.BundleWrapper
import com.example.hh.vacancydetails.data.LoadVacancyDetailsResult
import com.example.hh.vacancydetails.data.VacancyDetailsRepository
import com.example.hh.vacancydetails.presentation.screen.VacancyDetailsUiState
import com.example.hh.vacancydetails.progressActions.HideProgressAction
import com.example.hh.vacancydetails.progressActions.ShowProgressAction
import com.example.hh.views.progress.CustomProgressLiveDataWrapper
import com.example.hh.views.progress.CustomProgressViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay

class VacancyDetailsViewModel(
    private val lastTimeButtonClicked: LastTimeButtonClicked,
    private val vacancyDetailsLiveDataWrapper: VacancyDetailsLiveDataWrapper,
    private val mapper: LoadVacancyDetailsResult.Mapper,
    private val progressViewModel: CustomProgressViewModel,
    private val progressLiveDataWrapper: CustomProgressLiveDataWrapper,
    private val repository: VacancyDetailsRepository,
    private val runAsync: RunAsync
) : AbstractViewModel<VacancyDetailsUiState>() {

    interface Mapper {
        fun map(
            progressViewModel: CustomProgressViewModel,
            mapper: LoadVacancyDetailsResult.Mapper
        )
    }

    fun map(mapper: Mapper) {
        mapper.map(
            progressViewModel,
            this.mapper
        )
    }

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var cachedVacancyId: String? = null

    fun init(isFirstRun: Boolean, vacancyId: String) {
        if (isFirstRun) {
            cachedVacancyId = vacancyId
            loadVacancyDetails()
        }
    }

    fun loadVacancyDetails() {
        progressLiveDataWrapper.update(ShowProgressAction())
        runAsync.runAsync(viewModelScope, {
            delay(2000)
            repository.vacancyDetails(cachedVacancyId!!)
        }) {
            progressLiveDataWrapper.update(HideProgressAction())
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

    override fun liveData(tag: String): LiveData<VacancyDetailsUiState> =
        vacancyDetailsLiveDataWrapper.liveData()

}