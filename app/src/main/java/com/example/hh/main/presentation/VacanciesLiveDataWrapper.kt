package com.example.hh.main.presentation

import com.example.hh.core.LiveDataWrapper
import com.example.hh.main.data.BundleWrapper

interface VacanciesLiveDataWrapper : LiveDataWrapper.Mutable<VacanciesUiState> {

    fun clickFavorite(vacancyUi: VacancyUi)
    override fun save(bundleWrapper: BundleWrapper.Save<VacanciesUiState>)
    fun clickVacancy(vacancyUi: VacancyUi) = Unit

    class Base : LiveDataWrapper.Abstract<VacanciesUiState>(), VacanciesLiveDataWrapper {

        override fun clickVacancy(vacancyUi: VacancyUi) {
            liveData.value?.let {
                val newUiState = it.clickVacancy(vacancyUi.id())
                update(newUiState)
            }
        }

        override fun clickFavorite(vacancyUi: VacancyUi) {
            liveData.value?.let {
                val newUiState = it.chooseFavorite(vacancyUi)
                update(newUiState)
            }
        }

        override fun save(bundleWrapper: BundleWrapper.Save<VacanciesUiState>) {
            bundleWrapper.save(liveData.value!!)
        }
    }
}