package com.example.hh.filters.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.databinding.FragmentFiltersBinding
import com.example.hh.filters.areafilters.presentation.screen.AreaFragment
import com.example.hh.filters.presentation.CreateFilters
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import com.example.hh.filters.presentation.FiltersViewModel
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel

class FiltersFragment : AbstractFragment<FragmentFiltersBinding>() {

    private lateinit var viewModel: FiltersViewModel

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFiltersBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(FiltersViewModel::class.java.simpleName)
        viewModel.init()

        viewModel.init(object : FiltersViewModel.Mapper {
            override fun map(
                filtersViewModel: FiltersViewModel,
                experienceButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
                scheduleButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
                employmentButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
                searchFieldButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
                customAreaButtonViewModel: CustomAreaButtonViewModel
            ) {
                binding.nameRecyclerView.initButtons(
                    viewModel,
                    searchFieldButtonLiveDataWrapper,
                    CreateFilters.SEARCH_FIELD_TAG
                )
                binding.scheduleRecyclerView.initButtons(
                    viewModel,
                    scheduleButtonsLiveDataWrapper,
                    CreateFilters.SCHEDULE_TAG
                )
                binding.employmentRecyclerView.initButtons(
                    viewModel,
                    employmentButtonLiveDataWrapper,
                    CreateFilters.EMPLOYMENT_TAG
                )
                binding.experienceRecyclerView.initButtons(
                    viewModel,
                    experienceButtonsLiveDataWrapper,
                    CreateFilters.EXPERIENCE_TAG
                )
                binding.customAreaButton.init(customAreaButtonViewModel, parentFragmentManager)
            }
        })

        binding.cityFragmentButton.setOnClickListener {
            val dialogFragment = AreaFragment()
            dialogFragment.show(parentFragmentManager, AreaFragment.AREA_FRAGMENT_TAG)
        }


        binding.searchButton.setOnClickListener {
            viewModel.clickSearchVacanciesButton(requireActivity() as NavigateToLoadVacancies)
        }

//        binding.cityName.setOnClickListener {
//            binding.cityName.text = "Текст появился"
//
//        }
//
//        binding.removeCity.setOnClickListener {
//            binding.cityName.text = ""
//            binding.cityLayout.visibility = View.GONE
//
//        }
//        val adapter = FiltersAdapter()
//        binding.nameRecyclerView.adapter =
    }
}