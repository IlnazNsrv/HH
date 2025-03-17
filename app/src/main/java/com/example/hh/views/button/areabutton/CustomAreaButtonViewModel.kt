package com.example.hh.views.button.areabutton

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hh.core.LiveDataWrapper
import com.example.hh.filters.areafilters.presentation.screen.AreaFragment
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.search.presentation.VacanciesSearchParams
import com.example.hh.views.button.CustomButtonLiveDataWrapper

class CustomAreaButtonViewModel(
    private val customButtonLiveDataWrapper: CustomButtonLiveDataWrapper<CustomAreaButtonUiState>,
    private val areaNameCache: VacanciesSearchParams.Builder,
    private val repository: FiltersRepository,
) : ViewModel(), LiveDataWrapper.GetLiveData<CustomAreaButtonUiState> {

    override fun liveData(): LiveData<CustomAreaButtonUiState> {
        return customButtonLiveDataWrapper.liveData()
    }

    fun handleClickArea(fragmentManager: FragmentManager) {
        customButtonLiveDataWrapper.update(CustomAreaButtonUiState.Show(repository.restoreArea()))
        val dialogFragment = AreaFragment()
        dialogFragment.show(fragmentManager, AreaFragment.AREA_FRAGMENT_TAG)
    }

    fun handleCloseAction() {
        repository.saveParams(areaNameCache.setArea(null).build())
      //  chosenFiltersCache.save(areaNameCache.setArea(null).build())

    }
}