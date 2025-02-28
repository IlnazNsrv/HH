package com.example.hh.views.button

import kotlinx.coroutines.CoroutineScope

interface HandleButtonAction {

    fun handle(viewModelScope: CoroutineScope)

    object Empty : HandleButtonAction {
        override fun handle(viewModelScope: CoroutineScope) = Unit
    }
}