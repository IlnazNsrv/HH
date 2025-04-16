package com.example.hh.vacancydetails.data.cloud


import com.example.hh.main.data.cloud.Area
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.Salary
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.net.UnknownHostException

interface VacancyDetailsService {

    @GET("vacancies/{vacancy_id}")
    fun vacancyDetails(
        @Path("vacancy_id") vacancyId: String
    ): Call<VacancyDetailsCloud>
}

class FakeVacancyDetailsService : VacancyDetailsService {

    private val response = listOf(
        VacancyDetailsCloud(
            id = "1",
            name = "Android Developer",
            premium = false,
            approvedModeration = true,
            archived = false,
            area = Area("1", "Казань"),
            salary = Salary(100000, 200000, "RUR", true),
            contacts = Contacts(
                "testEmail@test.com",
                "Yandex",
                listOf(Phones("Moscow", "test Comment", "formatted", "8800553535"))
            ),
            description = "This is a test description for android developer vacancy",
            experience = Experience("1", "1-3 years"),
            employment = Employment("1", "testEmployment"),
            employer = Employer("1", "Yandex", null),
            hasTest = false,
            internship = true,
            null,
            publishedAt = "published at",
            null,
            null,
            null
        ),
        VacancyDetailsCloud(
            id = "2",
            name = "Project manager",
            premium = false,
            approvedModeration = true,
            archived = false,
            area = Area("1", "Москва"),
            salary = null,
            contacts = null,
            description = "This is the second test description for project manager vacancy",
            experience = null,
            employment = Employment("1", "testEmployment"),
            employer = Employer("1", "testEmployer", null),
            hasTest = false,
            internship = false,
            null,
            publishedAt = "published at",
            null,
            null,
            null
        ),
        VacancyDetailsCloud(
            id = "3",
            name = "Android Senior Developer",
            premium = false,
            approvedModeration = true,
            archived = false,
            area = Area("1", "Москва"),
            salary = Salary(300000, 500000, "RUR", true),
            contacts = Contacts(
                "testEmail@test.com",
                "Yandex",
                listOf(Phones("Moscow", "test Comment", "formatted", "8800553535"))
            ),
            description = "This is a test description for senior android developer vacancy",
            experience = Experience("3", "6 years"),
            employment = Employment("1", "testEmployment"),
            employer = Employer("1", "Yandex", null),
            hasTest = false,
            internship = true,
            null,
            publishedAt = "published at",
            null,
            null,
            null
        )
    )

    private var showSuccess = false

    override fun vacancyDetails(vacancyId: String): Call<VacancyDetailsCloud> {
        Thread.sleep(1000)
        if (showSuccess) {
            return object : Call<VacancyDetailsCloud> {

                override fun clone(): Call<VacancyDetailsCloud> {
                    return this
                }

                override fun execute(): Response<VacancyDetailsCloud> {
                    return Response.success(response[vacancyId.toInt() - 1])
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

                override fun enqueue(p0: Callback<VacancyDetailsCloud>) {}
            }
        } else {
            showSuccess = true
            throw UnknownHostException()
        }
    }
}