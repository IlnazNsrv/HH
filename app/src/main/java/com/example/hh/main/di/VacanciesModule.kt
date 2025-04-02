package com.example.hh.main.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.loadvacancies.data.CreatePropertiesForVacancyUi
import com.example.hh.main.data.MainVacanciesRepository
import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesResultMapper
import com.example.hh.main.presentation.VacanciesViewModel

class VacanciesModule(private val core: Core) : Module<VacanciesViewModel> {

    override fun viewModel(): VacanciesViewModel {

        val vacanciesLiveDataWrapper = VacanciesLiveDataWrapper.Base()

        return VacanciesViewModel(
            core.lastTimeButtonClicked,
            MainVacanciesRepository.Base(
                CreatePropertiesForVacancyUi.Base(),
                core.vacanciesCacheModule.clearVacancies(),
                core.vacanciesCacheModule.dao(),
                core.favoriteVacanciesCacheModule.favoriteVacanciesDao(),
                LoadVacanciesCloudDataSource.Base(
                    core.provideRetrofitBuilder.provideRetrofitBuilder(),
                    core.handleDataError
                ),
                core.handleDomainError
            ),
            core.runAsync,
            VacanciesResultMapper(
                vacanciesLiveDataWrapper
            ),
            vacanciesLiveDataWrapper
        )
    }
}