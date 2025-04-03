package com.example.hh.filters.presentation.screen

import com.example.hh.core.presentation.Screen

object FiltersScreen : Screen.ReplaceWithBackStack(FiltersFragment::class.java) {
    override val backStackName: String = Screen.HOME_SCREEN
}