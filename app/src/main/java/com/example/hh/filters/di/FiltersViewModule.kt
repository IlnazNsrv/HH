package com.example.hh.filters.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.filters.presentation.CreateFilters
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import com.example.hh.filters.presentation.FiltersViewModel

class FiltersViewModule(private val core: Core) : Module<FiltersViewModel> {

    override fun viewModel(): FiltersViewModel {
        return FiltersViewModel(
            FilterButtonsLiveDataWrapper.Base<FilterButtonUi>(),
            FilterButtonsLiveDataWrapper.Base<FilterButtonUi>(),
            FilterButtonsLiveDataWrapper.Base<FilterButtonUi>(),
            FilterButtonsLiveDataWrapper.Base<FilterButtonUi>(),
            FiltersRepository.Base(ChosenFiltersCache.Base(core.sharedPreferences)),
            CreateFilters.Base()
        )
    }
}