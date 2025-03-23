package com.example.hh.search.data

import com.example.hh.loadvacancies.data.cache.AddressEntity
import com.example.hh.loadvacancies.data.cache.AreaEntity
import com.example.hh.loadvacancies.data.cache.EmployerEntity
import com.example.hh.loadvacancies.data.cache.ExperienceEntity
import com.example.hh.loadvacancies.data.cache.LogoUrlsEntity
import com.example.hh.loadvacancies.data.cache.SalaryEntity
import com.example.hh.loadvacancies.data.cache.TypeEntity
import com.example.hh.loadvacancies.data.cache.VacanciesDao
import com.example.hh.loadvacancies.data.cache.VacancyCache
import com.example.hh.loadvacancies.data.cache.WorkFormatEntity
import com.example.hh.loadvacancies.data.cache.WorkScheduleByDaysEntity
import com.example.hh.loadvacancies.data.cache.WorkingHoursEntity
import com.example.hh.loadvacancies.presentation.VacancyCachedUi
import com.example.hh.main.data.HandleError
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.data.cloud.Address
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.presentation.VacancyUi
import com.example.hh.search.presentation.VacanciesSearchParams

interface VacanciesRepository {

    suspend fun vacancies(searchParams: VacanciesSearchParams): LoadVacanciesResult
    suspend fun vacanciesWithCache(searchParams: VacanciesSearchParams): LoadVacanciesResult
    suspend fun vacanciesFromCache(): LoadVacanciesResult
    suspend fun clearVacancies()

    class Base(
        private val cloudDataSource: LoadVacanciesCloudDataSource,
        private val handleError: HandleError<String>,
        private val dao: VacanciesDao
    ) : VacanciesRepository {

        override suspend fun vacancies(searchParams: VacanciesSearchParams): LoadVacanciesResult {
            return try {
                val data = cloudDataSource.loadVacancies(searchParams)
                LoadVacanciesResult.Success(
                    data.map {
                        VacancyUi.Base(it, false)
                    })
            } catch (e: Exception) {
                return LoadVacanciesResult.Error(handleError.handle(e))
            }
        }

        override suspend fun vacanciesWithCache(searchParams: VacanciesSearchParams): LoadVacanciesResult {
            try {
                val vacancyData = cloudDataSource.loadVacancies(searchParams)

                val workFormat: MutableList<WorkFormatEntity> = mutableListOf()
                val workingHours: MutableList<WorkingHoursEntity> = mutableListOf()
                val workScheduleByDays: MutableList<WorkScheduleByDaysEntity> = mutableListOf()

                val vacancies = vacancyData.map { vacancyCloud ->
                    val temporaryWorkFormat = vacancyCloud.workFormat?.map {
                        WorkFormatEntity(vacancyId = vacancyCloud.id, id = it.id, name = it.name)
                    }
                    val temporaryWorkingHours = vacancyCloud.workingHours?.map {
                        WorkingHoursEntity(vacancyId = vacancyCloud.id, id = it.id, name = it.name)
                    }
                    val temporaryWorkScheduleByDays = vacancyCloud.workScheduleByDays?.map {
                        WorkScheduleByDaysEntity(
                            vacancyId = vacancyCloud.id,
                            id = it.id,
                            name = it.name
                        )
                    }

                    workFormat.addAll(temporaryWorkFormat ?: emptyList())
                    workingHours.addAll(temporaryWorkingHours ?: emptyList())
                    workScheduleByDays.addAll(temporaryWorkScheduleByDays ?: emptyList())

                    VacancyCache(
                        vacancyCloud.id,
                        vacancyCloud.name,
                        AreaEntity(vacancyCloud.area.id, vacancyCloud.area.name),
                        createSalaryEntity(vacancyCloud.salary),
                        createAddressEntity(vacancyCloud.address),
                        createEmployerEntity(vacancyCloud.employer),
                        createExperienceEntity(vacancyCloud.experience),
                        vacancyCloud.url,
                        TypeEntity(
                            vacancyCloud.type.id,
                            vacancyCloud.type.name
                        )
                    )
                }
                dao.saveVacancies(vacancies)
                dao.saveWorkFormat(workFormat)
                dao.saveWorkingHours(workingHours)
                dao.saveWorkScheduleByDays(workScheduleByDays)

                return LoadVacanciesResult.Success(
                    vacancyData.map {
                        VacancyUi.Base(
                            it,
                            false
                        )
                    })
            } catch (e: Exception) {
                return LoadVacanciesResult.Error(handleError.handle(e))
            }
        }

        override suspend fun vacanciesFromCache(): LoadVacanciesResult {
            val dataCache = dao.getAllVacancies()
            return LoadVacanciesResult.Success(
                dataCache.map {
                    VacancyCachedUi(it, false)
                }
            )
        }

        override suspend fun clearVacancies() {
            dao.clearNonFavoriteData()
        }

        private fun createSalaryEntity(salary: Salary?): SalaryEntity? {
            return salary?.let {
                SalaryEntity(it.from, it.to, it.currency, it.gross)
            }
        }

        private fun createAddressEntity(address: Address?): AddressEntity? {
            return address?.let {
                AddressEntity(it.city, it.street)
            }
        }

        private fun createEmployerEntity(employer: Employer): EmployerEntity {
            val logoUrls = employer.logoUrls?.let {
                LogoUrlsEntity(it.ninety, it.twoHundredForty, it.original)
            }
            return EmployerEntity(employer.id, employer.name, logoUrls)
        }

        private fun createExperienceEntity(experience: Experience?): ExperienceEntity? {
            return experience?.let {
                ExperienceEntity(it.id, it.name)
            }
        }

    }
}
