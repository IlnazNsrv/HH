package com.example.hh.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import com.example.hh.core.RunAsync
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.data.MainVacanciesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class VacanciesViewModel(
    //private val errorTextViewModel: CustomTextViewModel,
    // private val retryButtonViewModel: CustomButtonViewModel,
//    private val vacancyTitleViewModel: CustomTextViewModel,
//    private val vacancySalaryViewModel: CustomTextViewModel,
//    private val vacancyCityViewModel: CustomTextViewModel,
//    private val vacancyCompanyNameViewModel: CustomTextViewModel,
//    private val vacancyExperienceViewModel: CustomTextViewModel,
//    private val vacancyFavoriteButtonViewModel: CustomButtonViewModel,
//    private val vacancyRespondButtonViewModel: CustomButtonViewModel,
    private val mainVacanciesRepository: MainVacanciesRepository,
    private val runAsync: RunAsync,
    private val mapper: LoadVacanciesResult.Mapper,
    private val liveDataWrapper: VacanciesLiveDataWrapper,
) : ViewModel(), ClickActions, LoadVacancies, LiveDataWrapper.GetLiveData<VacanciesUiState> {

    interface Mapper {
        fun map(
            //errorTextViewModel: CustomTextViewModel,
            //retryButtonViewModel: CustomButtonViewModel,
//            vacancyTitleViewModel: CustomTextViewModel,
//            vacancySalaryViewModel: CustomTextViewModel,
//            vacancyCityViewModel: CustomTextViewModel,
//            vacancyCompanyNameViewModel: CustomTextViewModel,
//            vacancyExperienceViewModel: CustomTextViewModel,
//            vacancyFavoriteButtonViewModel: CustomButtonViewModel,
//            vacancyRespondButtonViewModel: CustomButtonViewModel,
            vacanciesViewModel: VacanciesViewModel
        )
    }

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            loadVacancies()
    }

    fun init(mapper: Mapper) {
        mapper.map(
            //errorTextViewModel,
            // retryButtonViewModel,
//            vacancyTitleViewModel,
//            vacancySalaryViewModel,
//            vacancyCityViewModel,
//            vacancyCompanyNameViewModel,
//            vacancyExperienceViewModel,
//            vacancyFavoriteButtonViewModel,
//            vacancyRespondButtonViewModel,
            this
        )
    }

    override fun clickFavorite(vacancyUi: VacancyUi) {
        liveDataWrapper.clickFavorite(vacancyUi)
    }

    override fun retry() {
        //retryButtonViewModel.handleClick()
    }

    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore) {
        val vacancyState = bundleWrapper.restore()
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

    override fun liveData(): LiveData<VacanciesUiState> = liveDataWrapper.liveData()
}

interface LoadVacancies {
    fun loadVacancies()
}

