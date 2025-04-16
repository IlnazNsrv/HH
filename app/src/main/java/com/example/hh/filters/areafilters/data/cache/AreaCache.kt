package com.example.hh.filters.areafilters.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "areas_table")
data class AreaCache(
    @PrimaryKey
    @ColumnInfo(name = "area")
    val areaId: String,
    @ColumnInfo("city")
    val city: String
) {
}