package com.example.hh.filters.presentation.screen

import com.example.hh.core.presentation.Screen

object FiltersScreen : Screen.ReplaceWithBackstack(FiltersFragment::class.java) {
    override val backStackName: String = Screen.HOME_SCREEN
}