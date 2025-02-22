package com.example.hh.views.text

import com.example.hh.core.LiveDataWrapper
import com.example.hh.views.text.CustomTextUiState

interface CustomTextLiveDataWrapper : LiveDataWrapper.Mutable<CustomTextUiState> {
    class Base : LiveDataWrapper.Abstract<CustomTextUiState>(), CustomTextLiveDataWrapper
}