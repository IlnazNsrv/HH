package com.example.hh.views.button

import com.example.hh.core.LiveDataWrapper

interface CustomButtonLiveDataWrapper : LiveDataWrapper.Mutable<CustomButtonUiState> {

    class Base : LiveDataWrapper.Abstract<CustomButtonUiState>(), CustomButtonLiveDataWrapper
}