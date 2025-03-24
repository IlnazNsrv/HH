package com.example.hh.favorite.data.cache

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.hh.loadvacancies.data.cache.VacancyCache

@Dao
interface FavoriteVacanciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVacancies(vacancies: List<FavoriteVacancyCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVacancy(vacancy: FavoriteVacancyCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorkFormat(workFormat: List<FavoriteWorkFormatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorkingHours(workingHours: List<FavoriteWorkingHoursEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorkScheduleByDays(workScheduleByDays: List<FavoriteWorkScheduleByDaysEntity>)

    @Query("SELECT * FROM favorite_table WHERE isFavorite = 1")
    suspend fun getFavoriteVacancies() : List<FavoriteVacancyCache>

    @Query("SELECT * FROM favorite_table WHERE id=:id")
    suspend fun getFavoriteVacancy(id: String) : FavoriteVacancyCache

    @Query("DELETE FROM favorite_table WHERE isFavorite=0")
    suspend fun deleteNonFavoriteVacancies()

    @Transaction
    @Query("SELECT * FROM favorite_table")
    suspend fun getAllVacancies() : List<FavoriteVacancyWithDetails>

    data class FavoriteVacancyWithDetails(
        @Embedded val vacancy: VacancyCache,
        @Relation(
            parentColumn = "id",
            entityColumn = "vacancyId"
        )
        val workFormats: List<FavoriteWorkFormatEntity>?,
        @Relation(
            parentColumn = "id",
            entityColumn = "vacancyId"
        )
        val workingHours: List<FavoriteWorkingHoursEntity>?,
        @Relation(
            parentColumn = "id",
            entityColumn = "vacancyId"
        )
        val workBySchedule: List<FavoriteWorkScheduleByDaysEntity>?
    )
}