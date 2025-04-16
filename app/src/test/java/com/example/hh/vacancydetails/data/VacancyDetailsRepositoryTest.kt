package com.example.hh.vacancydetails.data

import com.example.hh.favorite.data.cache.FavoriteVacanciesDao
import com.example.hh.favorite.data.cache.FavoriteVacancyCache
import com.example.hh.loadvacancies.data.cache.AddressEntity
import com.example.hh.loadvacancies.data.cache.EmployerEntity
import com.example.hh.loadvacancies.data.cache.ExperienceEntity
import com.example.hh.loadvacancies.data.cache.SalaryEntity
import com.example.hh.loadvacancies.data.cache.TypeEntity
import com.example.hh.loadvacancies.data.cache.VacanciesDao
import com.example.hh.loadvacancies.data.cache.VacancyCache
import com.example.hh.main.data.HandleError
import com.example.hh.vacancydetails.data.cloud.LoadVacancyDetailsCloudDataSource
import com.example.hh.vacancydetails.data.cloud.VacancyDetailsCloud
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class VacancyDetailsRepositoryTest {

    private lateinit var repository: VacancyDetailsRepository.Base
    private lateinit var vacanciesDao: VacanciesDao
    private lateinit var favoriteVacanciesDao: FavoriteVacanciesDao
    private lateinit var cloudDataSource: LoadVacancyDetailsCloudDataSource
    private lateinit var handleError: HandleError<String>

    @Before
    fun setup() {
        vacanciesDao = mock()
        favoriteVacanciesDao = mock()
        cloudDataSource = mock()
        handleError = mock()

        repository = VacancyDetailsRepository.Base(
            vacanciesDao,
            favoriteVacanciesDao,
            cloudDataSource,
            handleError
        )
    }

    @Test
    fun `vacancyDetails should return Success with favorite true when vacancy is favorite`() =
        runBlocking {
            val vacancyId = "123"
            val cloudData = mock<VacancyDetailsCloud>()
            val favoriteCache = FavoriteVacancyCache(
                id = vacancyId,
                name = "Test",
                area = com.example.hh.loadvacancies.data.cache.AreaEntity("1", "Area"),
                salary = SalaryEntity(5000, 10000, "RUR", false),
                address = AddressEntity("Address", "Address"),
                employer = EmployerEntity("1", "Employer", null),
                experience = ExperienceEntity("1", "Experience"),
                url = "Url",
                type = TypeEntity("1", "Type"),
                isFavorite = true
            )

            whenever(cloudDataSource.loadVacancyDetails(vacancyId)).thenReturn(cloudData)
            whenever(favoriteVacanciesDao.getFavoriteVacancy(vacancyId)).thenReturn(favoriteCache)

            val result = repository.vacancyDetails(vacancyId)

            assertTrue(result is LoadVacancyDetailsResult.Success)
            val successResult = result as LoadVacancyDetailsResult.Success
            assertTrue(successResult.vacancyDetails().favoriteChosen())
        }

    @Test
    fun `vacancyDetails should return Success with favorite false when vacancy is not favorite`() =
        runBlocking {
            val vacancyId = "123"
            val cloudData = mock<VacancyDetailsCloud>()

            whenever(cloudDataSource.loadVacancyDetails(vacancyId)).thenReturn(cloudData)
            whenever(favoriteVacanciesDao.getFavoriteVacancy(vacancyId)).thenReturn(null)

            val result = repository.vacancyDetails(vacancyId)

            assertTrue(result is LoadVacancyDetailsResult.Success)
            val successResult = result as LoadVacancyDetailsResult.Success
            assertFalse(successResult.vacancyDetails().favoriteChosen())
        }

    @Test
    fun `vacancyDetails should return Error when exception occurred`() = runBlocking {
        val vacancyId = "123"
        val exception = Exception("Test error")
        val errorMessage = "Error message"

        whenever(cloudDataSource.loadVacancyDetails(vacancyId)).thenAnswer { throw exception }
        whenever(handleError.handle(exception)).thenReturn(errorMessage)

        val result = repository.vacancyDetails(vacancyId)

        assertTrue(result is LoadVacancyDetailsResult.Error)
        assertEquals(errorMessage, (result as LoadVacancyDetailsResult.Error).error().message())
    }

    @Test
    fun `updateFavoriteStatus should toggle favorite status when vacancy exists`() = runBlocking {
        val vacancyId = "123"
        val vacancyCache = VacancyCache(
            id = vacancyId,
            name = "Test",
            area = com.example.hh.loadvacancies.data.cache.AreaEntity("1", "Area"),
            salary = SalaryEntity(5000, 10000, "RUR", false),
            address = AddressEntity("Address", "Address"),
            employer = EmployerEntity("1", "Employer", null),
            experience = ExperienceEntity("1", "Experience"),
            url = "Url",
            type = TypeEntity("1", "Type"),
            isFavorite = false
        )

        whenever(vacanciesDao.getVacancy(vacancyId)).thenReturn(vacancyCache)

        repository.updateFavoriteStatus(vacancyId)

        verify(vacanciesDao).updateFavoriteState(vacancyId, true)
        verify(favoriteVacanciesDao).addVacancy(any())
    }
}