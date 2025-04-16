package com.example.hh.main.data

import com.example.hh.core.VacanciesSearchParams
import com.example.hh.favorite.data.cache.FavoriteVacanciesDao
import com.example.hh.favorite.data.cache.FavoriteVacancyCache
import com.example.hh.favorite.data.cache.FavoriteWorkFormatEntity
import com.example.hh.favorite.data.cache.FavoriteWorkScheduleByDaysEntity
import com.example.hh.favorite.data.cache.FavoriteWorkingHoursEntity
import com.example.hh.loadvacancies.data.CreatePropertiesForVacancyUi
import com.example.hh.loadvacancies.data.cache.ClearDataBase
import com.example.hh.loadvacancies.data.cache.VacanciesDao
import com.example.hh.loadvacancies.data.cache.VacancyCache
import com.example.hh.loadvacancies.data.cache.WorkFormatEntity
import com.example.hh.loadvacancies.data.cache.WorkScheduleByDaysEntity
import com.example.hh.loadvacancies.data.cache.WorkingHoursEntity
import com.example.hh.main.data.cloud.Address
import com.example.hh.main.data.cloud.Area
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.LoadVacanciesCloudDataSource
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.data.cloud.Type
import com.example.hh.main.data.cloud.VacancyCloud
import com.example.hh.main.data.cloud.WorkFormat
import com.example.hh.main.data.cloud.WorkScheduleByDays
import com.example.hh.main.data.cloud.WorkingHours
import com.example.hh.main.presentation.VacancyUi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainVacanciesRepositoryTest {


    private lateinit var repository: MainVacanciesRepository

    @Test
    fun successDownloadVacancies() = runBlocking {

        repository = MainVacanciesRepository.Base(
            createProperties = CreatePropertiesForVacancyUi.Base(),
            clearVacancies = FakeClearVacancies(),
            vacanciesDao = FakeVacanciesDao(),
            favoriteVacanciesDao = FakeFavoriteVacanciesDao(),
            cloudDataSource = FakeCloudSuccess(),
            handleError = FakeHandleError()
        )

        val actual = repository.vacancies()

        val expected = LoadVacanciesResult.Success(
            list = listOf(
                VacancyUi.Base(
                    VacancyCloud(
                        id = "1",
                        name = "Android Developer",
                        area = Area("1", "Moscow"),
                        salary = Salary(100000, 200000, "RUR", true),
                        address = Address("Moscow", "Tverskaya"),
                        employer = Employer("1", "Yandex", null),
                        workFormat = listOf(WorkFormat("1", "Remote")),
                        workingHours = listOf(WorkingHours("1", "Full day")),
                        workScheduleByDays = listOf(WorkScheduleByDays("1", "5/2")),
                        experience = Experience("1", "1-3 years"),
                        url = "http://example.com",
                        type = Type("1", "Direct")
                    ), false
                ),
                VacancyUi.Base(
                    VacancyCloud(
                        id = "2",
                        name = "Project manager",
                        area = Area("1", "Moscow"),
                        salary = Salary(50000, 100000, "RUR", true),
                        address = Address("Moscow", "Tverskaya"),
                        employer = Employer("25", "Ozon", null),
                        workFormat = listOf(WorkFormat("2", "Remote2")),
                        workingHours = listOf(WorkingHours("2", "Full day2")),
                        workScheduleByDays = listOf(WorkScheduleByDays("2", "6/1")),
                        experience = Experience("2", "2-5 years"),
                        url = "http://example.com",
                        type = Type("2", "Direct2")
                    ), false
                )
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun errorDownloadVacancies() = runBlocking {

        repository = MainVacanciesRepository.Base(
            createProperties = CreatePropertiesForVacancyUi.Base(),
            clearVacancies = FakeClearVacancies(),
            vacanciesDao = FakeVacanciesDao(),
            favoriteVacanciesDao = FakeFavoriteVacanciesDao(),
            cloudDataSource = FakeCloudError(),
            handleError = FakeHandleError()
        )
        val actual = repository.vacancies()
        val expected = LoadVacanciesResult.Error("Fake cloud error")
        assertEquals(expected, actual)
    }
}

private class FakeCloudError : LoadVacanciesCloudDataSource {

    override suspend fun loadMainVacancies(): List<VacancyCloud> {
        throw IllegalStateException("Fake cloud error")
    }

    override suspend fun loadVacancies(searchParams: VacanciesSearchParams): List<VacancyCloud> {
        return emptyList()
    }
}

private class FakeCloudSuccess : LoadVacanciesCloudDataSource {

    override suspend fun loadMainVacancies(): List<VacancyCloud> {
        listCloudVacancy = listOf(
            VacancyCloud(
                id = "1",
                name = "Android Developer",
                area = Area("1", "Moscow"),
                salary = Salary(100000, 200000, "RUR", true),
                address = Address("Moscow", "Tverskaya"),
                employer = Employer("1", "Yandex", null),
                workFormat = listOf(WorkFormat("1", "Remote")),
                workingHours = listOf(WorkingHours("1", "Full day")),
                workScheduleByDays = listOf(WorkScheduleByDays("1", "5/2")),
                experience = Experience("1", "1-3 years"),
                url = "http://example.com",
                type = Type("1", "Direct")
            ),
            VacancyCloud(
                id = "2",
                name = "Project manager",
                area = Area("1", "Moscow"),
                salary = Salary(50000, 100000, "RUR", true),
                address = Address("Moscow", "Tverskaya"),
                employer = Employer("25", "Ozon", null),
                workFormat = listOf(WorkFormat("2", "Remote2")),
                workingHours = listOf(WorkingHours("2", "Full day2")),
                workScheduleByDays = listOf(WorkScheduleByDays("2", "6/1")),
                experience = Experience("2", "2-5 years"),
                url = "http://example.com",
                type = Type("2", "Direct2")
            )
        )
        return listCloudVacancy as List<VacancyCloud>
    }

    override suspend fun loadVacancies(searchParams: VacanciesSearchParams): List<VacancyCloud> {
        return emptyList()
    }
}

private var listCloudVacancy: List<VacancyCloud>? = null


private class FakeClearVacancies : ClearDataBase {
    override suspend fun clearAllVacancies() = Unit
}

private class FakeHandleError : HandleError<String> {
    override fun handle(error: Exception): String {
        return "Fake cloud error"
    }
}

class FakeVacanciesDao() : VacanciesDao {

    private var listCacheVacancy: List<VacancyCache> = emptyList()

    private val workFormatMap: MutableMap<String, MutableList<WorkFormatEntity>> = mutableMapOf()
    private val workingHoursMap: MutableMap<String, MutableList<WorkingHoursEntity>> = mutableMapOf()
    private val workScheduleByDaysMap: MutableMap<String, MutableList<WorkScheduleByDaysEntity>> =
        mutableMapOf()

    override suspend fun saveVacancies(vacancies: List<VacancyCache>) {
        listCacheVacancy = vacancies
    }

    override suspend fun saveWorkFormat(workFormat: List<WorkFormatEntity>) {
        workFormat.forEach { format ->
            val formatList =
                workFormatMap.computeIfAbsent(format.vacancyId) { mutableListOf() }
            formatList.add(format)
        }
    }

    override suspend fun saveWorkingHours(workingHours: List<WorkingHoursEntity>) {
        workingHours.forEach {
                workingHoursMap.computeIfAbsent(it.vacancyId) { mutableListOf() }
                    .add(it)
        }
    }

    override suspend fun saveWorkScheduleByDays(workScheduleByDays: List<WorkScheduleByDaysEntity>) {
        workScheduleByDays.forEach {
            workScheduleByDaysMap.computeIfAbsent(it.vacancyId) { mutableListOf() }.add(it)
        }
    }

    override suspend fun vacancy(): List<VacancyCache> {
        return listCacheVacancy
    }

    override suspend fun updateFavoriteState(id: String, isFavorite: Boolean) {
        listCacheVacancy.find { it.id == id }
    }

    override suspend fun getVacancy(vacancyId: String): VacancyCache? {
        return null
    }

    override suspend fun deleteNonFavoriteVacancies() {}

    override suspend fun deleteNonFavoriteWorkFormats() {}

    override suspend fun deleteNonFavoriteWorkingHours() {}

    override suspend fun deleteNonFavoriteWorkSchedules() {}

    override suspend fun getAllVacancies(): List<VacanciesDao.VacancyWithDetails> {

        val list: List<VacanciesDao.VacancyWithDetails> = listCacheVacancy.map {
            VacanciesDao.VacancyWithDetails(
                vacancy = it,
                workFormats = workFormatMap[it.id] ?: emptyList(),
                workingHours = workingHoursMap[it.id] ?: emptyList(),
                workBySchedule = workScheduleByDaysMap[it.id] ?: emptyList()
            )
        }
        return list
    }

    override suspend fun getDecreaseSalaryVacancies(): List<VacanciesDao.VacancyWithDetails> {
        return emptyList()
    }

    override suspend fun getIncreaseSalaryVacancies(): List<VacanciesDao.VacancyWithDetails> {
        return emptyList()
    }
}

class FakeFavoriteVacanciesDao() : FavoriteVacanciesDao {

    private var listCacheFavoriteVacancy: MutableList<FavoriteVacancyCache> = mutableListOf()
    private var cachedWorkFormat: List<FavoriteWorkFormatEntity> = emptyList()
    private var cachedWorkingHours: List<FavoriteWorkingHoursEntity> = emptyList()
    private var cachedWorkScheduleByDays: List<FavoriteWorkScheduleByDaysEntity> = emptyList()

    override suspend fun addVacancy(vacancy: FavoriteVacancyCache) {
        listCacheFavoriteVacancy.add(vacancy)
    }

    override suspend fun saveWorkFormat(workFormat: List<FavoriteWorkFormatEntity>) {
        cachedWorkFormat = workFormat
    }

    override suspend fun saveWorkingHours(workingHours: List<FavoriteWorkingHoursEntity>) {
        cachedWorkingHours = workingHours
    }

    override suspend fun saveWorkScheduleByDays(workScheduleByDays: List<FavoriteWorkScheduleByDaysEntity>) {
        cachedWorkScheduleByDays = workScheduleByDays
    }

    override suspend fun updateFavoriteState(id: String, isFavorite: Boolean) {}

    override suspend fun getFavoriteVacancies(): List<FavoriteVacancyCache> {
        return listCacheFavoriteVacancy
    }

    override suspend fun getFavoriteVacancy(id: String): FavoriteVacancyCache? {
        return null
    }

    override suspend fun deleteNonFavoriteVacancies() {}

    override suspend fun getAllVacancies(): List<FavoriteVacanciesDao.FavoriteVacancyWithDetails> {
        val list: List<FavoriteVacanciesDao.FavoriteVacancyWithDetails> =
            listCacheFavoriteVacancy.map {
                FavoriteVacanciesDao.FavoriteVacancyWithDetails(
                    it,
                    cachedWorkFormat,
                    cachedWorkingHours,
                    cachedWorkScheduleByDays
                )
            }
        return list
    }
}