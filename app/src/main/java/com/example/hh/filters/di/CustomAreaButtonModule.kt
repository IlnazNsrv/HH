package com.example.hh.filters.di

import com.example.hh.core.Module
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.search.presentation.VacanciesSearchParams
import com.example.hh.views.button.CustomButtonLiveDataWrapper
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel

class CustomAreaButtonModule(
    private val searchParams: VacanciesSearchParams.Builder,
    private val repository: FiltersRepository
) : Module<CustomAreaButtonViewModel> {

    override fun viewModel(): CustomAreaButtonViewModel {
        return CustomAreaButtonViewModel(
            CustomButtonLiveDataWrapper.Base(),
            searchParams,
            repository
        )
    }
}