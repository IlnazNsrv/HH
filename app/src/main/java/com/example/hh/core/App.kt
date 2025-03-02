package com.example.hh.core

import android.app.Application
import androidx.lifecycle.ViewModel

class App : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val clearViewModel = object : ClearViewModel {
            override fun clear(tag: String) {
                factory.clear(tag)
            }
        }
        factory = ProvideViewModel.Factory(ProvideViewModel.Make(Core(clearViewModel, this)))
    }

    override fun <T : ViewModel> viewModel(tag: String): T {
        return factory.viewModel(tag)
    }
}