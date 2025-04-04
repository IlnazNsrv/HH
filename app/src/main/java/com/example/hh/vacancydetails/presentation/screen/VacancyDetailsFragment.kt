package com.example.hh.vacancydetails.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.databinding.FragmentVacancyDetailsBinding
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.presentation.screen.MainFragment
import com.example.hh.vacancydetails.presentation.VacancyDetailsViewModel

class VacancyDetailsFragment : AbstractFragment<FragmentVacancyDetailsBinding>() {

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var argumentFromBundleData: String? = null
    private lateinit var viewModel: VacancyDetailsViewModel


    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVacancyDetailsBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argumentFromBundleData = arguments?.getString(MainFragment.STRING_KEY)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
                viewModel.clearViewModel()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            onBackPressedCallback = onBackPressedCallback
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(VacancyDetailsViewModel::class.java.simpleName)

        viewModel.init(savedInstanceState == null, argumentFromBundleData!!)

        viewModel.liveData("").observe(viewLifecycleOwner) {
            it.show(binding)
        }

        binding.retryButton.setOnClickListener {
            binding.errorLayout.visibility = View.GONE
            viewModel.loadVacancyDetails()
        }

        binding.favoriteButton.setOnClickListener {
            viewModel.clickFavorite()
        }

        binding.backButton.setOnClickListener {
           parentFragmentManager.popBackStack()
            viewModel.clearViewModel()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isAdded && !requireActivity().isFinishing) {
            outState.putInt(POSITION_KEY, binding.scrollView.scrollY)
            viewModel.save(BundleWrapper.Base(outState))
        }
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.size() > 0) {
            viewModel.restore(BundleWrapper.Base(savedInstanceState))
            binding.scrollView.post {
                binding.scrollView.scrollTo(0, savedInstanceState.getInt(POSITION_KEY))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove() // Удаляем коллбек
    }

    companion object {
        private const val POSITION_KEY = "POSITION"
    }
}