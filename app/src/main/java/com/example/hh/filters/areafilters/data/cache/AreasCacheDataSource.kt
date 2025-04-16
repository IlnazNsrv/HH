package com.example.hh.filters.areafilters.data.cache

interface AreasCacheDataSource {

    suspend fun areas() : List<AreaCache>
    suspend fun saveAreasList(areasList: List<AreaCache>)
    suspend fun saveArea(area: AreaCache)
    suspend fun searchAreas(query: String) : List<AreaCache>

    class Base(
        private val dao: AreasDao
    ) : AreasCacheDataSource {

        override suspend fun areas(): List<AreaCache> {
            return dao.areas()
        }

        override suspend fun saveAreasList(areasList: List<AreaCache>) {
            dao.insertAreasList(areasList)
        }

        override suspend fun saveArea(area: AreaCache) {
            dao.insert(area)
        }

        override suspend fun searchAreas(query: String): List<AreaCache> {
           return dao.searchAreas(query)
        }
    }
}