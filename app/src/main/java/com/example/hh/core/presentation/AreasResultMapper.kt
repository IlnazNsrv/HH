package com.example.hh.core.presentation

import com.example.hh.filters.areafilters.data.AreaChoice
import com.example.hh.filters.areafilters.data.LoadAreasResult
import com.example.hh.filters.presentation.ButtonsUiState
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper

class AreasResultMapper(
    private val areaButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>
) : LoadAreasResult.Mapper {

    override fun mapSuccess(list: List<AreaChoice>) {
        areaButtonLiveDataWrapper.update(ButtonsUiState.ShowAreaButtons(list.map {
            FilterButtonUi.AreaButton(
                it.id,
                it.text,
                it.chosen
            )
        }))
    }
}