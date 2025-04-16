package com.example.hh.main.data

import com.example.hh.R
import com.example.hh.core.ProvideResource
import java.net.UnknownHostException

interface HandleError<T : Any> {

    fun handle(error: Exception): T

    class Data : HandleError<Exception> {

        override fun handle(error: Exception): Exception {
            return if (error is UnknownHostException)
                NoInternetConnection()
            else
                ServiceUnavailableException()
        }
    }

    class Domain(
        private val provideResources: ProvideResource
    ) : HandleError<String> {
        override fun handle(error: Exception): String {
            val message = if (error is NoInternetConnection)
                provideResources.string(R.string.no_internet_connection)
            else
                provideResources.string(R.string.service_is_unavailable)
            return message
        }
    }
}