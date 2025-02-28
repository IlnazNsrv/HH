package com.example.hh.main.data.cloud


import retrofit2.Call
import retrofit2.http.GET

/**
 * https://api.hh.ru/vacancies
 */
interface MainVacanciesService {

    @GET("vacancies")
    fun loadVacanciesForMainPage() : Call<MainVacanciesCloud>
}