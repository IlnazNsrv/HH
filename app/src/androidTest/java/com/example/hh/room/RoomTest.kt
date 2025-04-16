package com.example.hh.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hh.loadvacancies.data.cache.AddressEntity
import com.example.hh.loadvacancies.data.cache.AreaEntity
import com.example.hh.loadvacancies.data.cache.EmployerEntity
import com.example.hh.loadvacancies.data.cache.ExperienceEntity
import com.example.hh.loadvacancies.data.cache.SalaryEntity
import com.example.hh.loadvacancies.data.cache.TypeEntity
import com.example.hh.loadvacancies.data.cache.VacanciesDao
import com.example.hh.loadvacancies.data.cache.VacanciesDao.VacancyWithDetails
import com.example.hh.loadvacancies.data.cache.VacanciesDatabase
import com.example.hh.loadvacancies.data.cache.VacancyCache
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var dao: VacanciesDao
    private lateinit var database: VacanciesDatabase
    private lateinit var list: List<VacancyCache>

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            VacanciesDatabase::class.java
        ).build()
        dao = database.vacanciesDao()

        list = listOf(
            VacancyCache(
                id = "1",
                name = "Android Developer",
                area = AreaEntity("24", "Kazan"),
                salary = SalaryEntity(100000, 200000, "RUR", true),
                address = AddressEntity("Kazan", "Vos"),
                employer = EmployerEntity("1", "Yandex", null),
                experience = ExperienceEntity("1", "1-3 years"),
                url = "http://example.com",
                type = TypeEntity("1", "Direct"),
                isFavorite = false,
                salaryFrom = 100000,
                salaryTo = 200000
            ),
            VacancyCache(
                id = "2",
                name = "Project Manager",
                area = AreaEntity("1", "Moscow"),
                salary = SalaryEntity(null, 200000, "RUR", true),
                address = AddressEntity("Moscow", "Tverskaya"),
                employer = EmployerEntity("1", "Yandex", null),
                experience = ExperienceEntity("1", "1-3 years"),
                url = "http://example.com",
                type = TypeEntity("1", "Direct"),
                isFavorite = false,
                salaryFrom = null,
                salaryTo = 200000
            ),
            VacancyCache(
                id = "3",
                name = "Test 3",
                area = AreaEntity("1", "Moscow"),
                salary = SalaryEntity(null, null, "RUR", true),
                address = AddressEntity("Moscow", "Tverskaya"),
                employer = EmployerEntity("1", "Test3", null),
                experience = ExperienceEntity("1", "1-3 years"),
                url = "http://example.com",
                type = TypeEntity("1", "Direct"),
                isFavorite = true,
                salaryFrom = null,
                salaryTo = null
            ),
            VacancyCache(
                id = "4",
                name = "Test 4",
                area = AreaEntity("1", "Moscow"),
                salary = SalaryEntity(null, null, "RUR", true),
                address = AddressEntity("Moscow", "Tverskaya"),
                employer = EmployerEntity("1", "Test3", null),
                experience = ExperienceEntity("1", "1-3 years"),
                url = "http://example.com",
                type = TypeEntity("1", "Direct"),
                isFavorite = true,
                salaryFrom = null,
                salaryTo = 400000
            ),
            VacancyCache(
                id = "5",
                name = "Test 5",
                area = AreaEntity("1", "Moscow"),
                salary = SalaryEntity(null, null, "RUR", true),
                address = AddressEntity("Moscow", "Tverskaya"),
                employer = EmployerEntity("1", "Test3", null),
                experience = ExperienceEntity("1", "1-3 years"),
                url = "http://example.com",
                type = TypeEntity("1", "Direct"),
                isFavorite = true,
                salaryFrom = 400000,
                salaryTo = null
            ),
            VacancyCache(
                id = "6",
                name = "Test 6",
                area = AreaEntity("1", "Moscow"),
                salary = SalaryEntity(null, null, "RUR", true),
                address = AddressEntity("Moscow", "Tverskaya"),
                employer = EmployerEntity("1", "Test3", null),
                experience = ExperienceEntity("1", "1-3 years"),
                url = "http://example.com",
                type = TypeEntity("1", "Direct"),
                isFavorite = true,
                salaryFrom = 500000,
                salaryTo = 700000
            ),
        )
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun add_vacancies_and_update_favorite() = runBlocking {
        dao.saveVacancies(list)

        var actual: Any? = dao.getVacancy("2")
        var expected: Any = list[1]

        assertEquals(expected, actual)

        actual = dao.getVacancy("1")
        expected = list[0]
        assertEquals(expected, actual)


        dao.updateFavoriteState("1", true)
        actual = dao.getVacancy("1")
        expected = VacancyCache(
            id = "1",
            name = "Android Developer",
            area = AreaEntity("24", "Kazan"),
            salary = SalaryEntity(100000, 200000, "RUR", true),
            address = AddressEntity("Kazan", "Vos"),
            employer = EmployerEntity("1", "Yandex", null),
            experience = ExperienceEntity("1", "1-3 years"),
            url = "http://example.com",
            type = TypeEntity("1", "Direct"),
            isFavorite = true,
            salaryFrom = 100000,
            salaryTo = 200000
        )
        assertEquals(expected, actual)
    }

    @Test
    fun case_check_decrease() = runBlocking {
        dao.saveVacancies(list)

        val actual = dao.getDecreaseSalaryVacancies()
        val expected: Any = listOf(
            VacancyWithDetails(
                vacancy = VacancyCache(
                    id = "6",
                    name = "Test 6",
                    area = AreaEntity("1", "Moscow"),
                    salary = SalaryEntity(null, null, "RUR", true),
                    address = AddressEntity("Moscow", "Tverskaya"),
                    employer = EmployerEntity("1", "Test3", null),
                    experience = ExperienceEntity("1", "1-3 years"),
                    url = "http://example.com",
                    type = TypeEntity("1", "Direct"),
                    isFavorite = true,
                    salaryFrom = 500000,
                    salaryTo = 700000
                ),
                workFormats = listOf(),
                workingHours = emptyList(),
                workBySchedule = emptyList()
            ),
            VacancyWithDetails(
                vacancy = VacancyCache(
                    id = "4",
                    name = "Test 4",
                    area = AreaEntity("1", "Moscow"),
                    salary = SalaryEntity(null, null, "RUR", true),
                    address = AddressEntity("Moscow", "Tverskaya"),
                    employer = EmployerEntity("1", "Test3", null),
                    experience = ExperienceEntity("1", "1-3 years"),
                    url = "http://example.com",
                    type = TypeEntity("1", "Direct"),
                    isFavorite = true,
                    salaryFrom = null,
                    salaryTo = 400000
                ),
                workFormats = listOf(),
                workingHours = emptyList(),
                workBySchedule = emptyList()
            ),
            VacancyWithDetails(
                vacancy = VacancyCache(
                    id = "5",
                    name = "Test 5",
                    area = AreaEntity("1", "Moscow"),
                    salary = SalaryEntity(null, null, "RUR", true),
                    address = AddressEntity("Moscow", "Tverskaya"),
                    employer = EmployerEntity("1", "Test3", null),
                    experience = ExperienceEntity("1", "1-3 years"),
                    url = "http://example.com",
                    type = TypeEntity("1", "Direct"),
                    isFavorite = true,
                    salaryFrom = 400000,
                    salaryTo = null
                ),
                workFormats = listOf(),
                workingHours = emptyList(),
                workBySchedule = emptyList()
            ),
            VacancyWithDetails(
                vacancy = VacancyCache(
                    id = "2",
                    name = "Project Manager",
                    area = AreaEntity("1", "Moscow"),
                    salary = SalaryEntity(null, 200000, "RUR", true),
                    address = AddressEntity("Moscow", "Tverskaya"),
                    employer = EmployerEntity("1", "Yandex", null),
                    experience = ExperienceEntity("1", "1-3 years"),
                    url = "http://example.com",
                    type = TypeEntity("1", "Direct"),
                    isFavorite = false,
                    salaryFrom = null,
                    salaryTo = 200000
                ),
                workFormats = listOf(),
                workingHours = emptyList(),
                workBySchedule = emptyList()
            ),
            VacancyWithDetails(
                vacancy = VacancyCache(
                    id = "1",
                    name = "Android Developer",
                    area = AreaEntity("24", "Kazan"),
                    salary = SalaryEntity(100000, 200000, "RUR", true),
                    address = AddressEntity("Kazan", "Vos"),
                    employer = EmployerEntity("1", "Yandex", null),
                    experience = ExperienceEntity("1", "1-3 years"),
                    url = "http://example.com",
                    type = TypeEntity("1", "Direct"),
                    isFavorite = false,
                    salaryFrom = 100000,
                    salaryTo = 200000
                ),
                workFormats = listOf(),
                workingHours = emptyList(),
                workBySchedule = emptyList()
            ),
            VacancyWithDetails(
                VacancyCache(
                    id = "3",
                    name = "Test 3",
                    area = AreaEntity("1", "Moscow"),
                    salary = SalaryEntity(null, null, "RUR", true),
                    address = AddressEntity("Moscow", "Tverskaya"),
                    employer = EmployerEntity("1", "Test3", null),
                    experience = ExperienceEntity("1", "1-3 years"),
                    url = "http://example.com",
                    type = TypeEntity("1", "Direct"),
                    isFavorite = true,
                    salaryFrom = null,
                    salaryTo = null
                ),
                workFormats = listOf(),
                workingHours = emptyList(),
                workBySchedule = emptyList()
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun case_check_increase() = runBlocking {
        dao.saveVacancies(list)

        val actual = dao.getIncreaseSalaryVacancies()[0]
        val expected = VacancyWithDetails(
            VacancyCache(
                id = "1",
                name = "Android Developer",
                area = AreaEntity("24", "Kazan"),
                salary = SalaryEntity(100000, 200000, "RUR", true),
                address = AddressEntity("Kazan", "Vos"),
                employer = EmployerEntity("1", "Yandex", null),
                experience = ExperienceEntity("1", "1-3 years"),
                url = "http://example.com",
                type = TypeEntity("1", "Direct"),
                isFavorite = false,
                salaryFrom = 100000,
                salaryTo = 200000
            ),
            workFormats = listOf(),
            workingHours = emptyList(),
            workBySchedule = emptyList()
        )
        assertEquals(expected, actual)
    }
}