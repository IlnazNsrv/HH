package com.example.hh.core.presentation

import com.example.hh.filters.areafilters.data.AreaChoice
import com.example.hh.filters.areafilters.data.LoadAreasResult
import com.example.hh.filters.areafilters.presentation.screen.AreaUi
import com.example.hh.filters.presentation.ButtonsUiState
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper

class AreasResultMapper(
    private val areaButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<AreaUi>
) : LoadAreasResult.Mapper {

    override fun mapSuccess(list: List<AreaChoice>) {
        areaButtonLiveDataWrapper.update(ButtonsUiState.ShowAreaButtons(list.map {
            AreaUi.Base(
                it.id,
                it.text,
                it.chosen
            )
        }))
    }
}