package com.example.hh.main.data

import com.example.hh.favorite.data.cache.FavoriteVacanciesDao
import com.example.hh.favorite.data.cache.FavoriteVacancyCache
import com.example.hh.loadvacancies.data.CreatePropertiesForVacancyUi
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
import com.example.hh.main.data.cloud.Address
import com.example.hh.main.data.cloud.Area
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.data.cloud.Type
import com.example.hh.main.data.cloud.VacancyCloud
import com.example.hh.main.presentation.VacancyUi

interface MainVacanciesRepository {

    suspend fun vacancies(): LoadVacanciesResult
    suspend fun updateFavoriteStatus(vacancyUi: VacancyUi)

    class Base(
        private val createProperties: CreatePropertiesForVacancyUi,
        private val clearVacancies: ClearDataBase,
        private val vacanciesDao: VacanciesDao,
        private val favoriteVacanciesDao: FavoriteVacanciesDao,
        private val cloudDataSource: LoadVacanciesCloudDataSource,
        private val handleError: HandleError<String>,
    ) : MainVacanciesRepository {

        override suspend fun vacancies(): LoadVacanciesResult {
            return try {
                clearVacancies.clearAllVacancies()

                val favoriteVacancies = favoriteVacanciesDao.getFavoriteVacancies()
                val favoriteIds = favoriteVacancies.map { it.id }

                val workFormat: MutableList<WorkFormatEntity> = mutableListOf()
                val workingHours: MutableList<WorkingHoursEntity> = mutableListOf()
                val workScheduleByDays: MutableList<WorkScheduleByDaysEntity> = mutableListOf()

                val data: List<VacancyCloud> = cloudDataSource.loadMainVacancies()
                val vacancies = data.map { vacancyCloud ->
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

//                LoadVacanciesResult.Success(data.map {
//                    VacancyUi.Base(it, false)
//                })
                LoadVacanciesResult.Success(
                    vacanciesFromCache.map {
                        VacancyUi.Base(
                            VacancyCloud(
                                it.vacancy.id,
                                it.vacancy.name,
                                Area(it.vacancy.area.id, it.vacancy.area.name),
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

        override suspend fun updateFavoriteStatus(vacancyUi: VacancyUi) {
            val getVacancy = vacanciesDao.getVacancy(vacancyUi.id())
            if (getVacancy != null) {
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

interface LoadVacanciesResult {
    fun map(mapper: Mapper)

    interface Mapper {
        fun mapSuccess(list: List<VacancyUi>)
        fun mapError(message: String)
        fun mapProgress() = Unit
        fun mapEmptyFavoriteCache() = Unit
    }

    data class Success(private val list: List<VacancyUi>) : LoadVacanciesResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(list)
        }
    }

    data class Error(private val message: String) : LoadVacanciesResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }

    object EmptyFavoriteCache : LoadVacanciesResult {
        override fun map(mapper: Mapper) {
            mapper.mapEmptyFavoriteCache()
        }

    }
}