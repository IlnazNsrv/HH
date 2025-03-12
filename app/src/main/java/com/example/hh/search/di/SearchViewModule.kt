package com.example.hh.search.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.loadvacancies.presentation.LoadVacanciesViewModel
import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesResultMapper
import com.example.hh.search.data.VacanciesRepository

class SearchViewModule(
    private val core: Core,
) : Module<LoadVacanciesViewModel> {

    override fun viewModel(): LoadVacanciesViewModel {

        val vacanciesLiveDataWrapper = VacanciesLiveDataWrapper.Base()
        //  val customInputLiveDataWrapper = CustomInputLiveDataWrapper.Base()

        return LoadVacanciesViewModel(
            vacanciesLiveDataWrapper,
            core.runAsync,
            VacanciesRepository.Base(
                LoadVacanciesCloudDataSource.Base(
                    core.provideRetrofitBuilder.provideRetrofitBuilder(),
                    core.handleDataError
                ),
                core.handleDomainError
            ),
            VacanciesResultMapper(
                vacanciesLiveDataWrapper
            )
        )
    }
}