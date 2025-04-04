package com.example.hh.search.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.core.VacanciesSearchParams
import com.example.hh.filters.core.FiltersCore
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.search.presentation.SearchViewModel
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel

class SearchViewModule(
    private val core: Core,
    private val filtersCore: FiltersCore
) : Module<SearchViewModel> {

    private val searchParams = VacanciesSearchParams.Builder()
    private val chosenFilters = ChosenFiltersCache.Base(core.sharedPreferences)

    override fun viewModel(): SearchViewModel {

        return SearchViewModel(
            core.clearViewModel,
            searchParams,
            chosenFilters,
            CustomAreaButtonViewModel(
                core.lastTimeButtonClicked,
                filtersCore.customAreaButtonLiveDataWrapper,
                searchParams,
                FiltersRepository.Base(chosenFilters)
            ),
            core.lastTimeButtonClicked
        )
    }
}