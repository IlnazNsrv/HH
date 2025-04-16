package com.example.hh.views.button

import com.example.hh.core.LiveDataWrapper
import com.example.hh.core.UiState

interface CustomButtonLiveDataWrapper<T: UiState> : LiveDataWrapper.Mutable<T> {

    class Base<T : UiState> : LiveDataWrapper.Abstract<T>(), CustomButtonLiveDataWrapper<T>
}