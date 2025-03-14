package com.example.hh.filters.presentation

import com.example.hh.core.LiveDataWrapper

interface FilterButtonsLiveDataWrapper : LiveDataWrapper.Mutable<ButtonsUiState> {

    fun clickButton(buttonUi: FilterButtonUi)

    class Base : LiveDataWrapper.Abstract<ButtonsUiState>(), FilterButtonsLiveDataWrapper {

        override fun clickButton(buttonUi: FilterButtonUi) {
            liveData.value?.let {
                val newUiState = it.click(buttonUi)
                update(newUiState)
            }
        }

    }
}