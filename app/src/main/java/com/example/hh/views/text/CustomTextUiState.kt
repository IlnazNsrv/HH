package com.example.hh.views.text

import com.example.hh.core.UiState

interface CustomTextUiState : UiState {

    fun show(updateCustomTextView: UpdateCustomTextView) = Unit

    fun handle(text: String, viewModel: HandleText) = Unit
}