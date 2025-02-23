package com.example.hh.main.data

import retrofit2.Retrofit

interface LoadQuoteCloudDataSource {

    suspend fun loadMainVacancies(): List<MainVacancyCloud>

    class Base(
        private val service: MainVacanciesService,
        private val handleError: HandleError<Exception>
    ) : LoadQuoteCloudDataSource {

        constructor(
            retrofitBuilder: Retrofit.Builder,
            handleError: HandleError<Exception>
        ) : this(
            retrofitBuilder
                .baseUrl("https://api.hh.ru/")
                .build()
                .create(MainVacanciesService::class.java), handleError
        )

        override suspend fun loadMainVacancies(): List<MainVacancyCloud> {
            try {
                val data = service.loadVacanciesForMainPage().execute()
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