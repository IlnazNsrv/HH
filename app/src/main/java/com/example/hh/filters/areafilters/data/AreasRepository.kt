package com.example.hh.filters.areafilters.data

import com.example.hh.filters.areafilters.data.cache.AreasCacheDataSource
import com.example.hh.filters.data.cache.ChosenFiltersCache

interface AreasRepository {

    suspend fun areas(): LoadAreasResult

    class Base(
        private val cacheDataSource: AreasCacheDataSource,
        private val chosenArea: ChosenFiltersCache
    ) : AreasRepository {

        override suspend fun areas(): LoadAreasResult {
            val chosenId = chosenArea.read().area
            val data = cacheDataSource.areas()
            return LoadAreasResult.Success(data.map {
                AreaChoice(it.areaId, it.city, chosen = it.areaId == chosenId)
            })
        }
    }
}

data class AreaChoice(val id: String, val text: String, val chosen: Boolean)

interface LoadAreasResult {
    fun map(mapper: Mapper)

    interface Mapper {
        fun mapSuccess(list: List<AreaChoice>)
    }

    data class Success(private val list: List<AreaChoice>) : LoadAreasResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(list)
        }

    }
}
