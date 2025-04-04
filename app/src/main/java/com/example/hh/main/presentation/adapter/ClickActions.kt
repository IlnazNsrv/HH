package com.example.hh.main.presentation.adapter

import com.example.hh.core.UiState
import com.example.hh.filters.presentation.ChooseButton
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.presentation.ItemsUi
import com.example.hh.main.presentation.VacancyUi

interface SaveItems<T: UiState> {
    fun save(bundle: BundleWrapper.Save<T>)
    fun restore(bundleWrapper: BundleWrapper.Restore<T>)
}

interface UpdateItems<T: ItemsUi> {
    fun updateItems(newList: List<T>)
}


interface ClickActions : ChooseButton {

    fun clickFavorite(vacancyUi: VacancyUi) = Unit
    fun clickFavorite() = Unit
    fun retry() = Unit
    fun clickRespond(vacancyUi: VacancyUi) = Unit
    fun clickVacancy(vacancyUi: VacancyUi) = Unit
}

