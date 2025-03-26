package com.example.hh.loadvacancies.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.loadvacancies.data.CreatePropertiesForVacancyUi
import com.example.hh.loadvacancies.data.VacanciesRepository
import com.example.hh.loadvacancies.presentation.LoadVacanciesViewModel
import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesResultMapper
import com.example.hh.views.input.CustomInputLiveDataWrapper

class LoadVacanciesModule(private val core: Core) : Module<LoadVacanciesViewModel> {

    //val vacanciesLiveDataWrapper = VacanciesLiveDataWrapper.Base()
    val customInputLiveDataWrapper = CustomInputLiveDataWrapper.Base()
    val vacanciesLiveDataWrapper = VacanciesLiveDataWrapper.Base()


    override fun viewModel(): LoadVacanciesViewModel {

        return LoadVacanciesViewModel(
            core.lastTimeButtonClicked,
            core.clearViewModel,
            ChosenFiltersCache.Base(core.sharedPreferences),
            vacanciesLiveDataWrapper,
            core.runAsync,
            VacanciesRepository.Base(
                core.provideResource,
                CreatePropertiesForVacancyUi.Base(),
                LoadVacanciesCloudDataSource.Base(
                    core.provideRetrofitBuilder.provideRetrofitBuilder(),
                    core.handleDataError
                ),
                core.handleDomainError,
                core.vacanciesCacheModule.dao(),
                core.favoriteVacanciesCacheModule.favoriteVacanciesDao(),
                core.vacanciesCacheModule.clearVacancies()
            ),
            VacanciesResultMapper(
                vacanciesLiveDataWrapper
            )
        )
    }
}