package com.example.hh.filters.areafilters.data.cache

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AreasCacheModule(context: Context) {

    private fun prepopulateDatabase(areasDao: AreasDao) {
        val areas = listOf(
            AreaCache("1", "Москва"),
            AreaCache("2", "Санкт-Петербург"),
            AreaCache("3", "Екатеринбург"),
            AreaCache("4", "Новосибирск"),
            AreaCache("22", "Владивосток"),
            AreaCache("72", "Пермь"),
            AreaCache("88", "Казань"),
            AreaCache("1621", "Волжск"),
        )
        CoroutineScope(Dispatchers.IO).launch {
            areasDao.insertAreasList(areas)
        }
    }

    private val database: AreasDatabase by lazy {
        Room.databaseBuilder(
            context,
            AreasDatabase::class.java,
            AreasDatabase::class.java.simpleName
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val areasDao = database.areasDao()
                prepopulateDatabase(areasDao)
            }
        }).build()
    }

    fun database() = database
}