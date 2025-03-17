package com.example.hh.core.presentation

import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import com.example.hh.core.UiState
import com.example.hh.main.presentation.ClickActions

abstract class AbstractViewModel<T: UiState> : ViewModel(), LiveDataWrapper.GetLiveDataWithTag<T>, ClickActions {
}