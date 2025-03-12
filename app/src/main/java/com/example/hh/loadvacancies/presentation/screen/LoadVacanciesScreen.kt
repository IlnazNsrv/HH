package com.example.hh.loadvacancies.presentation.screen

import com.example.hh.core.presentation.Screen

object LoadVacanciesScreen : Screen.ReplaceWithBackstack(LoadVacanciesFragment::class.java) {
    override val backStackName: String = "home"
}