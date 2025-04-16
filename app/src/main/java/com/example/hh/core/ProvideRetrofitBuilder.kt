package com.example.hh.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ProvideRetrofitBuilder {

    fun provideRetrofitBuilder(): Retrofit.Builder

    class Base : ProvideRetrofitBuilder {

        private val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    })
                    .connectTimeout(40, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .writeTimeout(40, TimeUnit.SECONDS)
                    .build()
            )

        override fun provideRetrofitBuilder(): Retrofit.Builder = builder
    }
}