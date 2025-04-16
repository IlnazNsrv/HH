package com.example.hh.vacancydetails.data.cloud

import com.example.hh.main.data.HandleError
import retrofit2.Retrofit

interface LoadVacancyDetailsCloudDataSource {

    suspend fun loadVacancyDetails(vacancyId: String): VacancyDetailsCloud

    class Base(
        private val service: VacancyDetailsService,
        private val handleError: HandleError<Exception>
    ) : LoadVacancyDetailsCloudDataSource {

        constructor(
            retrofitBuilder: Retrofit.Builder,
            handleError: HandleError<Exception>
        ) : this(
            retrofitBuilder
                .baseUrl("https://api.hh.ru/")
                .build()
                .create(VacancyDetailsService::class.java), handleError
        )

        override suspend fun loadVacancyDetails(vacancyId: String): VacancyDetailsCloud {
            try {
                val data = service.vacancyDetails(vacancyId).execute()
                return data.body()!!
            } catch (e: Exception) {
                throw handleError.handle(e)
            }
        }
    }
}