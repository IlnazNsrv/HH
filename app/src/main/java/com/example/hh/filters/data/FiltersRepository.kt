package com.example.hh.filters.data

import com.example.hh.core.VacanciesSearchParams
import com.example.hh.filters.data.cache.ChosenFiltersCache

interface FiltersRepository {

    fun saveParams(vacanciesSearchParams: VacanciesSearchParams)
    fun restoreArea(): Pair<String, String>?

    class Base(
        private val chosenFiltersCache: ChosenFiltersCache
    ) : FiltersRepository {

        override fun saveParams(vacanciesSearchParams: VacanciesSearchParams) {
            chosenFiltersCache.save(vacanciesSearchParams)
        }

        override fun restoreArea(): Pair<String, String>? {
            return chosenFiltersCache.read().area
        }
    }
}