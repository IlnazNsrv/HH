package com.example.hh.loadvacancies.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [VacancyCache::class,
        WorkFormatEntity::class,
        WorkingHoursEntity::class,
        WorkScheduleByDaysEntity::class],
    version = 1
)
abstract class VacanciesDatabase : RoomDatabase() {

    abstract fun vacanciesDao(): VacanciesDao

}

interface ClearDataBase {

    suspend fun clearWithoutIsFavorite()
}