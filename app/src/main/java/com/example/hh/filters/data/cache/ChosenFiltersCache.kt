package com.example.hh.filters.data.cache

import android.content.SharedPreferences
import com.example.hh.search.presentation.VacanciesSearchParams
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

        val gson = Gson()

        override fun read(): VacanciesSearchParams {
            val paramsJson = sharedPreferences.getString(KEY, null)
            if (paramsJson != null) {
                val searchParams = gson.fromJson(paramsJson, VacanciesSearchParams::class.java)
                return searchParams
            } else {
                val defaultSearchParams = VacanciesSearchParams.Builder()
                return defaultSearchParams.build()
            }

//            val paramsBytes = android.util.Base64.decode(paramsBase64, android.util.Base64.DEFAULT)
//
//            val byteArrayInputStream = ByteArrayInputStream(paramsBytes)
//            val objectInputStream = ObjectInputStream(byteArrayInputStream)
//            val params = objectInputStream.readObject() as VacanciesSearchParams
//            objectInputStream.close()

        }

        override fun save(vacanciesSearchParams: VacanciesSearchParams) {
            val editor = sharedPreferences.edit()

            val json = gson.toJson(vacanciesSearchParams)
            editor.putString(KEY, json).apply()


//            val byteArrayOutputStream = ByteArrayOutputStream()
//            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
//            objectOutputStream.writeObject(vacanciesSearchParams)
//            objectOutputStream.close()
//            val paramsBytes = byteArrayOutputStream.toByteArray()
//
//            val paramsBase64 =
//                android.util.Base64.encodeToString(paramsBytes, android.util.Base64.DEFAULT)
//            editor.putString(KEY, paramsBase64)
//            editor.apply()
        }
    }
}