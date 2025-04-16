package com.example.hh.vacancydetails.data

import com.example.hh.favorite.data.cache.FavoriteVacanciesDao
import com.example.hh.favorite.data.cache.FavoriteVacancyCache
import com.example.hh.loadvacancies.data.cache.VacanciesDao
import com.example.hh.main.data.HandleError
import com.example.hh.vacancydetails.data.cloud.LoadVacancyDetailsCloudDataSource
import com.example.hh.vacancydetails.data.cloud.VacancyDetailsService
import com.example.hh.vacancydetails.presentation.VacancyDetailsUi

interface VacancyDetailsRepository {

    suspend fun vacancyDetails(vacancyId: String): LoadVacancyDetailsResult
    suspend fun updateFavoriteStatus(vacancyId: String)

    class Base(
        private val vacanciesDao: VacanciesDao,
        private val favoriteVacanciesDao: FavoriteVacanciesDao,
        private val cloudDataSource: LoadVacancyDetailsCloudDataSource,
        private val handleError: HandleError<String>
    ) : VacancyDetailsRepository {

        override suspend fun vacancyDetails(vacancyId: String): LoadVacancyDetailsResult {
            return try {
                val cachedFavoriteData = favoriteVacanciesDao.getFavoriteVacancy(vacancyId)
                val data = cloudDataSource.loadVacancyDetails(vacancyId)
                if (cachedFavoriteData != null) {
                    LoadVacancyDetailsResult.Success(
                        VacancyDetailsUi.Base(data, cachedFavoriteData.isFavorite)
                    )
                } else {
                    LoadVacancyDetailsResult.Success(
                        VacancyDetailsUi.Base(data, false)
                    )
                }
            } catch (e: Exception) {
                LoadVacancyDetailsResult.Error(
                    VacancyDetailsUi.Error(handleError.handle(e))
                )
            }
        }

        override suspend fun updateFavoriteStatus(vacancyId: String) {
            val getVacancy = vacanciesDao.getVacancy(vacancyId)
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
                    !getVacancy.isFavorite
                )
                vacanciesDao.updateFavoriteState(vacancyId, !getVacancy.isFavorite)
                favoriteVacanciesDao.addVacancy(favoriteVacancy)
            }
        }
    }

    class Fake(
        private val cloudDataSource: VacancyDetailsService,
        private val handleError: HandleError<String>
    ) : VacancyDetailsRepository {

        private var favoriteClicked = false

        override suspend fun vacancyDetails(vacancyId: String): LoadVacancyDetailsResult {
            try {
                val data = cloudDataSource.vacancyDetails(vacancyId).execute().body()!!
                return LoadVacancyDetailsResult.Success(
                    VacancyDetailsUi.Base(
                        data,
                        false
                    )
                )
            } catch (e: Exception) {
                return LoadVacancyDetailsResult.Error(VacancyDetailsUi.Error(handleError.handle(e)))
            }
        }

        override suspend fun updateFavoriteStatus(vacancyId: String) {
            favoriteClicked = !favoriteClicked
        }
    }
}