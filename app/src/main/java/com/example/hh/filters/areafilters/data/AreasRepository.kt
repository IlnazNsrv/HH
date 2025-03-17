package com.example.hh.filters.areafilters.data

import com.example.hh.filters.areafilters.data.cache.AreasCacheDataSource
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.search.presentation.VacanciesSearchParams

interface AreasRepository {

    suspend fun areas(text: String): LoadAreasResult
    fun saveArea(vacanciesSearchParams: VacanciesSearchParams)

    class Base(
        private val cacheDataSource: AreasCacheDataSource,
        private val chosenArea: ChosenFiltersCache
    ) : AreasRepository {


        override suspend fun areas(text: String): LoadAreasResult {
            val chosenId = chosenArea.read().area
            if (text.isEmpty()) {
                val data = cacheDataSource.areas()
                return LoadAreasResult.Success(data.map {
                    AreaChoice(it.areaId, it.city, chosen = it.areaId == chosenId)
                })
            } else {
                val data = cacheDataSource.searchAreas(text)
                return LoadAreasResult.Success(data.map {
                    AreaChoice(it.areaId, it.city, chosen = it.areaId == chosenId)
                })
            }
        }

        override fun saveArea(vacanciesSearchParams: VacanciesSearchParams) {
           chosenArea.save(vacanciesSearchParams)
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
