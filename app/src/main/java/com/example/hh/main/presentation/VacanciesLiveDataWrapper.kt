package com.example.hh.main.presentation

import com.example.hh.core.LiveDataWrapper
import com.example.hh.main.data.BundleWrapper

interface VacanciesLiveDataWrapper : LiveDataWrapper.Mutable<VacanciesUiState> {

    fun clickFavorite(vacancyUi: VacancyUi)
    fun save(bundle: BundleWrapper.Save)

    class Base : LiveDataWrapper.Abstract<VacanciesUiState>(), VacanciesLiveDataWrapper {

        override fun clickFavorite(vacancyUi: VacancyUi) {
            liveData.value?.let {
                val newUiState = it.chooseFavorite(vacancyUi)
                update(newUiState)
            }
        }

        override fun save(bundle: BundleWrapper.Save) {
            bundle.save(liveData.value!!)
        }
    }
}