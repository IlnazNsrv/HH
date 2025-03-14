package com.example.hh.filters.data.cache

import android.content.SharedPreferences
import com.example.hh.search.presentation.VacanciesSearchParams
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

interface ChosenFiltersCache {

    fun read(): VacanciesSearchParams
    fun save(vacanciesSearchParams: VacanciesSearchParams)

    class Base(
        private val sharedPreferences: SharedPreferences
    ) : ChosenFiltersCache {

        companion object {
            private const val KEY = "paramsKey"
        }

        override fun read(): VacanciesSearchParams {
            val paramsBase64 = sharedPreferences.getString(KEY, "")

            val paramsBytes = android.util.Base64.decode(paramsBase64, android.util.Base64.DEFAULT)

            val byteArrayInputStream = ByteArrayInputStream(paramsBytes)
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            val params = objectInputStream.readObject() as VacanciesSearchParams
            objectInputStream.close()

            return params
        }

        override fun save(vacanciesSearchParams: VacanciesSearchParams) {
            val editor = sharedPreferences.edit()

            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(vacanciesSearchParams)
            objectOutputStream.close()
            val paramsBytes = byteArrayOutputStream.toByteArray()

            val paramsBase64 =
                android.util.Base64.encodeToString(paramsBytes, android.util.Base64.DEFAULT)
            editor.putString(KEY, paramsBase64)
            editor.apply()
        }
    }
}