package com.example.hh.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.LiveDataWrapper
import com.example.hh.core.RunAsync
import com.example.hh.filters.presentation.screen.NavigateToFilters
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.data.MainVacanciesRepository
import com.example.hh.main.data.cloud.Address
import com.example.hh.main.data.cloud.Area
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.data.cloud.Type
import com.example.hh.main.data.cloud.VacancyCloud
import com.example.hh.main.data.cloud.WorkFormat
import com.example.hh.main.data.cloud.WorkScheduleByDays
import com.example.hh.main.data.cloud.WorkingHours
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class VacanciesViewModelTest {

    private lateinit var runAsync: FakeRunAsync
    private lateinit var repository: FakeMainRepository
    private lateinit var viewModel: VacanciesViewModel
    private val lastTimeButtonClicked: LastTimeButtonClicked = LastTimeButtonClicked.Base()
    private lateinit var mapper: FakeVacanciesMapper
    private lateinit var liveDataWrapper: FakeVacanciesLiveDataWrapper
    private lateinit var fakeMapper: VacanciesResultMapper

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        runAsync = FakeRunAsync()
        repository = FakeMainRepository()
        liveDataWrapper = FakeVacanciesLiveDataWrapper()
        mapper = FakeVacanciesMapper(liveDataWrapper)
        fakeMapper = VacanciesResultMapper(liveDataWrapper)

        viewModel = VacanciesViewModel(
            lastTimeButtonClicked,
            repository,
            runAsync,
            mapper,
            liveDataWrapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadVacancies should show error then success`() = runBlocking {

        repository.expectedFailure()
        viewModel.loadVacancies()
        runAsync.returnResult()

        assertTrue(mapper.progressMapped)
        assertTrue(mapper.errorMapped)
        assertEquals(2, liveDataWrapper.updateCount)
        assertTrue(liveDataWrapper.lastState is VacanciesUiState.Error)

        viewModel.loadVacancies()
        runAsync.returnResult()

        assertEquals(4, liveDataWrapper.updateCount)
        assertTrue(liveDataWrapper.lastState is VacanciesUiState.Show)
    }

    @Test
    fun `init with isFirstRun true should load vacancies`() {

        viewModel.init(true)

        assertEquals(1, repository.loadCalledCount)
        assertTrue(mapper.progressMapped)
    }

    @Test
    fun `init with isFirstRun false should not load vacancies`() {

        viewModel.init(false)

        assertEquals(0, repository.loadCalledCount)
    }

    @Test
    fun `loadVacancies should show progress then success`() = runBlocking {

        viewModel.loadVacancies()
        runAsync.returnResult()

        assertTrue(mapper.progressMapped)
        assertTrue(mapper.successMapped)
        assertEquals(2, liveDataWrapper.updateCount)
        assertTrue(liveDataWrapper.lastState is VacanciesUiState.Show)
    }

    @Test
    fun `clickFavorite should update favorite status`() {

        val vacancy = repository.listVacancy.first()

        viewModel.clickFavorite(vacancy)
        runAsync.returnResult()

        assertEquals(1, liveDataWrapper.favoriteClickedCount)
    }

    @Test
    fun `navigateToFilters when time passed should call navigate`() {

        var navigateCalled = false

        viewModel.navigateToFilters(object : NavigateToFilters {
            override fun navigateToFilters() {
                navigateCalled = true
            }
        })

        assertTrue(navigateCalled)
    }
}

@Suppress("UNCHECKED_CAST")
class FakeRunAsync : RunAsync {

    private var result: Any? = null
    private var uiUpdate: (Any) -> Unit = {}

    override fun <T : Any> runAsync(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        ui: (T) -> Unit
    ) = runBlocking {
        result = background.invoke()
        uiUpdate = ui as (Any) -> Unit
    }

    fun returnResult() {
        uiUpdate.invoke(result!!)
    }
}

private class FakeVacanciesMapper(
    private val liveDataWrapper: FakeVacanciesLiveDataWrapper
) : LoadVacanciesResult.Mapper {

    var progressMapped = false
    var successMapped = false
    var errorMapped = false

    override fun mapProgress() {
        liveDataWrapper.update(VacanciesUiState.Progress(listOf(VacancyUi.Progress)))
        progressMapped = true
    }

    override fun mapSuccess(list: List<VacancyUi>) {
        liveDataWrapper.update(VacanciesUiState.Show(list))
        successMapped = true
    }

    override fun mapError(message: String) {
        liveDataWrapper.update(VacanciesUiState.Error(listOf(VacancyUi.Error(message))))
        errorMapped = true
    }
}

class FakeVacanciesLiveDataWrapper : VacanciesLiveDataWrapper,
    LiveDataWrapper.GetLiveDataWithTag<VacanciesUiState> {
    private val _states = mutableListOf<VacanciesUiState>()
    var updateCount = 0
    var favoriteClickedCount = 0
    var vacancyClickedCount = 0
    val states: List<VacanciesUiState> get() = _states
    val lastState: VacanciesUiState? get() = _states.lastOrNull()

    override fun liveData(tag: String): LiveData<VacanciesUiState> {
        return MutableLiveData<VacanciesUiState>().apply {
           _states.forEach { value = it }
        }
    }

    override fun clickFavorite(vacancyUi: VacancyUi) {
        favoriteClickedCount++
    }

    override fun save(bundle: BundleWrapper.Save<VacanciesUiState>) = Unit

    override fun clickVacancy(vacancyUi: VacancyUi) {
        vacancyClickedCount++
    }

    override fun liveData(): LiveData<VacanciesUiState> {
       return liveData()
    }

    override fun update(data: VacanciesUiState) {
        _states.add(data)
        updateCount++
    }
}

private class FakeMainRepository : MainVacanciesRepository {

    val listVacancy = listOf(
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

    var loadCalledCount = 0
    var favoriteClickedCount = 0
    private var success = true

    fun expectedFailure() {
        success = false
    }

    override suspend fun vacancies(): LoadVacanciesResult {
        loadCalledCount++
        return if (!success) {
            success = true
            LoadVacanciesResult.Error("")
        }
        else {
            LoadVacanciesResult.Success(listVacancy)
        }
    }
    override suspend fun updateFavoriteStatus(vacancyUi: VacancyUi) {
        favoriteClickedCount++
    }
}
