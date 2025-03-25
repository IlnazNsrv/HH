package com.example.hh.loadvacancies.data

import com.example.hh.favorite.data.cache.FavoriteVacanciesDao
import com.example.hh.favorite.data.cache.FavoriteVacancyCache
import com.example.hh.loadvacancies.data.cache.AddressEntity
import com.example.hh.loadvacancies.data.cache.AreaEntity
import com.example.hh.loadvacancies.data.cache.ClearDataBase
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
import com.example.hh.main.data.cloud.Area
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.data.cloud.Type
import com.example.hh.main.data.cloud.VacancyCloud
import com.example.hh.main.presentation.VacancyUi
import com.example.hh.search.presentation.VacanciesSearchParams

interface VacanciesRepository {

    suspend fun vacancies(searchParams: VacanciesSearchParams): LoadVacanciesResult
    suspend fun vacanciesWithCache(searchParams: VacanciesSearchParams): LoadVacanciesResult
    suspend fun vacanciesFromCache(): LoadVacanciesResult
    suspend fun clearVacancies()
    suspend fun updateFavoriteStatus(vacancyUi: VacancyUi)

    class Base(
        private val createProperties: CreatePropertiesForVacancyUi,
        private val cloudDataSource: LoadVacanciesCloudDataSource,
        private val handleError: HandleError<String>,
        private val vacanciesDao: VacanciesDao,
        private val favoriteVacanciesDao: FavoriteVacanciesDao,
        private val clearVacancies: ClearDataBase
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

                val favoriteVacancies = favoriteVacanciesDao.getFavoriteVacancies()
                val favoriteIds = favoriteVacancies.map { it.id }

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

                    val isFavorite = favoriteIds.contains(vacancyCloud.id)


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
                        ),
                        isFavorite
                    )
                }
                vacanciesDao.saveVacancies(vacancies)
                vacanciesDao.saveWorkFormat(workFormat)
                vacanciesDao.saveWorkingHours(workingHours)
                vacanciesDao.saveWorkScheduleByDays(workScheduleByDays)

//                return LoadVacanciesResult.Success(
//                    vacancyData.map {
//                        VacancyUi.Base(
//                            it,
//                            false
//                        )
//                    })

                val vacanciesFromCache = vacanciesDao.getAllVacancies()

                return LoadVacanciesResult.Success(
                    vacanciesFromCache.map {
//                        VacancyCachedUi(
//                            VacanciesDao.VacancyWithDetails(
//                                it,
//                                workFormat,
//                                workingHours,
//                                workScheduleByDays
//                            ),
//                            it.isFavorite
//                        )
                        VacancyUi.Base(
                            VacancyCloud(
                                it.vacancy.id,
                                it.vacancy.name,
                                Area(it.vacancy.id, it.vacancy.name),
                                createProperties.createSalary(it.vacancy.salary),
                                createProperties.createAddress(it.vacancy.address),
                                createProperties.createEmployer(it.vacancy.employer),
                                createProperties.createWorkFormatList(it.workFormats),
                                createProperties.createWorkingHours(it.workingHours),
                                createProperties.createWorkScheduleByDays(it.workBySchedule),
                                createProperties.createExperience(it.vacancy.experience),
                                it.vacancy.url,
                                Type(it.vacancy.type.id, it.vacancy.type.name)
                            ),
                            it.vacancy.isFavorite
                        )
                    })
            } catch (e: Exception) {
                return LoadVacanciesResult.Error(handleError.handle(e))
            }
        }

        override suspend fun vacanciesFromCache(): LoadVacanciesResult {
            val dataCache = vacanciesDao.getAllVacancies()
            return LoadVacanciesResult.Success(
                dataCache.map {
                    VacancyCachedUi(it, it.vacancy.isFavorite)
                }
            )
        }

        override suspend fun clearVacancies() {
            clearVacancies.clearAllVacancies()
        }

        override suspend fun updateFavoriteStatus(vacancyUi: VacancyUi) {
            val getVacancy = vacanciesDao.getVacancy(vacancyUi.id())
            val favoriteVacancy = FavoriteVacancyCache(
                getVacancy.id,
                getVacancy.name,
                getVacancy.area,
                getVacancy.salary,
                getVacancy.address,
                getVacancy.employer,
                getVacancy.experience,
                getVacancy.url,
                getVacancy.type,
                vacancyUi.favoriteChosen()
            )
            vacanciesDao.updateFavoriteState(vacancyUi.id(), vacancyUi.favoriteChosen())
            favoriteVacanciesDao.addVacancy(favoriteVacancy)
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
