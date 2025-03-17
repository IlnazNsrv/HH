package com.example.hh.filters.areafilters.presentation.screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractBottomDialogFragment
import com.example.hh.databinding.FragmentAreaBinding
import com.example.hh.filters.presentation.FilterButtonUi
import com.example.hh.filters.presentation.FilterButtonsLiveDataWrapper

class AreaFragment() : AbstractBottomDialogFragment<FragmentAreaBinding>() {

    companion object {
        const val AREA_FRAGMENT_TAG = "Area Fragment"
    }

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    private lateinit var viewModel: AreaViewModel

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit


        override fun afterTextChanged(s: Editable?) {
            searchRunnable?.let { handler.removeCallbacks(it) }
            searchRunnable = Runnable {
                viewModel.handleUserInput(text = s.toString())
            }
             //text для проверки дошли мы до нужного количества букв или нет
            handler.postDelayed(searchRunnable!!, 300)
        }

    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAreaBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(AreaViewModel::class.java.simpleName)

        viewModel.loadAreas(binding.inputEditText.text.toString())

        viewModel.map(object : AreaViewModel.Mapper {
            override fun map(
                viewModel: AreaViewModel,
                buttonLiveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>
            ) {
                binding.areaRecyclerView.initButtons(viewModel, buttonLiveDataWrapper, "")
            }
        })

        binding.saveArea.setOnClickListener {
            viewModel.saveArea()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.inputEditText.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.inputEditText.removeTextChangedListener(textWatcher)
    }


}