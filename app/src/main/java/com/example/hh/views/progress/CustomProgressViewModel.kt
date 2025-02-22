package com.example.hh.views.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper

class CustomProgressViewModel(
    private val customProgressLiveDataWrapper: CustomProgressLiveDataWrapper
) : ViewModel(), LiveDataWrapper.GetLiveData<CustomProgressBarUiState> {

    override fun liveData(): LiveData<CustomProgressBarUiState> {
       return customProgressLiveDataWrapper.liveData()
    }
}