package com.example.hh.loadvacancies.presentation.screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("inz", "load fragment attached")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.clearVacancies()
                navigateToHome()
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
        binding.backButton.setOnClickListener {
            viewModel.clearVacancies()
            navigateToHome()
        }

        simpleItems = resources.getStringArray(R.array.simple_items)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(LoadVacanciesViewModel::class.java.simpleName)

        viewModel.init(savedInstanceState == null)

        viewModel.init(object : LoadVacanciesViewModel.Mapper {
            override fun map(
                loadVacanciesViewModel: LoadVacanciesViewModel,
                liveDataWrapper: VacanciesLiveDataWrapper
            ) {
                //  binding.recyclerView.initSearchFragment(loadVacanciesViewModel, liveDataWrapper)
                binding.recyclerView.init(loadVacanciesViewModel, liveDataWrapper, navigate = requireActivity() as NavigateToVacancyDetails)
            }
        })

        binding.filtersAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Log.d("inz", "selectedItem is $selectedItem")
            compareSelectedItems(selectedItem)
            viewModel.clickFilters(selectedItem)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Отключаем колбэк при уничтожении фрагмента
        onBackPressedCallback.remove()
        Log.d("inz", "LoadVacanciesFragment was destroyed")
        // viewModel.clearVacancies()
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("inz", "LoadFragment was detached")
    }

    private fun navigateToHome() {
        parentFragmentManager.popBackStack(
            Screen.FILTERS_SCREEN,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun compareSelectedItems(selectedItem: String) {
        when(selectedItem) {
            simpleItems[0] -> Log.d("inz", "Default chosen")
            simpleItems[1] -> Log.d("inz", "Decrease chosen")
            simpleItems[2] -> Log.d("inz", "Increase chosen")
        }

    }
}