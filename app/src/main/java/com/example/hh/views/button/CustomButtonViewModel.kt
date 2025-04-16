package com.example.hh.views.button

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CustomButtonViewModel(
    private val handleButtonAction: HandleButtonAction,
    private val customButtonLiveDataWrapper: CustomButtonLiveDataWrapper<CustomButtonUiState>
) : ViewModel(), LiveDataWrapper.GetLiveData<CustomButtonUiState> {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun liveData(): LiveData<CustomButtonUiState> {
        return customButtonLiveDataWrapper.liveData()
    }

    fun handleClick() {
        handleButtonAction.handle(viewModelScope)
    }
}