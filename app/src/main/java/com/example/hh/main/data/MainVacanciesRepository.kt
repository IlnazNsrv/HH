package com.example.hh.main.data

import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.data.cloud.MainVacancyCloud
import com.example.hh.main.presentation.VacancyUi

interface MainVacanciesRepository {

    suspend fun vacancies(): LoadVacanciesResult

    class Base(
        private val cloudDataSource: LoadVacanciesCloudDataSource,
        private val handleError: HandleError<String>,
    ) : MainVacanciesRepository {

        override suspend fun vacancies(): LoadVacanciesResult {
            return try {
                val data: List<MainVacancyCloud> = cloudDataSource.loadMainVacancies()
                LoadVacanciesResult.Success(data.map {
                    VacancyUi.Base(it, false)
                })
            } catch (e: Exception) {
                return LoadVacanciesResult.Error(handleError.handle(e))
            }
        }
    }
}

data class VacancyChoice(
    val vacancyCloud: MainVacancyCloud,
    val favoriteChosen: Boolean
)

interface LoadVacanciesResult {
    fun map(mapper: Mapper)

    interface Mapper {
        fun mapSuccess(list: List<VacancyUi>)
        fun mapError(message: String)
        fun mapProgress() = Unit
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

    object Progress : LoadVacanciesResult {
        override fun map(mapper: Mapper) {
            mapper.mapProgress()
        }

    }
}