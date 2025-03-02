package com.example.hh.main.data

import android.os.Build
import android.os.Bundle
import com.example.hh.core.UiState

interface BundleWrapper<T: UiState> {

    companion object {
        const val KEY = "vacancyList"
    }

    interface Save<T: UiState> {
        fun save(vacancyList: T)
    }

    interface Restore<T: UiState> {
        fun restore(): T
    }

    interface Mutable<T: UiState> : Save<T>, Restore<T>

    class Base<T: UiState>(private val bundle: Bundle) : Mutable<T> {
        override fun save(vacancyList: T) {
           bundle.putSerializable(KEY, vacancyList)
            //bundle.putSerializable(KEY, arrayListOf(vacancyList))
        }

//        override fun restore(): T {
//            val vacancyList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                bundle.getSerializable(KEY) as T
//            } else bundle.getSerializable(KEY) as T
//            return vacancyList
//        }

        override fun restore(): T {
            val vacancyList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(KEY)
            } else bundle.getSerializable(KEY)
            return vacancyList as T
        }

    }
}