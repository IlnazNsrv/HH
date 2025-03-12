package com.example.hh.views.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CustomInputViewModel(
    private val handleInputAction: HandleInputAction,
    private val customInputLiveDataWrapper: CustomInputLiveDataWrapper,
) : ViewModel(), LiveDataWrapper.GetLiveData<CustomInputUiState>, HandleClickWithNavigate  {

   // private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun liveData(): LiveData<CustomInputUiState> {
        return customInputLiveDataWrapper.liveData()
    }

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun handleClick(text: String, navigateToLoad: NavigateToLoadVacancies) {
        navigate(navigateToLoad)
        handleInputAction.handle(text, viewModelScope)
    }

    private fun navigate(navigate: NavigateToLoadVacancies) = navigate.navigateToLoadVacancies()
}

interface HandleClickWithNavigate {
    fun handleClick(text: String, navigateToLoad: NavigateToLoadVacancies)
}