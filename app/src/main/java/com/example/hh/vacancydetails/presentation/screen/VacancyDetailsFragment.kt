package com.example.hh.vacancydetails.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.databinding.FragmentVacancyDetailsBinding
import com.example.hh.main.presentation.screen.MainFragment

class VacancyDetailsFragment : AbstractFragment<FragmentVacancyDetailsBinding>() {

    private var argumentFromBundleData: String? = null

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
       // val data = VacancyDetailsFragmentArgs.fromBundle(requireArguments()).vacancyId

        //argumentFromBundleData = arguments?.getString(MainFragment.STRING_KEY)
        binding.vacancyName.text = argumentFromBundleData
        Log.d("inz", "argument is $argumentFromBundleData")
    }
}