package com.example.hh.views.input

import com.example.hh.core.LiveDataWrapper

interface CustomInputLiveDataWrapper : LiveDataWrapper.Mutable<CustomInputUiState> {

    class Base : LiveDataWrapper.Abstract<CustomInputUiState>(), CustomInputLiveDataWrapper
}