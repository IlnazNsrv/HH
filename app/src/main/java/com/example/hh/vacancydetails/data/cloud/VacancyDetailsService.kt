package com.example.hh.vacancydetails.data.cloud


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface VacancyDetailsService {

    @GET("vacancies/{vacancy_id}")
    fun vacancyDetails(
        @Path("vacancy_id") vacancyId: String) : Call<VacancyDetailsCloud>
}