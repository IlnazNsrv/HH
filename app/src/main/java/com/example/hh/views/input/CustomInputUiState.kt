package com.example.hh.views.input

import com.example.hh.core.UiState

interface CustomInputUiState : UiState {

    fun show(updateInput: UpdateCustomInput)
    fun text() : String

    data class Initial(private val userInput: String) : CustomInputUiState {
        override fun show(updateInput: UpdateCustomInput) {
            updateInput.update(userInput)
        }

        override fun text(): String {
            return userInput
        }
    }
}