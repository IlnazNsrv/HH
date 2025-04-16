package com.example.hh.search.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractBottomDialogFragment
import com.example.hh.databinding.FragmentSearchDialogBinding
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.search.presentation.SearchViewModel
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel

class SearchFragment() : AbstractBottomDialogFragment<FragmentSearchDialogBinding>() {

    private lateinit var viewModel: SearchViewModel

    companion object {
        const val TAG = "SearchFragmentBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(SearchViewModel::class.java.simpleName)

        viewModel.init(object : SearchViewModel.Mapper {
            override fun map(customAreaButtonViewModel: CustomAreaButtonViewModel) {
                binding.cityButton.init(customAreaButtonViewModel, parentFragmentManager)
            }
        })

        binding.dismissButton.setOnClickListener {
            dismiss()
            viewModel.clearViewModel()
        }

        binding.addAreaButton.setOnClickListener {
            viewModel.openAreaDialogFragment(parentFragmentManager)
        }

        binding.inputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.inputSearch(
                    binding.inputEditText.text.toString(),
                    requireActivity() as NavigateToLoadVacancies
                )
                dismiss()
                viewModel.clearViewModel()
                true
            } else
                false
        }

    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSearchDialogBinding.inflate(inflater, container, false)
}