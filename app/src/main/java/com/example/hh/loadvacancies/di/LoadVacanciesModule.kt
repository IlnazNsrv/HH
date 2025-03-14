package com.example.hh.loadvacancies.di

//class LoadVacanciesModule(private val core: Core) : Module<LoadVacanciesViewModel> {
//
//    //val vacanciesLiveDataWrapper = VacanciesLiveDataWrapper.Base()
//    val customInputLiveDataWrapper = CustomInputLiveDataWrapper.Base()
//    val vacanciesLiveDataWrapper = VacanciesLiveDataWrapper.Base()
//
//
//    override fun viewModel(): LoadVacanciesViewModel {
//
//        return LoadVacanciesViewModel(
//            vacanciesLiveDataWrapper,
//            core.runAsync,
//            VacanciesRepository.Base(
//                LoadVacanciesCloudDataSource.Base(
//                    core.provideRetrofitBuilder.provideRetrofitBuilder(),
//                    core.handleDataError
//                ),
//                core.handleDomainError
//            ),
//            VacanciesResultMapper(
//                vacanciesLiveDataWrapper
//            )
//        )
//    }
//}