package com.example.hh.core.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    companion object {
        const val HOME_SCREEN = "home"
        const val FILTERS_SCREEN = "filters"
        const val LOAD_VACANCIES_SCREEN = "vacancies"
    }

    fun show(containerId: Int, fragmentManager: FragmentManager) = Unit

    abstract class AddWithBackstack(private val fragment: Class<out Fragment>) : Screen {

        protected open val backStackName: String = ""

        protected open fun newFragment() : Fragment = fragment.getConstructor().newInstance()

        override fun show(containerId: Int, fragmentManager: FragmentManager) {

            fragmentManager.beginTransaction()
                .add(containerId, newFragment())
                .addToBackStack(backStackName)
                .commit()
        }
    }

    abstract class ReplaceWithBackStack(fragment: Class<out Fragment>) : AddWithBackstack(fragment) {

        override fun show(containerId: Int, fragmentManager: FragmentManager) {

            fragmentManager.beginTransaction()
                .replace(containerId, newFragment())
                .addToBackStack(backStackName)
                .commit()
        }
    }
}