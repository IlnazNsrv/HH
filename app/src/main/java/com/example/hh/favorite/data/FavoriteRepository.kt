package com.example.hh.favorite.data

import com.example.hh.favorite.data.cache.FavoriteVacanciesDao
import com.example.hh.loadvacancies.data.CreatePropertiesForVacancyUi
import com.example.hh.loadvacancies.data.cache.VacanciesDao
import com.example.hh.loadvacancies.data.cache.WorkFormatEntity
import com.example.hh.loadvacancies.data.cache.WorkScheduleByDaysEntity
import com.example.hh.loadvacancies.data.cache.WorkingHoursEntity
import com.example.hh.main.data.HandleError
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.data.cloud.Area
import com.example.hh.main.data.cloud.Type
import com.example.hh.main.data.cloud.VacancyCloud
import com.example.hh.main.presentation.VacancyUi

interface FavoriteRepository {

    suspend fun favoriteVacancies(): LoadVacanciesResult
    suspend fun clickFavorite(vacancyUi: VacancyUi)

    class Base(
        private val vacanciesDao: VacanciesDao,
        private val favoriteVacanciesDao: FavoriteVacanciesDao,
        private val handleError: HandleError<String>,
        private val createProperties: CreatePropertiesForVacancyUi
    ) : FavoriteRepository {

        override suspend fun favoriteVacancies(): LoadVacanciesResult {
            return try {
                favoriteVacanciesDao.deleteNonFavoriteVacancies()
                val data = favoriteVacanciesDao.getAllVacancies()

                if (data.isEmpty()) {
                    LoadVacanciesResult.EmptyFavoriteCache
                } else {
                    LoadVacanciesResult.Success(
                        data.map {
                            VacancyUi.Base(
                                VacancyCloud(
                                    it.vacancy.id,
                                    it.vacancy.name,
                                    Area(it.vacancy.area.id, it.vacancy.area.name),
                                    createProperties.createSalary(it.vacancy.salary),
                                    createProperties.createAddress(it.vacancy.address),
                                    createProperties.createEmployer(it.vacancy.employer),
                                    createProperties.createWorkFormatList(it.workFormats.map { data ->
                                        WorkFormatEntity(
                                            vacancyId = data.vacancyId,
                                            id = data.id,
                                            name = data.name
                                        )
                                    }),
                                    createProperties.createWorkingHours(it.workingHours.map { data ->
                                        WorkingHoursEntity(
                                            vacancyId = data.vacancyId,
                                            id = data.id,
                                            name = data.name
                                        )
                                    }),
                                    createProperties.createWorkScheduleByDays(it.workBySchedule.map { data ->
                                        WorkScheduleByDaysEntity(
                                            vacancyId = data.vacancyId,
                                            id = data.id,
                                            name = data.name
                                        )
                                    }),
                                    createProperties.createExperience(it.vacancy.experience),
                                    it.vacancy.url,
                                    Type(it.vacancy.type.id, it.vacancy.type.name)
                                ),
                                it.vacancy.isFavorite
                            )
                        }
                    )
                }
            } catch (e: Exception) {
                LoadVacanciesResult.Error(handleError.handle(e))
            }
        }

        override suspend fun clickFavorite(vacancyUi: VacancyUi) {
            favoriteVacanciesDao.updateFavoriteState(vacancyUi.id(), !vacancyUi.favoriteChosen())
            vacanciesDao.updateFavoriteState(vacancyUi.id(), !vacancyUi.favoriteChosen())
        }
    }
}