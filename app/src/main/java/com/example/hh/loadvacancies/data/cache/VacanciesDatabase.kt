package com.example.hh.loadvacancies.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [VacancyCache::class,
        WorkFormatEntity::class,
        WorkingHoursEntity::class,
        WorkScheduleByDaysEntity::class],
    version = 2
)
abstract class VacanciesDatabase : RoomDatabase(), ClearDataBase {

    abstract fun vacanciesDao(): VacanciesDao

    override suspend fun clearAllVacancies() {
        clearAllTables()
    }

}

interface ClearDataBase {

    suspend fun clearAllVacancies()
}