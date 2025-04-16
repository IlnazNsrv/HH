package com.example.hh.main.data

import android.os.Build
import android.os.Bundle
import com.example.hh.core.UiState

interface BundleWrapper {

    companion object {
        const val KEY = "vacancyList"
    }

    interface Save<T : UiState> {
        fun save(data: T)
        fun saveWithKey(key: String): Save<T>
    }

    interface Restore<T : UiState> {
        fun restore(): T
        fun restoreWithKey(key: String): Restore<T>
    }

    interface Mutable<T : UiState> : Save<T>, Restore<T>

    class Base<T : UiState>(private val bundle: Bundle) : Mutable<T> {

        override fun save(data: T) {
            bundle.putSerializable(KEY, data)
        }

        override fun saveWithKey(key: String): Save<T> {
            return KeyedBundleWrapper<T>(bundle, key)
        }

        override fun restore(): T {
            val vacancyList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(KEY)
            } else bundle.getSerializable(KEY)
            return vacancyList as T
        }

        override fun restoreWithKey(key: String): Restore<T> {
            return KeyedBundleWrapper(bundle, key)
        }
    }

    private class KeyedBundleWrapper<T : UiState>(
        private val bundle: Bundle,
        private val key: String
    ) : Save<T>, Restore<T> {
        override fun save(data: T) {
            bundle.putSerializable(key, data)
        }

        override fun restore(): T {
            val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(key)
            } else {
                bundle.getSerializable(key)
            }
            return data as T
        }

        override fun saveWithKey(key: String): Save<T> {
            return KeyedBundleWrapper(bundle, key)
        }

        override fun restoreWithKey(key: String): Restore<T> {
            return KeyedBundleWrapper(bundle, key)
        }
    }
}