package com.example.hh.loadvacancies.data.cache

interface VacanciesCacheDataSource {

    suspend fun vacancies() : VacancyCache
}