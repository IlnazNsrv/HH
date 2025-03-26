package com.example.hh.search.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.search.presentation.SearchViewModel

class SearchViewModule(
    private val core: Core,
) : Module<SearchViewModel> {

    override fun viewModel(): SearchViewModel {

        return SearchViewModel(
            ChosenFiltersCache.Base(core.sharedPreferences)
        )
    }
}