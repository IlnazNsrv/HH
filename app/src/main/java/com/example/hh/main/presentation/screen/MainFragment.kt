package com.example.hh.main.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hh.R
import com.example.hh.core.ProvideViewModel
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.presentation.CustomRecyclerView
import com.example.hh.main.presentation.VacanciesViewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var viewModel: VacanciesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).viewModel(VacanciesViewModel::class.java.simpleName)

        viewModel.init(savedInstanceState == null)

        //val errorTextView = view.findViewById<CustomTextView>(R.id.errorText)
        // val retryButton = view.findViewById<CustomButton>(R.id.retryButton)
//        val vacancyTitleTextView = view.findViewById<CustomTextView>(R.id.vacancyTitle)
//        val vacancySalaryTextView = view.findViewById<CustomTextView>(R.id.salary)
//        val vacancyCityTextView = view.findViewById<CustomTextView>(R.id.city)
//        val vacancyCompanyNameTextView = view.findViewById<CustomTextView>(R.id.companyName)
//        val vacancyExperienceTextView = view.findViewById<CustomTextView>(R.id.experience)
//        val vacancyFavoriteButton = view.findViewById<CustomButton>(R.id.favoriteButton)
//        val vacancyRespondButton = view.findViewById<CustomButton>(R.id.respondButton)
        val recyclerView = view.findViewById<CustomRecyclerView>(R.id.recyclerView)

        viewModel.init(object : VacanciesViewModel.Mapper {
            override fun map(
                //errorTextViewModel: CustomTextViewModel,
                //retryButtonViewModel: CustomButtonViewModel,
//                vacancyTitleViewModel: CustomTextViewModel,
//                vacancySalaryViewModel: CustomTextViewModel,
//                vacancyCityViewModel: CustomTextViewModel,
//                vacancyCompanyNameViewModel: CustomTextViewModel,
//                vacancyExperienceViewModel: CustomTextViewModel,
//                vacancyFavoriteButtonViewModel: CustomButtonViewModel,
//                vacancyRespondButtonViewModel: CustomButtonViewModel,
                vacanciesViewModel: VacanciesViewModel
            ) {
                //errorTextView.init(errorTextViewModel)
                //retryButton.init(retryButtonViewModel)
//                vacancyTitleTextView.init(vacancyTitleViewModel)
//                vacancySalaryTextView.init(vacancySalaryViewModel)
//                vacancyCityTextView.init(vacancyCityViewModel)
//                vacancyCompanyNameTextView.init(vacancyCompanyNameViewModel)
//                vacancyExperienceTextView.init(vacancyExperienceViewModel)
//                vacancyFavoriteButton.init(vacancyFavoriteButtonViewModel)
//                vacancyRespondButton.init(vacancyRespondButtonViewModel)
                recyclerView.init(vacanciesViewModel)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.save(BundleWrapper.Base(outState))

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
            viewModel.restore(BundleWrapper.Base(savedInstanceState))
    }
}