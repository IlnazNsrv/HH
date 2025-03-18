package com.example.hh.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hh.main.data.BundleWrapper
import java.io.Serializable

interface LiveDataWrapper {

    interface Save<T : UiState> {
        fun save(bundleWrapper: BundleWrapper.Save<T>)
    }

    interface Restore<T: UiState> {
        fun restore(bundleWrapper: BundleWrapper.Restore<T>)
    }

    interface GetLiveData<T: Any> {
        fun liveData() : LiveData<T>
    }

    interface GetLiveDataWithTag<T: Any> {

        fun liveData(tag: String) : LiveData<T>
    }

    interface Update<T: UiState> {
        fun update(data: T)
    }

    interface Mutable<T: UiState> : GetLiveData<T>, Update<T>, Save<T>

    abstract class Abstract<T : UiState>(protected val liveData: MutableLiveData<T> = SingleLiveEvent()) :
        Mutable<T>, Save<T> {

        override fun save(bundleWrapper: BundleWrapper.Save<T>) {
            bundleWrapper.save(liveData.value!!)
        }

        override fun liveData(): LiveData<T> {
            return liveData
        }

        override fun update(data: T) {
            liveData.value = data
        }
    }

}

interface UiState : Serializable {
    //fun<T : ItemsUi> show(updateItemsRecyclerView: UpdateItemsRecyclerView<T>) = Unit
}
