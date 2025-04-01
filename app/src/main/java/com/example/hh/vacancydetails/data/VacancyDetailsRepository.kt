package com.example.hh.vacancydetails.data

import com.example.hh.core.ProvideResource
import com.example.hh.main.data.HandleError
import com.example.hh.vacancydetails.data.cloud.LoadVacancyDetailsCloudDataSource
import com.example.hh.vacancydetails.presentation.VacancyDetailsUi

interface VacancyDetailsRepository {

 //   suspend fun vacancyDetails(vacancyId: String) : VacancyDetailsCloud
    suspend fun vacancyDetails(vacancyId: String) : LoadVacancyDetailsResult

    class Base(
        private val provideResource: ProvideResource,
        private val cloudDataSource: LoadVacancyDetailsCloudDataSource,
        private val handleError: HandleError<String>
    ) : VacancyDetailsRepository {

//        override suspend fun vacancyDetails(vacancyId: String): VacancyDetailsCloud {
//            return cloudDataSource.loadVacancyDetails(vacancyId)
//        }

        override suspend fun vacancyDetails(vacancyId: String): LoadVacancyDetailsResult {
            return try {
                val data = cloudDataSource.loadVacancyDetails(vacancyId)
                LoadVacancyDetailsResult.Success(
                    VacancyDetailsUi.Base(data, false)
                )
            } catch (e:Exception) {
                LoadVacancyDetailsResult.Error(
                    VacancyDetailsUi.Error(handleError.handle(e))
                )
            }

        }
    }
}