package com.example.hh.search.di

//class SearchViewModule(
//    private val core: Core,
//) : Module<LoadVacanciesViewModel> {
//
//    override fun viewModel(): LoadVacanciesViewModel {
//
//        val vacanciesLiveDataWrapper = VacanciesLiveDataWrapper.Base()
//        //  val customInputLiveDataWrapper = CustomInputLiveDataWrapper.Base()
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