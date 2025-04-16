package com.example.hh.loadvacancies.data.cache

import com.example.hh.loadvacancies.data.cache.VacanciesDao.VacancyWithDetails

interface VacanciesCacheDataSource {

    suspend fun vacancies() : List<VacancyWithDetails>
    suspend fun saveVacancies(vacancies: List<VacancyCache>) = Unit
    suspend fun saveWorkFormat(workFormat: List<WorkFormatEntity>) = Unit
    suspend fun saveWorkingHours(workingHours: List<WorkingHoursEntity>) = Unit
    suspend fun saveWorkScheduleByDays(workScheduleByDays: List<WorkScheduleByDaysEntity>) = Unit

    class Base(
        private val dao: VacanciesDao
    ) : VacanciesCacheDataSource {

        override suspend fun vacancies(): List<VacancyWithDetails> {
           return dao.getAllVacancies()
        }

        override suspend fun saveVacancies(vacancies: List<VacancyCache>) {
            dao.saveVacancies(vacancies)
        }

        override suspend fun saveWorkFormat(workFormat: List<WorkFormatEntity>) {
            dao.saveWorkFormat(workFormat)
        }

        override suspend fun saveWorkingHours(workingHours: List<WorkingHoursEntity>) {
            dao.saveWorkingHours(workingHours)
        }

        override suspend fun saveWorkScheduleByDays(workScheduleByDays: List<WorkScheduleByDaysEntity>) {
            dao.saveWorkScheduleByDays(workScheduleByDays)
        }
    }
}