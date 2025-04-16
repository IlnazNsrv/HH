package com.example.hh.views.button.areabutton

import com.example.hh.core.UiState

interface CustomAreaButtonUiState : UiState {

    fun show(updateCustomAreaButton: UpdateCustomAreaButton)

    data class Show(private val chosenArea: Pair<String, String>?) : CustomAreaButtonUiState {
        override fun show(updateCustomAreaButton: UpdateCustomAreaButton) {
            updateCustomAreaButton.update(chosenArea)
        }

        fun chosenArea() = chosenArea
    }
}