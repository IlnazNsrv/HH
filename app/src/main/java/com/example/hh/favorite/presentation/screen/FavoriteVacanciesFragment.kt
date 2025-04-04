package com.example.hh.favorite.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.core.presentation.Screen
import com.example.hh.databinding.FragmentFavoriteVacanciesBinding
import com.example.hh.favorite.presentation.FavoriteVacanciesViewModel
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.vacancydetails.presentation.screen.NavigateToVacancyDetails

class FavoriteVacanciesFragment : AbstractFragment<FragmentFavoriteVacanciesBinding>() {

    private lateinit var viewModel: FavoriteVacanciesViewModel

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFavoriteVacanciesBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity().application as ProvideViewModel).viewModel(FavoriteVacanciesViewModel::class.java.simpleName)

        viewModel.init()

        viewModel.init(object : FavoriteVacanciesViewModel.Mapper {
            override fun map(
                favoriteVacanciesViewModel: FavoriteVacanciesViewModel,
                liveDataWrapper: VacanciesLiveDataWrapper
            ) {
                binding.recyclerView.init(favoriteVacanciesViewModel, liveDataWrapper, navigate = requireActivity() as NavigateToVacancyDetails, backStackName = Screen.HOME_SCREEN)
            }
        })
    }
}