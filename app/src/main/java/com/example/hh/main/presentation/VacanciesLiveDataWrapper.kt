package com.example.hh.main.presentation

import com.example.hh.core.LiveDataWrapper
import com.example.hh.main.data.BundleWrapper

interface VacanciesLiveDataWrapper : LiveDataWrapper.Mutable<VacanciesUiState> {

    fun clickFavorite(vacancyUi: VacancyUi)
    override fun save(bundle: BundleWrapper.Save<VacanciesUiState>)
    fun clickRespondTest(vacancyUi: VacancyUi)
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