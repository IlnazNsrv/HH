package com.example.hh.favorite.data.cache

import android.content.Context
import androidx.room.Room

class FavoriteVacanciesCacheModule(context: Context) {

    private val database: FavoriteVacanciesDatabase by lazy {
        Room.databaseBuilder(
            context = context,
            FavoriteVacanciesDatabase::class.java,
            FavoriteVacanciesDatabase::class.java.simpleName
        ).build()
    }

    fun favoriteVacanciesDao() : FavoriteVacanciesDao {
        return database.favoriteVacanciesDao()
    }
}