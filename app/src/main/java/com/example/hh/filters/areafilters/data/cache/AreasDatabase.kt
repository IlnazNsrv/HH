package com.example.hh.filters.areafilters.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AreaCache::class], version = 1, exportSchema = false)
abstract class AreasDatabase : RoomDatabase() {

    abstract fun areasDao() : AreasDao
}