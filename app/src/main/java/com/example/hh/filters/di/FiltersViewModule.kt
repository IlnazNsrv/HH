package com.example.hh.filters.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.filters.core.FiltersCore
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.filters.presentation.CreateFilters
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import com.example.hh.filters.presentation.FiltersViewModel
import com.example.hh.search.presentation.VacanciesSearchParams

class FiltersViewModule(private val core: Core) : Module<FiltersViewModel> {

    companion object {
        val coreFilters = FiltersCore()
    }

    private val searchParams = VacanciesSearchParams.Builder()
    private val filtersRepository =
        FiltersRepository.Base(ChosenFiltersCache.Base(core.sharedPreferences))

    override fun viewModel(): FiltersViewModel {

        return FiltersViewModel(
            coreFilters.lastTimeButtonClicked,
            core.clearViewModel,
            searchParams,
            FilterButtonsLiveDataWrapper.Base<FilterButtonUi>(),
            FilterButtonsLiveDataWrapper.Base<FilterButtonUi>(),
            FilterButtonsLiveDataWrapper.Base<FilterButtonUi>(),
            FilterButtonsLiveDataWrapper.Base<FilterButtonUi>(),
            filtersRepository,
            CreateFilters.Base(),
            CustomAreaButtonModule(
                coreFilters,
                searchParams,
                filtersRepository
            ).viewModel(),
        )
    }
}