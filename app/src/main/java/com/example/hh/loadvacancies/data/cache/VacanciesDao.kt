package com.example.hh.loadvacancies.data.cache

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction

@Dao
interface VacanciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVacancies(vacancies: List<VacancyCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorkFormat(workFormat: List<WorkFormatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorkingHours(workingHours: List<WorkingHoursEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorkScheduleByDays(workScheduleByDays: List<WorkScheduleByDaysEntity>)

    @Query("SELECT * FROM vacancies_table")
    suspend fun vacancy(): List<VacancyCache>

    @Query("UPDATE vacancies_table SET isFavorite=:isFavorite WHERE id=:id")
    suspend fun updateFavoriteState(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM vacancies_table WHERE id=:vacancyId")
    suspend fun getVacancy(vacancyId: String): VacancyCache?

    @Query("DELETE FROM vacancies_table WHERE isFavorite=0")
    suspend fun deleteNonFavoriteVacancies()

    @Query("DELETE FROM work_format WHERE vacancyId NOT IN (SELECT id FROM vacancies_table WHERE isFavorite = 1)")
    suspend fun deleteNonFavoriteWorkFormats()

    @Query("DELETE FROM working_hours WHERE vacancyId NOT IN (SELECT id FROM vacancies_table WHERE isFavorite = 1)")
    suspend fun deleteNonFavoriteWorkingHours()

    @Query("DELETE FROM work_schedule_by_days WHERE vacancyId NOT IN (SELECT id FROM vacancies_table WHERE isFavorite = 1)")
    suspend fun deleteNonFavoriteWorkSchedules()

    suspend fun clearNonFavoriteData() {
        deleteNonFavoriteVacancies()
        deleteNonFavoriteWorkFormats()
        deleteNonFavoriteWorkingHours()
        deleteNonFavoriteWorkSchedules()
    }

    @Transaction
    @Query("SELECT * FROM vacancies_table")
    suspend fun getAllVacancies(): List<VacancyWithDetails>

    @Transaction
    @Query(
        """
    SELECT *
    FROM vacancies_table
    ORDER BY 
        CASE 
            WHEN salary_from IS NULL AND salary_to IS NULL THEN 1
            ELSE 0
        END,
        CASE 
            WHEN salary_from IS NOT NULL THEN salary_from
            ELSE salary_to
        END DESC
"""
    )
    suspend fun getDecreaseSalaryVacancies(): List<VacancyWithDetails>

    @Transaction
    @Query(
        """
    SELECT *
    FROM vacancies_table
    ORDER BY 
        CASE 
            WHEN salary_from IS NULL AND salary_to IS NULL THEN 1
            ELSE 0
        END,
        CASE 
            WHEN salary_from IS NOT NULL THEN salary_from
            ELSE salary_to
        END ASC
"""
    )
    suspend fun getIncreaseSalaryVacancies(): List<VacancyWithDetails>

    data class VacancyWithDetails(
        @Embedded val vacancy: VacancyCache,
        @Relation(
            parentColumn = "id",
            entityColumn = "vacancyId"
        )
        val workFormats: List<WorkFormatEntity>,
        @Relation(
            parentColumn = "id",
            entityColumn = "vacancyId"
        )
        val workingHours: List<WorkingHoursEntity>,
        @Relation(
            parentColumn = "id",
            entityColumn = "vacancyId"
        )
        val workBySchedule: List<WorkScheduleByDaysEntity>
    )
}