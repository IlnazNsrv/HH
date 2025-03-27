package com.example.hh.loadvacancies.data.cache

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "vacancies_table")
data class VacancyCache(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo("name")
    val name: String,
    @Embedded
    val area: AreaEntity,
    @Embedded
    val salary: SalaryEntity?,
    @Embedded
    val address: AddressEntity?,
    @Embedded
    val employer: EmployerEntity,
    @Embedded
    val experience: ExperienceEntity?,
    val url: String,
    @Embedded
    val type: TypeEntity,
    val isFavorite: Boolean = false,
    @ColumnInfo(name = "salary_from")
    val salaryFrom: String? = null,
    @ColumnInfo(name = "salary_to")
    val salaryTo: String? = null
)

@Entity(
    tableName = "work_format",
    //primaryKeys = ["vacancyId", "id", "name"]
    foreignKeys = [ForeignKey(
        entity = VacancyCache::class,
        parentColumns = ["id"],
        childColumns = ["vacancyId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WorkFormatEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0,
    val vacancyId: String,
    val id: String,
    val name: String
)

@Entity(
    tableName = "working_hours",
    //primaryKeys = ["vacancyId", "id", "name"]
    foreignKeys = [ForeignKey(
        entity = VacancyCache::class,
        parentColumns = ["id"],
        childColumns = ["vacancyId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WorkingHoursEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0,
    val vacancyId: String,
    val id: String,
    val name: String
)

@Entity(
    tableName = "work_schedule_by_days",
    // primaryKeys = ["vacancyId", "id", "name"],
    foreignKeys = [ForeignKey(
        entity = VacancyCache::class,
        parentColumns = ["id"],
        childColumns = ["vacancyId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WorkScheduleByDaysEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0,
    val vacancyId: String,
    val id: String,
    val name: String
)

data class AreaEntity(
    @ColumnInfo("area_id")
    val id: String,
    @ColumnInfo("area_name")
    val name: String
)

data class SalaryEntity(
    val from: String?,
    val to: String?,
    val currency: String?,
    val gross: Boolean?
)

data class AddressEntity(
    val city: String?,
    val street: String?
)

data class EmployerEntity(
    @ColumnInfo("employer_id")
    val id: String?,
    @ColumnInfo("employer_name")
    val name: String,
    @Embedded
    val logoUrls: LogoUrlsEntity?
)

data class LogoUrlsEntity(
    val ninety: String,
    val twoHundredForty: String,
    val original: String
)

data class ExperienceEntity(
    @ColumnInfo("experience_id")
    val id: String,
    @ColumnInfo("experience_name")
    val name: String
)

data class TypeEntity(
    @ColumnInfo("type_id")
    val id: String,
    @ColumnInfo("type_name")
    val name: String
)