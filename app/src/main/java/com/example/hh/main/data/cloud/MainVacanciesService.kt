package com.example.hh.main.data.cloud


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * https://api.hh.ru/vacancies
 */
interface MainVacanciesService {

    @GET("vacancies")
    fun loadVacanciesForMainPage(): Call<VacanciesCloud>

    @GET("vacancies")
    fun searchVacancies(
        @Query("text") searchText: String,
        @Query("search_field") vacancySearchField: List<String>? = listOf(
            "name",
            "company_name",
            "description"
        ),
        @Query("experience") experience: List<String>? = listOf(
            "noExperience",
            "between1And3",
            "between3And6",
            "moreThan6"
        ),
        @Query("employment") employment: List<String>? = listOf(
            "full",
            "part",
            "project",
            "volunteer",
            "probation"
        ),
        @Query("schedule") schedule: List<String>? = listOf(
            "fullDay",
            "shift",
            "flexible",
            "remote",
            "flyInFlyOut",
        ),
        @Query("area") area: String = "1",
        @Query("salary") salary: Int? = null,
        @Query("only_with_salary") onlyWithSalary: Boolean = false
    ): Call<VacanciesCloud>
}