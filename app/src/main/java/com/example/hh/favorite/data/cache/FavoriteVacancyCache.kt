package com.example.hh.favorite.data.cache

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.hh.loadvacancies.data.cache.AddressEntity
import com.example.hh.loadvacancies.data.cache.AreaEntity
import com.example.hh.loadvacancies.data.cache.EmployerEntity
import com.example.hh.loadvacancies.data.cache.ExperienceEntity
import com.example.hh.loadvacancies.data.cache.SalaryEntity
import com.example.hh.loadvacancies.data.cache.TypeEntity

@Entity(tableName = "favorite_table")
data class FavoriteVacancyCache(
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
    val isFavorite: Boolean = false
)

@Entity(tableName = "work_format",
    foreignKeys = [ForeignKey(
        entity = FavoriteVacancyCache::class,
        parentColumns = ["id"],
        childColumns = ["vacancyId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class FavoriteWorkFormatEntity(
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
        entity = FavoriteVacancyCache::class,
        parentColumns = ["id"],
        childColumns = ["vacancyId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class FavoriteWorkingHoursEntity(
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
        entity = FavoriteVacancyCache::class,
        parentColumns = ["id"],
        childColumns = ["vacancyId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class FavoriteWorkScheduleByDaysEntity(
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