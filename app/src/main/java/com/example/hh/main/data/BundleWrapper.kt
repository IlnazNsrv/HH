package com.example.hh.main.data

import android.os.Build
import android.os.Bundle
import com.example.hh.main.presentation.VacanciesUiState

interface BundleWrapper {

    companion object {
        const val KEY = "vacancyList"
    }

    interface Save {
        fun save(vacancyList: VacanciesUiState)
    }

    interface Restore {
        fun restore(): VacanciesUiState
    }

    interface Mutable : Save, Restore

    class Base(private val bundle: Bundle) : Mutable {
        override fun save(vacancyList: VacanciesUiState) {
            bundle.putSerializable(KEY, vacancyList)
        }

        override fun restore(): VacanciesUiState {
            val vacancyList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(KEY) as VacanciesUiState
            } else bundle.getSerializable(KEY) as VacanciesUiState
            return vacancyList
        }

    }
}