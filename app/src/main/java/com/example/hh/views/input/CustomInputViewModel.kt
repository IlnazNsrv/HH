package com.example.hh.views.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CustomInputViewModel(
    //private val handleInputAction: HandleInputAction,
    private val customInputLiveDataWrapper: CustomInputLiveDataWrapper,
) : ViewModel(), LiveDataWrapper.GetLiveData<CustomInputUiState>, HandleClickWithNavigate, HandleInputValue {

    override fun liveData(): LiveData<CustomInputUiState> {
        return customInputLiveDataWrapper.liveData()
    }

    private var cachedQuery: String = ""
    private var cachedSalaryQuery: Int? = null


    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun handleClick(text: String, navigateToLoad: NavigateToLoadVacancies) {
        navigate(navigateToLoad)
    }



    private fun navigate(navigate: NavigateToLoadVacancies) = navigate.navigateToLoadVacancies()

    fun getCachedInputText() : String {
        return cachedQuery
    }

    fun getCachedSalaryQuery() : Int? {
        return cachedSalaryQuery
    }

    override fun cacheInputText(value: String) {
        cachedQuery = value
    }

    override fun cacheInputNumber(value: Int?) {
        cachedSalaryQuery = value
    }
}

interface HandleClickWithNavigate {
    fun handleClick(text: String, navigateToLoad: NavigateToLoadVacancies)
}

interface HandleInputValue {
    fun cacheInputText(value: String)
    fun cacheInputNumber(value: Int?)
}
