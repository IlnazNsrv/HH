package com.example.hh.views.text

import com.example.hh.core.LiveDataWrapper

interface CustomTextUiState : LiveDataWrapper.UiState {

    fun show(updateCustomTextView: UpdateCustomTextView) = Unit

    fun handle(text: String, viewModel: HandleText) = Unit
}