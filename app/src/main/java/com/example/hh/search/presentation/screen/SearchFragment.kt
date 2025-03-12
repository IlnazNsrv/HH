package com.example.hh.search.presentation.screen

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.example.hh.R
import com.example.hh.core.ProvideViewModel
import com.example.hh.databinding.SearchDialogTestBinding
import com.example.hh.loadvacancies.presentation.LoadVacanciesViewModel
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchFragment() : BottomSheetDialogFragment() {

    private var _binding: SearchDialogTestBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoadVacanciesViewModel

    companion object {
        const val TAG = "SearchFragmentBottomSheet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setStyle(STYLE_NORMAL, R.style.ModalBottomSheetDialog)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchDialogTestBinding.inflate(inflater, container, false)
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let { sheet ->
            val behavior = BottomSheetBehavior.from(sheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isFitToContents = false
            behavior.peekHeight = resources.displayMetrics.heightPixels // На всю высоту экрана
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(LoadVacanciesViewModel::class.java.simpleName)

        binding.dismissButton.setOnClickListener {
            dismiss()
        }

        binding.inputEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == EditorInfo.IME_ACTION_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.ACTION_DOWN || event.action == KeyEvent.ACTION_DOWN)  {
                viewModel.inputSearch(binding.inputEditText.text.toString())
                navigate(requireActivity() as NavigateToLoadVacancies)
                dismiss()
                true
            } else
                false
        }

        binding.inputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER ||
                        event.action == KeyEvent.ACTION_DOWN))  {
                viewModel.inputSearch(binding.inputEditText.text.toString())
                navigate(requireActivity() as NavigateToLoadVacancies)
                dismiss()
                true
            } else
                false
        }

//        viewModel.map(object: SearchViewModel.Mapper {
//            override fun map(inputViewModel: CustomInputViewModel) {
//                binding.inputView.init(inputViewModel, requireActivity() as NavigateToLoadVacancies)
//            }
//        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate(navigate: NavigateToLoadVacancies) = navigate.navigateToLoadVacancies()
}