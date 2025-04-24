package com.example.hh.main.data.cloud

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VacanciesCloud(
    @SerializedName("request_id")
    val requestId: String?,
    @SerializedName("items")
    val items: List<VacancyCloud>
) : Serializable

data class VacancyCloud(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("area")
    val area: Area,
    @SerializedName("salary_range")
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
    val experience: Experience?,
    @SerializedName("url")
    val url: String,
    @SerializedName("type")
    val type: Type
) : Serializable

data class Type(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable

data class Area(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable

data class Salary(
    @SerializedName("from")
    val from: Int?,
    @SerializedName("to")
    val to: Int?,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("gross")
    val gross: Boolean
) : Serializable

data class Address(
    @SerializedName("city")
    val city: String?,
    @SerializedName("street")
    val street: String?
) : Serializable

data class Employer(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrls?,
) : Serializable

data class LogoUrls(
    @SerializedName("90")
    val ninety: String,
    @SerializedName("240")
    val twoHundredForty: String,
    @SerializedName("original")
    val original: String
) : Serializable

data class WorkFormat(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable

data class WorkingHours(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable

data class WorkScheduleByDays(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable

data class Experience(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable

