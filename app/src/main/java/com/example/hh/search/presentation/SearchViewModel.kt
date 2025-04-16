package com.example.hh.search.presentation

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.hh.core.ClearViewModel
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.VacanciesSearchParams
import com.example.hh.filters.areafilters.presentation.screen.AreaFragment
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel

class SearchViewModel(
    private val clearViewModel: ClearViewModel,
    private val searchParams: VacanciesSearchParams.Builder,
    private val chosenFiltersCache: ChosenFiltersCache,
    private val customAreaButtonViewModel: CustomAreaButtonViewModel,
    private val lastTimeButtonClicked: LastTimeButtonClicked
) : ViewModel() {

    fun inputSearch(text: String, navigate: NavigateToLoadVacancies) {
        searchParams.setSearchText(text)
        searchParams.setArea(chosenFiltersCache.read().area)
        chosenFiltersCache.save(searchParams.build())
        navigate.navigateToLoadVacancies()
    }

    interface Mapper {
        fun map(
            customAreaButtonViewModel: CustomAreaButtonViewModel,
        )
    }

    fun init(mapper: Mapper) {
        mapper.map(
            customAreaButtonViewModel,
        )
    }

    fun clearViewModel() {
        clearViewModel.clear(this::class.java.simpleName)
    }

    fun openAreaDialogFragment(fragmentManager: FragmentManager) {
        if (lastTimeButtonClicked.timePassed()) {
            val dialogFragment = AreaFragment()
            dialogFragment.show(fragmentManager, AreaFragment.AREA_FRAGMENT_TAG)
        }
    }
}
