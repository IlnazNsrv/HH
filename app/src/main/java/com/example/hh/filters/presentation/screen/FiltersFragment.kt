package com.example.hh.filters.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.AbstractFragment
import com.example.hh.core.ProvideViewModel
import com.example.hh.databinding.FragmentFiltersBinding
import com.example.hh.filters.presentation.CreateFilters
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import com.example.hh.filters.presentation.FiltersViewModel
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies

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
                experienceButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper,
                scheduleButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper,
                employmentButtonLiveDataWrapper: FilterButtonsLiveDataWrapper,
                searchFieldButtonLiveDataWrapper: FilterButtonsLiveDataWrapper
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
            }
        })


        binding.searchButton.setOnClickListener {
            viewModel.clickSearchVacanciesButton(requireActivity() as NavigateToLoadVacancies)
        }
//        val adapter = FiltersAdapter()
//        binding.nameRecyclerView.adapter =
    }
}