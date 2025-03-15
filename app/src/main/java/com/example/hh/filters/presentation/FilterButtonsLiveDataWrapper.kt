package com.example.hh.filters.presentation

import com.example.hh.core.LiveDataWrapper
import com.example.hh.main.presentation.ItemsUi

interface FilterButtonsLiveDataWrapper<T: ItemsUi> : LiveDataWrapper.Mutable<ButtonsUiState<T>> {

    fun clickButton(buttonUi: T)

    class Base<T: ItemsUi> : LiveDataWrapper.Abstract<ButtonsUiState<T>>(), FilterButtonsLiveDataWrapper<T> {

        override fun clickButton(buttonUi: T) {
            liveData.value?.let {
                val newUiState = it.click(buttonUi)
                update(newUiState)
            }
        }
    }
}