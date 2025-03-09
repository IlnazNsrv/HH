package com.example.hh.search.data

import com.example.hh.main.data.HandleError
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.presentation.VacancyUi

interface VacanciesRepository {

    suspend fun vacancies(searchText: String): LoadVacanciesResult

    class Base(
        private val cloudDataSource: LoadVacanciesCloudDataSource,
        private val handleError: HandleError<String>
    ) : VacanciesRepository {

        override suspend fun vacancies(searchText: String): LoadVacanciesResult {
            return try {
               val data = cloudDataSource.loadVacancies(searchText)
                LoadVacanciesResult.Success(
                    data.map {
                        VacancyUi.Base(it, false)
                    })
            } catch (e: Exception) {
                return LoadVacanciesResult.Error(handleError.handle(e))
            }
        }
    }
}

data class VacanciesSearchParams(
    val searchText: String = "",
    val vacancySearchField: List<String> = emptyList(),
    val experience: List<String> = emptyList(),
    val employment: List<String> = emptyList(),
    val schedule: List<String> = emptyList(),
    val area: String = "",
    val salary: String = "",
    val onlyWithSalary: Boolean = false
)