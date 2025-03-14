package com.example.hh.filters.data

import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.search.presentation.VacanciesSearchParams

interface FiltersRepository {

    fun saveParams(vacanciesSearchParams: VacanciesSearchParams)

    class Base(
        private val chosenFiltersCache: ChosenFiltersCache
    ) : FiltersRepository {

        override fun saveParams(vacanciesSearchParams: VacanciesSearchParams) {
            chosenFiltersCache.save(vacanciesSearchParams)
        }
    }
}