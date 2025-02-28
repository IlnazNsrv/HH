package com.example.hh.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    interface GetLiveData<T: Any> {
        fun liveData() : LiveData<T>
    }

    interface Update<T: UiState> {
        fun update(data: T)
    }

    interface Mutable<T: UiState> : GetLiveData<T>, Update<T>

    abstract class Abstract<T : UiState>(protected val liveData: MutableLiveData<T> = SingleLiveEvent()) :
        Mutable<T> {

        override fun liveData(): LiveData<T> {
            return liveData
        }

        override fun update(data: T) {
            liveData.value = data
        }
    }
}

interface UiState
