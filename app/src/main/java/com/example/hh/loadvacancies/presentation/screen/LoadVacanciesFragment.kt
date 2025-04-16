package com.example.hh.loadvacancies.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hh.R
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.core.presentation.Screen
import com.example.hh.databinding.FragmentLoadVacanciesBinding
import com.example.hh.loadvacancies.presentation.LoadVacanciesViewModel
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.vacancydetails.presentation.screen.NavigateToVacancyDetails

class LoadVacanciesFragment : AbstractFragment<FragmentLoadVacanciesBinding>() {

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var viewModel: LoadVacanciesViewModel
    private lateinit var simpleItems: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.clearVacancies()
                navigateToFilters()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            onBackPressedCallback = onBackPressedCallback
        )
    }

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoadVacanciesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleItems = resources.getStringArray(R.array.simple_items)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(LoadVacanciesViewModel::class.java.simpleName)

        viewModel.init(savedInstanceState == null)

        viewModel.init(object : LoadVacanciesViewModel.Mapper {
            override fun map(
                loadVacanciesViewModel: LoadVacanciesViewModel,
                liveDataWrapper: VacanciesLiveDataWrapper
            ) {
                binding.recyclerView.init(
                    loadVacanciesViewModel,
                    liveDataWrapper,
                    navigate = requireActivity() as NavigateToVacancyDetails,
                    backStackName = Screen.LOAD_VACANCIES_SCREEN,
                )
            }
        })

        binding.filtersAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            viewModel.clickFilters(selectedItem)
            binding.recyclerView.post {
                binding.recyclerView.scrollToPosition(0)
            }
        }

        binding.backButton.setOnClickListener {
            viewModel.clearVacancies()
            navigateToFilters()
        }
    }

    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.simple_items)
        )
        binding.filtersAutoCompleteTextView.setAdapter(adapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isAdded && !requireActivity().isFinishing) {
            val layoutManager = binding.recyclerView.getLayoutManager() as LinearLayoutManager
            outState.putInt(RECYCLER_POSITION_KEY, layoutManager.findFirstVisibleItemPosition())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.size() > 0 && isAdded && !requireActivity().isFinishing) {
            binding.recyclerView.post {
                binding.recyclerView.scrollToPosition(savedInstanceState.getInt(
                    RECYCLER_POSITION_KEY))
            }
        }
    }

    private fun navigateToFilters() {
        parentFragmentManager.popBackStack(
            Screen.FILTERS_SCREEN,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {
        private const val RECYCLER_POSITION_KEY = "RV_KEY"
    }
}