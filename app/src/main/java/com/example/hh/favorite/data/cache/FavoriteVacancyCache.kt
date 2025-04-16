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

@Entity(
    tableName = "work_format",
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
