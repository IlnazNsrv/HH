package com.example.hh.main.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.AbstractFragment
import com.example.hh.core.ProvideViewModel
import com.example.hh.databinding.FragmentMainBinding
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesViewModel
import com.example.hh.search.presentation.screen.SearchFragment

class MainFragment : AbstractFragment<FragmentMainBinding>() {

    private lateinit var viewModel: VacanciesViewModel

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(VacanciesViewModel::class.java.simpleName)

        viewModel.init(savedInstanceState == null)

        viewModel.init(object : VacanciesViewModel.Mapper {
            override fun map(
                vacanciesViewModel: VacanciesViewModel,
                liveDataWrapper: VacanciesLiveDataWrapper
            ) {
                binding.recyclerView.init(vacanciesViewModel, liveDataWrapper)
            }
        })

        binding.filterButton.setOnClickListener {
            val dialogFragment = SearchFragment()
            dialogFragment.show(parentFragmentManager, SearchFragment.TAG)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //viewModel.save(BundleWrapper.Base(outState))
        binding.recyclerView.save(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
//            viewModel.restore(BundleWrapper.Base(savedInstanceState))
            binding.recyclerView.restore(savedInstanceState)
        }
    }
}