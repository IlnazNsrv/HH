package com.example.hh.main.presentation

import com.example.hh.core.LiveDataWrapper
import com.example.hh.main.data.BundleWrapper

interface VacanciesLiveDataWrapper : LiveDataWrapper.Mutable<VacanciesUiState> {

    fun clickFavorite(vacancyUi: VacancyUi)
    fun save(bundle: BundleWrapper.Save<VacanciesUiState>)
    fun clickRespondTest(vacancyUi: VacancyUi)

    class Base : LiveDataWrapper.Abstract<VacanciesUiState>(), VacanciesLiveDataWrapper {

        override fun clickFavorite(vacancyUi: VacancyUi) {
            liveData.value?.let {
                val newUiState = it.chooseFavorite(vacancyUi)
                update(newUiState)
            }
        }

        override fun save(bundle: BundleWrapper.Save<VacanciesUiState>) {
            bundle.save(liveData.value!!)
        }

        override fun clickRespondTest(vacancyUi: VacancyUi) {
            liveData.value?.let {
                val newUiState = it.chooseFavorite(vacancyUi)
                update(newUiState)
            }
        }
    }
}