package com.example.hh.vacancydetails.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.vacancydetails.data.VacancyDetailsRepository
import com.example.hh.vacancydetails.data.cloud.LoadVacancyDetailsCloudDataSource
import com.example.hh.vacancydetails.presentation.VacancyDetailsLiveDataWrapper
import com.example.hh.vacancydetails.presentation.VacancyDetailsResultMapper
import com.example.hh.vacancydetails.presentation.VacancyDetailsViewModel
import com.example.hh.views.progress.CustomProgressLiveDataWrapper
import com.example.hh.views.progress.CustomProgressViewModel

class VacancyDetailsViewModule(private val core: Core) : Module<VacancyDetailsViewModel> {


    override fun viewModel(): VacancyDetailsViewModel {

        val progressLiveDataWrapper: CustomProgressLiveDataWrapper =
            CustomProgressLiveDataWrapper.Base()

        val vacancyDetailsLiveDataWrapper =
            VacancyDetailsLiveDataWrapper.Base()

        return VacancyDetailsViewModel(
            core.lastTimeButtonClicked,
            vacancyDetailsLiveDataWrapper,
            VacancyDetailsResultMapper(
                vacancyDetailsLiveDataWrapper
            ),
            CustomProgressViewModel(progressLiveDataWrapper),
            progressLiveDataWrapper,
            VacancyDetailsRepository.Base(
                core.vacanciesCacheModule.dao(),
                core.favoriteVacanciesCacheModule.favoriteVacanciesDao(),
                LoadVacancyDetailsCloudDataSource.Base(
                    core.provideRetrofitBuilder.provideRetrofitBuilder(),
                    core.handleDataError
                ),
                core.handleDomainError
            ),
            core.runAsync
        )
    }
}