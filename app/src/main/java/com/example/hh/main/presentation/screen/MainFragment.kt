package com.example.hh.main.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.databinding.FragmentMainBinding
import com.example.hh.filters.presentation.screen.NavigateToFilters
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.presentation.VacanciesAdapter
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesViewModel
import com.example.hh.search.presentation.screen.SearchFragment

class MainFragment : AbstractFragment<FragmentMainBinding>() {

    private lateinit var viewModel: VacanciesViewModel

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("inz", "MainFragment onCreate pinged")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(VacanciesViewModel::class.java.simpleName)

        viewModel.init(savedInstanceState == null)

        val adapter = VacanciesAdapter(
            clickActions = viewModel,
            liveDataWrapper = VacanciesLiveDataWrapper.Base()
        )
        binding.recyclerView.setAdapter(adapter)



        viewModel.init(object : VacanciesViewModel.Mapper {
            override fun map(
                vacanciesViewModel: VacanciesViewModel,
                liveDataWrapper: VacanciesLiveDataWrapper
            ) {
                binding.recyclerView.init(vacanciesViewModel, liveDataWrapper)
            }
        })


        binding.textInputForSearch.setOnClickListener {
            viewModel.openSearchDialogFragment(parentFragmentManager)

        }

        binding.filterButton.setOnClickListener {
            val dialogFragment = SearchFragment()
            // dialogFragment.show(parentFragmentManager, SearchFragment.TAG)
            savedInstanceState?.clear()
 //           navigate(requireActivity() as NavigateToFilters)
            viewModel.navigateToFilters(requireActivity() as NavigateToFilters)

        }
    }

    private fun navigate(navigate: NavigateToFilters) = navigate.navigateToFilters()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //viewModel.save(BundleWrapper.Base(outState))
        if (isAdded && !requireActivity().isFinishing) {
            Log.d("bdl", "saved instance in MainFragment is $outState")
            viewModel.save(BundleWrapper.Base(outState))
        }
        //  binding.recyclerView.save(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(
            "bdl",
            "saveInstance is null? ${savedInstanceState == null}, is empty? ${savedInstanceState?.isEmpty}"
        )
        if (savedInstanceState != null && savedInstanceState.isEmpty) {
//            Log.d("bdl", "restore instance in MainFragment is $savedInstanceState")
            viewModel.init(true)
//            // binding.recyclerView.restore(savedInstanceState)
        } else {
            if (savedInstanceState != null)
                viewModel.restore(BundleWrapper.Base(savedInstanceState))
        }
    }
}