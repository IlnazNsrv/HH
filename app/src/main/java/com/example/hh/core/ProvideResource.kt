package com.example.hh.core

import android.content.Context
import androidx.annotation.StringRes

interface ProvideResource {

    fun string(@StringRes id: Int): String

    class Base(private val context: Context) : ProvideResource {

        override fun string(id: Int): String {
            return context.getString(id)
        }

    }
}