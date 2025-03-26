package com.example.hh.search.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractBottomDialogFragment
import com.example.hh.databinding.SearchDialogTestBinding
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.search.presentation.SearchViewModel

class SearchFragment() : AbstractBottomDialogFragment<SearchDialogTestBinding>() {

    //    private var _binding: SearchDialogTestBinding? = null
//    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel

    companion object {
        const val TAG = "SearchFragmentBottomSheet"
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.ModalBottomSheetDialog)
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
////        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
////        bottomSheet?.let { sheet ->
////            val behavior = BottomSheetBehavior.from(sheet)
////            behavior.state = BottomSheetBehavior.STATE_EXPANDED
////            behavior.isFitToContents = false
////            behavior.peekHeight = resources.displayMetrics.heightPixels // На всю высоту экрана
////        }
//      //  return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(SearchViewModel::class.java.simpleName)

        binding.dismissButton.setOnClickListener {
            dismiss()
        }

        binding.inputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.inputSearch(binding.inputEditText.text.toString(), requireActivity() as NavigateToLoadVacancies)
//                viewModel.inputSearch(binding.inputEditText.text.toString())
               // navigate(requireActivity() as NavigateToLoadVacancies)
                dismiss()
                true
            } else
                false
        }

    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        SearchDialogTestBinding.inflate(inflater, container, false)

    private fun navigate(navigate: NavigateToLoadVacancies) = navigate.navigateToLoadVacancies()
}