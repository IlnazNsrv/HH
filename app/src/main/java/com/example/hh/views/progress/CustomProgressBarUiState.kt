package com.example.hh.views.progress

import com.example.hh.core.UiState

interface CustomProgressBarUiState : UiState {

    fun show(updateCustomProgress: UpdateCustomProgress)
}