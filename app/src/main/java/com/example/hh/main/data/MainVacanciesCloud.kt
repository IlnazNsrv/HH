package com.example.hh.main.data

import com.google.gson.annotations.SerializedName

data class MainVacanciesCloud(
    @SerializedName("request_id")
    val requestId: String?,
    @SerializedName("items")
    val items: List<MainVacancyCloud>
)

data class MainVacancyCloud(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("area")
    val area: Area,
    @SerializedName("Salary")
    val salary: Salary?,
    @SerializedName("address")
    val address: Address?,
    @SerializedName("employer")
    val employer: Employer,
    @SerializedName("work_format")
    val workFormat: List<WorkFormat>?,
    @SerializedName("working_hours")
    val workingHours: List<WorkingHours>?,
    @SerializedName("work_schedule_by_days")
    val workScheduleByDays: List<WorkScheduleByDays>?,
    @SerializedName("experience")
    val experience: Experience,
    @SerializedName("url")
    val url: String,
    @SerializedName("type")
    val type: Type
)
data class Type(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class Area(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class Salary(
    @SerializedName("from")
    val from: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("gross")
    val gross: Boolean?
)

data class Address(
    @SerializedName("city")
    val city: String?,
    @SerializedName("street")
    val street: String?
)

data class Employer(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrls?,
)

data class LogoUrls(
    @SerializedName("90")
    val ninety: String,
    @SerializedName("240")
    val twoHundredForty: String,
    @SerializedName("original")
    val original: String
)

data class WorkFormat(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class WorkingHours(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class WorkScheduleByDays(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class Experience(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

