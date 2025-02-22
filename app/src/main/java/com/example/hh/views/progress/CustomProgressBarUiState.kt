package com.example.hh.views.progress

import com.example.hh.core.LiveDataWrapper

interface CustomProgressBarUiState : LiveDataWrapper.UiState {

    fun show(updateCustomProgress: UpdateCustomProgress)
}