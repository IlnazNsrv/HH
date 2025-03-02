package com.example.hh.views.text

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CustomTextViewModel(
    private val handleTextAction: HandleTextAction,
    private val liveDataWrapper: CustomTextLiveDataWrapper
) : ViewModel(), LiveDataWrapper.GetLiveData<CustomTextUiState>, HandleText {

    override fun liveData(): LiveData<CustomTextUiState> {
        return liveDataWrapper.liveData()
    }

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun handleText(text: String) {
        handleTextAction.handle(text, viewModelScope)
    }
}

interface HandleText {
    fun handleText(text: String)
}