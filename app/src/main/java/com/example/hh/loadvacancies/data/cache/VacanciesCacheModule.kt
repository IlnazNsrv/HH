package com.example.hh.loadvacancies.data.cache

import android.content.Context
import androidx.room.Room

class VacanciesCacheModule(context: Context) {

    private val dataBase: VacanciesDatabase by lazy {
        Room.databaseBuilder(
            context,
            VacanciesDatabase::class.java,
            VacanciesDatabase::class.java.simpleName
        ).build()
    }

    fun dao() : VacanciesDao {
        return dataBase.vacanciesDao()
    }
}