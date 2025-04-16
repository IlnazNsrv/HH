package com.example.hh.filters.data.cache

import android.content.SharedPreferences
import com.example.hh.core.VacanciesSearchParams
import com.google.gson.Gson

interface ChosenFiltersCache {

    fun read(): VacanciesSearchParams
    fun save(vacanciesSearchParams: VacanciesSearchParams)

    class Base(
        private val sharedPreferences: SharedPreferences
    ) : ChosenFiltersCache {

        companion object {
            private const val KEY = "paramsKey"
        }

        private val gson = Gson()

        override fun read(): VacanciesSearchParams {
            val paramsJson = sharedPreferences.getString(KEY, null)
            if (paramsJson != null) {
                val searchParams = gson.fromJson(paramsJson, VacanciesSearchParams::class.java)
                return searchParams
            } else {
                val defaultSearchParams = VacanciesSearchParams.Builder()
                return defaultSearchParams.build()
            }
        }

        override fun save(vacanciesSearchParams: VacanciesSearchParams) {
            val editor = sharedPreferences.edit()

            val json = gson.toJson(vacanciesSearchParams)
            editor.putString(KEY, json).apply()
        }
    }
}