package com.example.hh.main.data.cloud

import com.example.hh.main.data.HandleError
import com.example.hh.search.presentation.VacanciesSearchParams
import retrofit2.Retrofit

interface LoadVacanciesCloudDataSource {

    suspend fun loadMainVacancies(): List<VacancyCloud>
    suspend fun loadVacancies(searchParams: VacanciesSearchParams) : List<VacancyCloud>

    class Base(
        private val service: MainVacanciesService,
        private val handleError: HandleError<Exception>
    ) : LoadVacanciesCloudDataSource {

        constructor(
            retrofitBuilder: Retrofit.Builder,
            handleError: HandleError<Exception>
        ) : this(
            retrofitBuilder
                .baseUrl("https://api.hh.ru/")
                .build()
                .create(MainVacanciesService::class.java), handleError
        )

        override suspend fun loadMainVacancies(): List<VacancyCloud> {
            try {
                val data = service.loadVacanciesForMainPage().execute()
                return data.body()!!.items
            } catch (e: Exception) {
                throw handleError.handle(e)
            }
        }

        override suspend fun loadVacancies(searchParams: VacanciesSearchParams): List<VacancyCloud> {
            try {
                val data = service.searchVacancies(
                    searchText = searchParams.searchText,
                    vacancySearchField = searchParams.vacancySearchField,
                    experience = searchParams.experience,
                    employment = searchParams.employment,
                    schedule = searchParams.schedule,
                    area = searchParams.area?.first,
                    salary = searchParams.salary,
                    onlyWithSalary = searchParams.onlyWithSalary
                ).execute()
                return data.body()!!.items
            } catch (e: Exception) {
                throw handleError.handle(e)
            }
        }

//        private fun handleResponseCode(code: Int): String {
//            return when (code) {
//                400 -> "Параметры переданы с ошибкой"
//                403 -> "Требуется ввести капчу"
//                404 -> "Указанная вакансия не существет"
//                else -> ""
//            }
//        }
    }
}