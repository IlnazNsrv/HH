package com.example.hh.filters.di

import com.example.hh.core.Module
import com.example.hh.filters.core.FiltersCore
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.search.presentation.VacanciesSearchParams
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel

class CustomAreaButtonModule(
    private val filtersCore: FiltersCore,
    private val searchParams: VacanciesSearchParams.Builder,
    private val repository: FiltersRepository
) : Module<CustomAreaButtonViewModel> {

    override fun viewModel(): CustomAreaButtonViewModel {
        return CustomAreaButtonViewModel(
            filtersCore.customAreaButtonLiveDataWrapper,
            searchParams,
            repository
        )
    }
}