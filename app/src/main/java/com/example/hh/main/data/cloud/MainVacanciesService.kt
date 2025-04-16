package com.example.hh.main.data.cloud


import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.UnknownHostException

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
        @Query("area") area: String? = "1",
        @Query("salary") salary: Int? = null,
        @Query("only_with_salary") onlyWithSalary: Boolean = false
    ): Call<VacanciesCloud>
}

class FakeLoadVacanciesService : MainVacanciesService {

    private val response = VacanciesCloud(
        requestId = "200",
        listOf(
            VacancyCloud(
                id = "1",
                name = "Android Developer",
                area = Area("1", "Казань"),
                salary = Salary(100000, 200000, "RUR", true),
                address = Address("Moscow", "Tverskaya"),
                employer = Employer("1", "Yandex", null),
                workFormat = listOf(WorkFormat("1", "Remote")),
                workingHours = listOf(WorkingHours("1", "Full day")),
                workScheduleByDays = listOf(WorkScheduleByDays("1", "5/2")),
                experience = Experience("1", "1-3 years"),
                url = "http://example.com",
                type = Type("1", "Direct")
            ),
            VacancyCloud(
                id = "2",
                name = "Project manager",
                area = Area("1", "Москва"),
                salary = Salary(50000, 100000, "RUR", true),
                address = Address("Moscow", "Tverskaya"),
                employer = Employer("25", "Ozon", null),
                workFormat = listOf(WorkFormat("2", "Remote2")),
                workingHours = listOf(WorkingHours("2", "Full day2")),
                workScheduleByDays = listOf(WorkScheduleByDays("2", "6/1")),
                experience = null,
                url = "http://example.com",
                type = Type("2", "Direct2")
            ),
            VacancyCloud(
                id = "3",
                name = "Android Senior Developer",
                area = Area("1", "Москва"),
                salary = Salary(300000, 500000, "RUR", true),
                address = Address("Moscow", "Tverskaya"),
                employer = Employer("1", "Yandex", null),
                workFormat = listOf(WorkFormat("1", "Remote")),
                workingHours = listOf(WorkingHours("1", "Full day")),
                workScheduleByDays = listOf(WorkScheduleByDays("1", "5/2")),
                experience = Experience("3", "6 years+"),
                url = "http://example.com",
                type = Type("1", "Direct")
            )
        )
    )

    private var showSuccess = false

    private fun loadVacancies(): Call<VacanciesCloud> {
        Thread.sleep(1000)
        if (showSuccess)
            return object : Call<VacanciesCloud> {

                override fun clone(): Call<VacanciesCloud> {
                    return this
                }

                override fun execute(): Response<VacanciesCloud> {
                    return Response.success(response)
                }

                override fun isExecuted(): Boolean {
                    return false
                }

                override fun cancel() {}

                override fun isCanceled(): Boolean {
                    return false
                }

                override fun request(): Request {
                    return Request.Builder().build()
                }

                override fun timeout(): Timeout {
                    return Timeout()
                }

                override fun enqueue(p0: Callback<VacanciesCloud>) {}
            }
        else {
            showSuccess = true
            throw UnknownHostException()
        }
    }

    override fun loadVacanciesForMainPage(): Call<VacanciesCloud> {
        return loadVacancies()
    }

    override fun searchVacancies(
        searchText: String,
        vacancySearchField: List<String>?,
        experience: List<String>?,
        employment: List<String>?,
        schedule: List<String>?,
        area: String?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Call<VacanciesCloud> {
        return loadVacancies()
    }
}