package com.example.hh.core

//import com.example.hh.loadvacancies.di.LoadVacanciesModule
import androidx.lifecycle.ViewModel
import com.example.hh.MainViewModel
import com.example.hh.filters.areafilters.di.AreaViewModule
import com.example.hh.filters.areafilters.presentation.screen.AreaViewModel
import com.example.hh.filters.di.FiltersViewModule
import com.example.hh.filters.presentation.FiltersViewModel
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

            //LoadVacanciesViewModel::class.java.simpleName -> LoadVacanciesModule(core).viewModel()

            FiltersViewModel::class.java.simpleName -> FiltersViewModule(core).viewModel()

            AreaViewModel::class.java.simpleName -> AreaViewModule(core).viewModel()

            //SearchViewModel::class.java.simpleName -> SearchViewModule(core, VacanciesLiveDataWrapper.Base(), CustomInputLiveDataWrapper.Base()).viewModel()

            else -> throw IllegalStateException("unknown class: $tag")

        } as T

    }
}

interface ClearViewModel {

    fun clear(tag: String)
}