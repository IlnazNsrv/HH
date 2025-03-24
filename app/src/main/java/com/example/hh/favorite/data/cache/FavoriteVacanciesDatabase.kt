package com.example.hh.favorite.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteVacancyCache::class,
    FavoriteWorkFormatEntity::class,
    FavoriteWorkingHoursEntity::class,
    FavoriteWorkScheduleByDaysEntity::class],
    version = 1
)
abstract class FavoriteVacanciesDatabase : RoomDatabase() {

    abstract fun favoriteVacanciesDao() : FavoriteVacanciesDao
}