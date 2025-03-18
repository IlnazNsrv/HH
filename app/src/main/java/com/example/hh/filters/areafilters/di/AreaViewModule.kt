package com.example.hh.filters.areafilters.di

import com.example.hh.core.Core
import com.example.hh.core.Module
import com.example.hh.core.presentation.AreasResultMapper
import com.example.hh.filters.areafilters.data.AreasRepository
import com.example.hh.filters.areafilters.data.cache.AreasCacheDataSource
import com.example.hh.filters.areafilters.presentation.screen.AreaViewModel
import com.example.hh.filters.core.FiltersCore
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper

class AreaViewModule(private val core: Core, private val filtersCore: FiltersCore) : Module<AreaViewModel> {
    private val areaButtonLiveDataWrapper = FilterButtonsLiveDataWrapper.Base<FilterButtonUi>()
    override fun viewModel(): AreaViewModel {
        return AreaViewModel(
            filtersCore.customAreaButtonLiveDataWrapper,
            areaButtonLiveDataWrapper,
            core.runAsync,
            AreasRepository.Base(
                AreasCacheDataSource.Base(
                    core.areasCacheModule.database().areasDao(),
                ),
                ChosenFiltersCache.Base(core.sharedPreferences)
            ),
            AreasResultMapper(
                areaButtonLiveDataWrapper
            )
        )
    }
}