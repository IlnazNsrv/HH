package com.example.hh.core

import android.content.Context
import com.example.hh.main.data.HandleError

class Core(private val context: Context) {

    val provideResource: ProvideResource = ProvideResource.Base(context)

    val handleDataError: HandleError<Exception> = HandleError.Data()

    val handleDomainError: HandleError<String> = HandleError.Domain(provideResource)

    val runAsync = RunAsync.Base()
}