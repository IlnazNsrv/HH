package com.example.hh.loadvacancies.data

import com.example.hh.R
import com.example.hh.core.ProvideResource
import com.example.hh.core.VacanciesSearchParams
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

interface VacanciesRepository {

    suspend fun vacancies(searchParams: VacanciesSearchParams): LoadVacanciesResult
    suspend fun vacanciesWithCache(searchParams: VacanciesSearchParams): LoadVacanciesResult
    suspend fun vacanciesFromCache(): LoadVacanciesResult
    suspend fun clearVacancies()
    suspend fun updateFavoriteStatus(vacancyUi: VacancyUi)
    suspend fun vacanciesWithDecreaseFilter(): LoadVacanciesResult
    suspend fun vacanciesWithIncreaseFilters(): LoadVacanciesResult

    class Base(
        private val provideResource: ProvideResource,
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
                clearVacancies.clearAllVacancies()
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
                        isFavorite,
                        vacancyCloud.salary?.from,
                        vacancyCloud.salary?.to
                    )
                }
                vacanciesDao.saveVacancies(vacancies)
                vacanciesDao.saveWorkFormat(workFormat)
                vacanciesDao.saveWorkingHours(workingHours)
                vacanciesDao.saveWorkScheduleByDays(workScheduleByDays)


                val vacanciesFromCache = vacanciesDao.getAllVacancies()

                return if (vacanciesFromCache.isEmpty()) {
                    LoadVacanciesResult.Error(provideResource.string(R.string.empty_vacancy_cache))
                } else {
                    LoadVacanciesResult.Success(
                        vacanciesFromCache.map {
                            VacancyUi.Base(
                                setVacancyCacheToCloud(it),
                                it.vacancy.isFavorite
                            )
                        })
                }
            } catch (e: Exception) {
                return LoadVacanciesResult.Error(handleError.handle(e))
            }
        }

        override suspend fun vacanciesFromCache(): LoadVacanciesResult {
            val dataCache = vacanciesDao.getAllVacancies()
            return if (dataCache.isEmpty()) {
                LoadVacanciesResult.Error(provideResource.string(R.string.empty_vacancy_cache))
            } else {
                LoadVacanciesResult.Success(
                    dataCache.map {
                        VacancyUi.Base(
                            setVacancyCacheToCloud(it),
                            it.vacancy.isFavorite
                        )
                    }
                )
            }
        }

        override suspend fun clearVacancies() {
            clearVacancies.clearAllVacancies()
        }

        override suspend fun updateFavoriteStatus(vacancyUi: VacancyUi) {
            val getVacancy = vacanciesDao.getVacancy(vacancyUi.id())!!
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
                !vacancyUi.favoriteChosen()
            )
            vacanciesDao.updateFavoriteState(vacancyUi.id(), !vacancyUi.favoriteChosen())
            favoriteVacanciesDao.addVacancy(favoriteVacancy)
        }

        override suspend fun vacanciesWithDecreaseFilter(): LoadVacanciesResult {
            val dataCache = vacanciesDao.getDecreaseSalaryVacancies()
            return if (dataCache.isEmpty()) {
                LoadVacanciesResult.Error(provideResource.string(R.string.empty_vacancy_cache))
            } else {
                LoadVacanciesResult.Success(
                    dataCache.map {
                        VacancyUi.Base(
                            setVacancyCacheToCloud(it),
                            it.vacancy.isFavorite
                        )
                    }
                )
            }
        }

        override suspend fun vacanciesWithIncreaseFilters(): LoadVacanciesResult {
            val dataCache = vacanciesDao.getIncreaseSalaryVacancies()
            return if (dataCache.isEmpty()) {
                LoadVacanciesResult.Error(provideResource.string(R.string.empty_vacancy_cache))
            } else {
                LoadVacanciesResult.Success(
                    dataCache.map {
                        VacancyUi.Base(
                            setVacancyCacheToCloud(it),
                            it.vacancy.isFavorite
                        )
                    }
                )
            }
        }

        private fun setVacancyCacheToCloud(dataCache: VacanciesDao.VacancyWithDetails): VacancyCloud {

            return VacancyCloud(
                dataCache.vacancy.id,
                dataCache.vacancy.name,
                Area(dataCache.vacancy.area.id, dataCache.vacancy.area.name),
                createProperties.createSalary(dataCache.vacancy.salary),
                createProperties.createAddress(dataCache.vacancy.address),
                createProperties.createEmployer(dataCache.vacancy.employer),
                createProperties.createWorkFormatList(dataCache.workFormats),
                createProperties.createWorkingHours(dataCache.workingHours),
                createProperties.createWorkScheduleByDays(dataCache.workBySchedule),
                createProperties.createExperience(dataCache.vacancy.experience),
                dataCache.vacancy.url,
                Type(dataCache.vacancy.type.id, dataCache.vacancy.type.name)
            )
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
