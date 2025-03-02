package com.example.hh.core

import androidx.lifecycle.ViewModel
import com.example.hh.MainViewModel
import com.example.hh.main.di.VacanciesModule
import com.example.hh.main.presentation.VacanciesViewModel

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(tag: String): T

    class Factory(private val make: ProvideViewModel) : ProvideViewModel, ClearViewModel {

        private val map = HashMap<String, ViewModel?>()

        override fun <T : ViewModel> viewModel(tag: String): T {
            return if (map[tag] != null) {
                map[tag]!! as T
            } else {
                val viewModel: T = make.viewModel(tag)
                map[tag] = viewModel
                viewModel
            }
        }

        override fun clear(tag: String) {
            map[tag] = null
        }
    }

    class Make(private val core: Core) : ProvideViewModel {

        override fun <T : ViewModel> viewModel(tag: String): T = when (tag) {

            MainViewModel::class.java.simpleName -> MainViewModel(core.clearViewModel)

            VacanciesViewModel::class.java.simpleName -> VacanciesModule(core).viewModel()

            else -> throw IllegalStateException("unknown class: $tag")

        } as T

    }
}

interface ClearViewModel {

    fun clear(tag: String)
}