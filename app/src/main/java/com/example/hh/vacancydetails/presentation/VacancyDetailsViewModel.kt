package com.example.hh.vacancydetails.presentation

import androidx.lifecycle.LiveData
import com.example.hh.core.RunAsync
import com.example.hh.core.presentation.AbstractViewModel
import com.example.hh.vacancydetails.data.LoadVacancyDetailsResult
import com.example.hh.vacancydetails.data.VacancyDetailsRepository
import com.example.hh.vacancydetails.data.cloud.VacancyDetailsCloud
import com.example.hh.vacancydetails.presentation.screen.VacancyDetailsUiState
import com.example.hh.vacancydetails.progressActions.HideProgressAction
import com.example.hh.vacancydetails.progressActions.ShowProgressAction
import com.example.hh.views.progress.CustomProgressLiveDataWrapper
import com.example.hh.views.progress.CustomProgressViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class VacancyDetailsViewModel(
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

    var cachedData: VacancyDetailsCloud? = null

    fun init(isFirstRun: Boolean, vacancyId: String) {
        // if (isFirstRun)
        progressLiveDataWrapper.update(ShowProgressAction())
        runAsync.runAsync(viewModelScope, {
            // cachedData = repository.vacancyDetails(vacancyId)
            repository.vacancyDetails(vacancyId)
        }) {
            progressLiveDataWrapper.update(HideProgressAction())
            it.map(mapper)
            //   VacancyDetailsUi.Base(cachedData!!, false).show(binding)
        }
    }

    override fun liveData(tag: String): LiveData<VacancyDetailsUiState> =
        vacancyDetailsLiveDataWrapper.liveData()

}