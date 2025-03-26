package com.example.hh.favorite.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.favorite.data.FavoriteRepository
import com.example.hh.favorite.presentation.FavoriteVacanciesViewModel
import com.example.hh.loadvacancies.data.CreatePropertiesForVacancyUi
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesResultMapper

class FavoriteVacanciesViewModule(private val core: Core) : Module<FavoriteVacanciesViewModel> {

    private val vacanciesLiveDataWrapper = VacanciesLiveDataWrapper.Base()

    override fun viewModel(): FavoriteVacanciesViewModel {
        return FavoriteVacanciesViewModel(
            core.runAsync,
            vacanciesLiveDataWrapper,
            FavoriteRepository.Base(
                core.vacanciesCacheModule.dao(),
                core.favoriteVacanciesCacheModule.favoriteVacanciesDao(),
                core.handleDomainError,
                CreatePropertiesForVacancyUi.Base()
            ),
            VacanciesResultMapper(
                vacanciesLiveDataWrapper
            )
        )
    }
}