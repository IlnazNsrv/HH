package com.example.hh.filters.core

import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.views.button.CustomButtonLiveDataWrapper
import com.example.hh.views.button.areabutton.CustomAreaButtonUiState

class FiltersCore {

    val customAreaButtonLiveDataWrapper =
        CustomButtonLiveDataWrapper.Base<CustomAreaButtonUiState>()

    val lastTimeButtonClicked = LastTimeButtonClicked.Base(400)
}