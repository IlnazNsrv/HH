package com.example.hh.views.button

import com.example.hh.core.LiveDataWrapper

interface CustomButtonUiState : LiveDataWrapper.UiState {

    fun show(updateCustomButton: UpdateCustomButton)
}