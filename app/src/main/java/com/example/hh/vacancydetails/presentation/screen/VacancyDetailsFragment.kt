package com.example.hh.vacancydetails.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.databinding.FragmentVacancyDetailsBinding
import com.example.hh.main.presentation.screen.MainFragment
import com.example.hh.vacancydetails.data.LoadVacancyDetailsResult
import com.example.hh.vacancydetails.presentation.VacancyDetailsViewModel
import com.example.hh.views.progress.CustomProgressViewModel

class VacancyDetailsFragment : AbstractFragment<FragmentVacancyDetailsBinding>() {

    private var argumentFromBundleData: String? = null
    private lateinit var viewModel: VacancyDetailsViewModel

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVacancyDetailsBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argumentFromBundleData = arguments?.getString(MainFragment.STRING_KEY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.clear()


        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(VacancyDetailsViewModel::class.java.simpleName)

        viewModel.init(savedInstanceState == null, argumentFromBundleData!!)



        viewModel.map(object : VacancyDetailsViewModel.Mapper {
            override fun map(progressViewModel: CustomProgressViewModel, mapper: LoadVacancyDetailsResult.Mapper) {
                binding.progressBar.init(progressViewModel)
            }
        })

        viewModel.liveData("").observe(viewLifecycleOwner) {
            it.show(binding)
        }

        binding.retryButton.setOnClickListener {
            binding.errorLayout.visibility = View.GONE
            viewModel.init(true, argumentFromBundleData!!)
        }

      //  binding.vacancyName.text = argumentFromBundleData
        Log.d("inz", "argument is $argumentFromBundleData")
    }
}