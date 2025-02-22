package com.example.hh.views.progress

import com.example.hh.core.LiveDataWrapper

interface CustomProgressLiveDataWrapper : LiveDataWrapper.Mutable<CustomProgressBarUiState> {

    class Base : LiveDataWrapper.Abstract<CustomProgressBarUiState>(), CustomProgressLiveDataWrapper
}