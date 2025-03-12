package com.example.hh.loadvacancies.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.hh.core.AbstractFragment
import com.example.hh.core.ProvideViewModel
import com.example.hh.databinding.FragmentLoadVacanciesBinding
import com.example.hh.loadvacancies.presentation.LoadVacanciesViewModel
import com.example.hh.main.presentation.VacanciesLiveDataWrapper

class LoadVacanciesFragment : AbstractFragment<FragmentLoadVacanciesBinding>() {

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var viewModel: LoadVacanciesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToHome()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback = onBackPressedCallback)
    }

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoadVacanciesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            navigateToHome()
        }
        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(LoadVacanciesViewModel::class.java.simpleName)

        viewModel.init(object : LoadVacanciesViewModel.Mapper {
            override fun map(
                loadVacanciesViewModel: LoadVacanciesViewModel,
                liveDataWrapper: VacanciesLiveDataWrapper
            ) {
                binding.recyclerView.initSearchFragment(loadVacanciesViewModel, liveDataWrapper)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        // Отключаем колбэк при уничтожении фрагмента
        onBackPressedCallback.remove()
    }

    private fun navigateToHome() {
        parentFragmentManager.popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}