package com.example.hh.main.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.main.data.MainVacanciesRepository
import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesResultMapper
import com.example.hh.main.presentation.VacanciesViewModel
import com.example.hh.views.button.CustomButtonLiveDataWrapper
import com.example.hh.views.button.CustomButtonUiState
import com.example.hh.views.text.CustomTextLiveDataWrapper

class VacanciesModule(private val core: Core) : Module<VacanciesViewModel> {

    override fun viewModel(): VacanciesViewModel {
        val errorTextLiveDataWrapper: CustomTextLiveDataWrapper =
            CustomTextLiveDataWrapper.Base()
        val retryButtonLiveDataWrapper = CustomButtonLiveDataWrapper.Base<CustomButtonUiState>()
        val vacanciesLiveDataWrapper = VacanciesLiveDataWrapper.Base()
        val vacancyTitleLiveDataWrapper: CustomTextLiveDataWrapper =
            CustomTextLiveDataWrapper.Base()
        val vacancySalaryLiveDataWrapper: CustomTextLiveDataWrapper =
            CustomTextLiveDataWrapper.Base()
        val vacancyCityLiveDataWrapper: CustomTextLiveDataWrapper =
            CustomTextLiveDataWrapper.Base()
        val vacancyCompanyNameLiveDataWrapper: CustomTextLiveDataWrapper =
            CustomTextLiveDataWrapper.Base()
        val vacancyExperienceDataWrapper: CustomTextLiveDataWrapper =
            CustomTextLiveDataWrapper.Base()
        val favoriteButtonLiveDataWrapper: CustomButtonLiveDataWrapper<CustomButtonUiState> =
            CustomButtonLiveDataWrapper.Base<CustomButtonUiState>()
        val respondButtonLiveDataWrapper: CustomButtonLiveDataWrapper<CustomButtonUiState> =
            CustomButtonLiveDataWrapper.Base<CustomButtonUiState>()

        return VacanciesViewModel(
//            CustomTextViewModel(
//                HandleTextAction.Empty,
//                errorTextLiveDataWrapper
//            ),
//            CustomButtonViewModel(
//                RetryButtonAction(vacanciesLiveDataWrapper),
//                retryButtonLiveDataWrapper
//            ),
//            CustomTextViewModel(
//                HandleTextAction.Empty,
//                vacancyTitleLiveDataWrapper
//            ),
//            CustomTextViewModel(
//                HandleTextAction.Empty,
//                vacancySalaryLiveDataWrapper
//            ),
//            CustomTextViewModel(
//                HandleTextAction.Empty,
//                vacancyCityLiveDataWrapper
//            ),
//            CustomTextViewModel(
//                HandleTextAction.Empty,
//                vacancyCompanyNameLiveDataWrapper
//            ),
//            CustomTextViewModel(
//                HandleTextAction.Empty,
//                vacancyExperienceDataWrapper
//            ),
//            CustomButtonViewModel(
//                HandleButtonAction.Empty,
//                favoriteButtonLiveDataWrapper
//            ),
//            CustomButtonViewModel(
//                HandleButtonAction.Empty,
//                respondButtonLiveDataWrapper
//            ),
            MainVacanciesRepository.Base(
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