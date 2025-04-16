package com.example.hh.views.text

import kotlinx.coroutines.CoroutineScope

interface HandleTextAction {
    fun handle(text: String, viewModelScope: CoroutineScope)

    object Empty : HandleTextAction {//todo use in translationTextViewModel
        override fun handle(text: String, viewModelScope: CoroutineScope) = Unit
    }
}