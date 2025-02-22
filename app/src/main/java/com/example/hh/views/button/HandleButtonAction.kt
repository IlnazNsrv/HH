package com.example.hh.views.button

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope

interface HandleButtonAction {

    fun handle(viewModelScope: CoroutineScope)
}