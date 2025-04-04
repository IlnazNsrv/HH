package com.example.hh.views.button.areabutton

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.LiveDataWrapper
import com.example.hh.core.VacanciesSearchParams
import com.example.hh.filters.areafilters.presentation.screen.AreaFragment
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.views.button.CustomButtonLiveDataWrapper

class CustomAreaButtonViewModel(
    private val lastTimeButtonClicked: LastTimeButtonClicked,
    private val customButtonLiveDataWrapper: CustomButtonLiveDataWrapper<CustomAreaButtonUiState>,
    private val areaNameCache: VacanciesSearchParams.Builder,
    private val repository: FiltersRepository,
) : ViewModel(), LiveDataWrapper.GetLiveData<CustomAreaButtonUiState> {

    override fun liveData(): LiveData<CustomAreaButtonUiState> {
        return customButtonLiveDataWrapper.liveData()
    }

    fun handleClickArea(fragmentManager: FragmentManager) {
        if (lastTimeButtonClicked.timePassed()) {
            val dialogFragment = AreaFragment()
            dialogFragment.show(fragmentManager, AreaFragment.AREA_FRAGMENT_TAG)
        }
    }

    fun handleCloseAction() {

        repository.saveParams(areaNameCache.setArea(null).build())
        updateState()
      //  chosenFiltersCache.save(areaNameCache.setArea(null).build())
    }

    fun restoreDefault() {
        repository.saveParams(areaNameCache.setArea(null).build())
    }

    fun updateState() {
        customButtonLiveDataWrapper.update(CustomAreaButtonUiState.Show(repository.restoreArea()))
    }
}