package com.example.hh.filters.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.core.presentation.Screen
import com.example.hh.databinding.FragmentFiltersBinding
import com.example.hh.filters.presentation.CreateFilters
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper
import com.example.hh.filters.presentation.FiltersViewModel
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.main.data.BundleWrapper
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel

class FiltersFragment : AbstractFragment<FragmentFiltersBinding>() {

    private lateinit var viewModel: FiltersViewModel

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFiltersBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(FiltersViewModel::class.java.simpleName)

        viewModel.init(savedInstanceState == null)



        viewModel.init(object : FiltersViewModel.Mapper {
            override fun map(
                filtersViewModel: FiltersViewModel,
                experienceButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
                scheduleButtonsLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
                employmentButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
                searchFieldButtonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>,
                customAreaButtonViewModel: CustomAreaButtonViewModel,

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

        binding.resetFiltersButton.setOnClickListener {
            binding.onlyWithSalarySwitchButton.isChecked = false
            viewModel.resetAllParams()
        }

        binding.cityFragmentButton.setOnClickListener {
            viewModel.openAreaDialogFragment(parentFragmentManager)
        }

        binding.backButton.setOnClickListener {
            savedInstanceState?.clear()
            navigateToHome()
        }

        binding.searchButton.setOnClickListener {
            val inputNumber = binding.inputSalary.text.toString()
            val inputString = binding.inputVacancyEditText.text.toString()
            viewModel.searchVacancies(
                inputString,
                inputNumber.toIntOrNull(),
                requireActivity() as NavigateToLoadVacancies
            )
            Log.d("inz", "text is: ${inputString}, number is ${inputNumber.toIntOrNull()}")
        }

        binding.onlyWithSalarySwitchButton.setOnCheckedChangeListener { _, isChecked ->
            viewModel.switchSalaryParams(isChecked)
        }

    }

    private fun navigateToHome() {
        parentFragmentManager.popBackStack(
            Screen.HOME_SCREEN,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isAdded && !requireActivity().isFinishing)
            viewModel.save(BundleWrapper.Base(outState))
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

    override fun onDestroyView() {
        super.onDestroyView()
    }
}