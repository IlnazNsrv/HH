package com.example.hh.core

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface ProvideResource {

    fun string(@StringRes id: Int): String
    fun array(@ArrayRes id: Int) : Array<String>

    class Base(private val context: Context) : ProvideResource {

        override fun string(id: Int): String {
            return context.getString(id)
        }

        override fun array(id: Int): Array<String> {
            return context.resources.getStringArray(id)
        }
    }
}