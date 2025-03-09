package com.example.hh.views.input

import com.example.hh.core.UiState

interface CustomInputUiState : UiState {

    fun show(updateInput: UpdateCustomInput)

    data class Initial(private val userInput: String) : CustomInputUiState {
        override fun show(updateInput: UpdateCustomInput) {
            updateInput.update(userInput)
        }
    }
}