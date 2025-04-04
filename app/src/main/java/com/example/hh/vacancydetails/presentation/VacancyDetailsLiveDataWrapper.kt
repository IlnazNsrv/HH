package com.example.hh.vacancydetails.presentation

import com.example.hh.core.LiveDataWrapper
import com.example.hh.main.data.BundleWrapper
import com.example.hh.vacancydetails.presentation.screen.VacancyDetailsUiState

interface VacancyDetailsLiveDataWrapper : LiveDataWrapper.Mutable<VacancyDetailsUiState> {

    fun clickFavorite()

    class Base : LiveDataWrapper.Abstract<VacancyDetailsUiState>(), VacancyDetailsLiveDataWrapper {

        override fun clickFavorite() {
            liveData.value?.let {
                update(it.clickFavorite())
            }
        }

        override fun save(bundleWrapper: BundleWrapper.Save<VacancyDetailsUiState>) {
            if (liveData.value != null)
                bundleWrapper.save(liveData.value!!)
        }
    }
}