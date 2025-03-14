package com.example.hh.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

interface LiveDataWrapper {

    interface GetLiveData<T: Any> {
        fun liveData() : LiveData<T>
    }

    interface GetLiveDataWithTag<T: Any> {

        fun liveData(tag: String) : LiveData<T>
    }

    interface Update<T: UiState> {
        fun update(data: T)
    }

    interface Mutable<T: UiState> : GetLiveData<T>, Update<T>, GetLiveDataWithTag<T>

    abstract class Abstract<T : UiState>(protected val liveData: MutableLiveData<T> = SingleLiveEvent()) :
        Mutable<T> {

        override fun liveData(): LiveData<T> {
            return liveData
        }

        override fun update(data: T) {
            liveData.value = data
        }

        override fun liveData(tag: String): LiveData<T> {
           return liveData
        }
    }

}

interface UiState : Serializable
