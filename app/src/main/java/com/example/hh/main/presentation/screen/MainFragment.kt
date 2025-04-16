package com.example.hh.main.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.core.presentation.Screen
import com.example.hh.databinding.FragmentMainBinding
import com.example.hh.filters.presentation.screen.NavigateToFilters
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesViewModel
import com.example.hh.vacancydetails.presentation.screen.NavigateToVacancyDetails

class MainFragment : AbstractFragment<FragmentMainBinding>() {

    private lateinit var viewModel: VacanciesViewModel

    companion object {
        const val STRING_KEY = "KEY"
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(VacanciesViewModel::class.java.simpleName)

        viewModel.init(savedInstanceState == null || savedInstanceState.isEmpty)

        viewModel.init(object : VacanciesViewModel.Mapper {
            override fun map(
                vacanciesViewModel: VacanciesViewModel,
                liveDataWrapper: VacanciesLiveDataWrapper
            ) {
                binding.recyclerView.init(
                    vacanciesViewModel,
                    liveDataWrapper,
                    navigate = requireActivity() as NavigateToVacancyDetails,
                    backStackName = Screen.HOME_SCREEN
                )
            }
        })

        binding.textInputForSearch.setOnClickListener {
            viewModel.openSearchDialogFragment(parentFragmentManager)
        }

        binding.filterButton.setOnClickListener {
            savedInstanceState?.clear()
            viewModel.navigateToFilters(requireActivity() as NavigateToFilters)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isAdded && !requireActivity().isFinishing) {
            viewModel.save(BundleWrapper.Base(outState))
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.isEmpty) {
            viewModel.init(true)
        } else {
            if (savedInstanceState != null)
                viewModel.restore(BundleWrapper.Base(savedInstanceState))
        }
    }
}