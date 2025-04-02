package com.example.hh.loadvacancies.presentation.screen

import com.example.hh.core.presentation.Screen

object LoadVacanciesScreen : Screen.ReplaceWithBackStack(LoadVacanciesFragment::class.java) {
    override val backStackName: String = Screen.FILTERS_SCREEN
}