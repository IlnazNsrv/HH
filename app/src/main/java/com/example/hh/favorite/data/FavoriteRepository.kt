package com.example.hh.favorite.data

import com.example.hh.favorite.data.cache.FavoriteVacanciesDao
import com.example.hh.favorite.data.cache.FavoriteWorkFormatEntity
import com.example.hh.favorite.data.cache.FavoriteWorkScheduleByDaysEntity
import com.example.hh.favorite.data.cache.FavoriteWorkingHoursEntity
import com.example.hh.loadvacancies.data.CreatePropertiesForVacancyUi
import com.example.hh.loadvacancies.data.cache.AddressEntity
import com.example.hh.loadvacancies.data.cache.EmployerEntity
import com.example.hh.loadvacancies.data.cache.ExperienceEntity
import com.example.hh.loadvacancies.data.cache.SalaryEntity
import com.example.hh.loadvacancies.data.cache.WorkFormatEntity
import com.example.hh.loadvacancies.data.cache.WorkScheduleByDaysEntity
import com.example.hh.loadvacancies.data.cache.WorkingHoursEntity
import com.example.hh.main.data.HandleError
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.data.cloud.Address
import com.example.hh.main.data.cloud.Area
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.LogoUrls
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.data.cloud.Type
import com.example.hh.main.data.cloud.VacancyCloud
import com.example.hh.main.data.cloud.WorkFormat
import com.example.hh.main.data.cloud.WorkScheduleByDays
import com.example.hh.main.data.cloud.WorkingHours
import com.example.hh.main.presentation.VacancyUi

interface FavoriteRepository {

    suspend fun favoriteVacancies(): LoadVacanciesResult

    class Base(
        private val favoriteVacanciesDao: FavoriteVacanciesDao,
        private val handleError: HandleError<String>,
        private val createProperties: CreatePropertiesForVacancyUi
    ) : FavoriteRepository {

        override suspend fun favoriteVacancies(): LoadVacanciesResult {
            return try {
                val data = favoriteVacanciesDao.getAllVacancies()

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
                                createProperties.createWorkFormatList(it.workFormats.map { data->
                                    WorkFormatEntity(
                                        vacancyId = data.vacancyId,
                                        id = data.id,
                                        name = data.name
                                    )
                                }),
                                createProperties.createWorkingHours(it.workingHours.map { data->
                                    WorkingHoursEntity(
                                        vacancyId = data.vacancyId,
                                        id = data.id,
                                        name = data.name
                                    )
                                }),
                                createProperties.createWorkScheduleByDays(it.workBySchedule.map { data->
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
                            false
                        )
                    }
                )
            } catch (e: Exception) {
                LoadVacanciesResult.Error(handleError.handle(e))
            }
        }

        private fun createSalary(salary: SalaryEntity?): Salary? {
            return salary?.let {
                Salary(it.from, it.to, it.currency, it.gross)
            }
        }

        private fun createAddress(address: AddressEntity?): Address? {
            return address?.let {
                Address(it.city, it.street)
            }
        }

        private fun createEmployer(employer: EmployerEntity): Employer {
            val logoUrls = employer.logoUrls?.let {
                LogoUrls(it.ninety, it.twoHundredForty, it.original)
            }
            return Employer(employer.id, employer.name, logoUrls)
        }

        private fun createExperience(experience: ExperienceEntity?): Experience? {
            return experience?.let {
                Experience(it.id, it.name)
            }
        }

        private fun createWorkFormatList(workFormat: List<FavoriteWorkFormatEntity>): List<WorkFormat> {
            val list = workFormat.map { WorkFormat(it.id, it.name) }
            return list
        }

        private fun createWorkingHours(workingHours: List<FavoriteWorkingHoursEntity>): List<WorkingHours> {
            val list = workingHours.map { WorkingHours(it.id, it.name) }
            return list
        }

        private fun createWorkScheduleByDays(workScheduleByDays: List<FavoriteWorkScheduleByDaysEntity>): List<WorkScheduleByDays> {
            val list = workScheduleByDays.map { WorkScheduleByDays(it.id, it.name) }
            return list
        }
    }
}