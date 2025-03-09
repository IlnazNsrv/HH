package com.example.hh.views.input

import kotlinx.coroutines.CoroutineScope

interface HandleInputAction {

    fun handle(text: String, viewModelScope: CoroutineScope)
}