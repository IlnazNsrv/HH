package com.example.hh.vacancydetails.data.cloud

import com.example.hh.main.data.cloud.Area
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.data.cloud.WorkFormat
import com.example.hh.main.data.cloud.WorkScheduleByDays
import com.example.hh.main.data.cloud.WorkingHours
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VacancyDetailsCloud(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("premium")
    val premium: Boolean,
    @SerializedName("approved")
    val approvedModeration: Boolean,
    @SerializedName("archived")
    val archived: Boolean,
    @SerializedName("area")
    val area: Area,
    @SerializedName("salary_range")
    val salary: Salary?,
    @SerializedName("contacts")
    val contacts: Contacts?,
    @SerializedName("description")
    val description: String,
    @SerializedName("experience")
    val experience: Experience?,
    @SerializedName("employment_form")
    val employment: Employment?,
    @SerializedName("employer")
    val employer: Employer?,
    @SerializedName("has_test")
    val hasTest: Boolean,
    @SerializedName("internship")
    val internship: Boolean?,
    @SerializedName("professional_roles")
    val professionalRoles: List<ProfessionalRoles>?,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("work_format")
    val workFormat: List<WorkFormat>?,
    @SerializedName("working_hours")
    val workingHours: List<WorkingHours>?,
    @SerializedName("work_schedule_by_days")
    val workScheduleByDays: List<WorkScheduleByDays>?,
)


data class Employment(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable

data class ProfessionalRoles(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class Contacts(
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phones")
    val phones: List<Phones>
)

data class Phones(
    @SerializedName("city")
    val city: String,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("formatted")
    val formatted: String,
    @SerializedName("number")
    val number: String
)