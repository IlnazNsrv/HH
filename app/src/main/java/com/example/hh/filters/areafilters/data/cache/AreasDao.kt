package com.example.hh.filters.areafilters.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AreasDao {

    @Query("SELECT * FROM areas_table")
    suspend fun areas() : List<AreaCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAreasList(areasList: List<AreaCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(area: AreaCache)

    @Query("SELECT * FROM areas_table WHERE LOWER(city) LIKE LOWER('%' || :query || '%') ORDER BY city ASC")
    suspend fun searchAreas(query: String) : List<AreaCache>
}