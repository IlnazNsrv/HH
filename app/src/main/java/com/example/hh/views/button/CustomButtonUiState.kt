package com.example.hh.views.button

import com.example.hh.core.UiState

interface CustomButtonUiState : UiState {

    fun show(updateCustomButton: UpdateCustomButton)
}