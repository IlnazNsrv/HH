package com.example.hh.vacancydetails.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hh.core.presentation.Screen

class VacancyDetailsScreen(private val vacancyId: String) : Screen.AddWithBackstack(VacancyDetailsFragment::class.java) {

    companion object {
        const val ID_KEY = "KEY"
    }

    override fun newFragment(): Fragment {
       return VacancyDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(ID_KEY, vacancyId)
            }
        }
    }

    override val backStackName: String = Screen.HOME_SCREEN
}