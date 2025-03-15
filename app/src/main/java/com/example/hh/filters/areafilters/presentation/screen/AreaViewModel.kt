package com.example.hh.filters.areafilters.presentation.screen

import androidx.lifecycle.ViewModel
import com.example.hh.core.RunAsync
import com.example.hh.filters.areafilters.data.AreasRepository
import com.example.hh.filters.areafilters.data.LoadAreasResult
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class AreaViewModel(
    private val buttonLiveDataWrapper: FilterButtonsLiveDataWrapper.Base<AreaUi>,
    private val runAsync: RunAsync,
    private val repository: AreasRepository,
    private val mapper: LoadAreasResult.Mapper
) : ViewModel() {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)



    fun loadAreas() {
        runAsync.runAsync(viewModelScope, {
            repository.areas()
        }) {
            it.map(mapper)
        }
    }
}