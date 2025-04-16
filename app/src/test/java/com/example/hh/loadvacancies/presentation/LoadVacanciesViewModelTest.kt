package com.example.hh.loadvacancies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hh.core.ClearViewModel
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.ProvideResource

import com.example.hh.core.VacanciesSearchParams
import com.example.hh.filters.data.cache.ChosenFiltersCache
import com.example.hh.loadvacancies.data.VacanciesRepository
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.data.LoadVacanciesResult
import com.example.hh.main.presentation.FakeRunAsync
import com.example.hh.main.presentation.VacanciesLiveDataWrapper
import com.example.hh.main.presentation.VacanciesUiState
import com.example.hh.main.presentation.VacancyUi
import com.example.hh.main.presentation.VacancyUiType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadVacanciesViewModelTest {

    private lateinit var viewModel: LoadVacanciesViewModel
    private lateinit var fakeProvideResource: FakeProvideResource
    private lateinit var fakeLastTimeButtonClicked: FakeLastTimeButtonClicked
    private lateinit var fakeClearViewModel: FakeClearViewModel
    private lateinit var fakeFiltersCache: FakeChosenFiltersCache
    private lateinit var fakeLiveDataWrapper: FakeVacanciesLiveDataWrapper
    private lateinit var fakeRunAsync: FakeRunAsync
    private lateinit var fakeRepository: FakeVacanciesRepository
    private lateinit var fakeMapper: FakeLoadVacanciesMapper

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeProvideResource = FakeProvideResource()
        fakeLastTimeButtonClicked = FakeLastTimeButtonClicked()
        fakeClearViewModel = FakeClearViewModel()
        fakeFiltersCache = FakeChosenFiltersCache()
        fakeLiveDataWrapper = FakeVacanciesLiveDataWrapper()
        fakeRunAsync = FakeRunAsync()
        fakeRepository = FakeVacanciesRepository()
        fakeMapper = FakeLoadVacanciesMapper()

        viewModel = LoadVacanciesViewModel(
            provideResource = fakeProvideResource,
            lastTimeButtonClicked = fakeLastTimeButtonClicked,
            clearViewModel = fakeClearViewModel,
            filtersCache = fakeFiltersCache,
            liveDataWrapper = fakeLiveDataWrapper,
            runAsync = fakeRunAsync,
            repository = fakeRepository,
            mapper = fakeMapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init with isFirstRun true should search with filters`() {
        fakeFiltersCache.cachedFilters =
            VacanciesSearchParams.Builder().setSearchText(searchText = "test").build()

        viewModel.init(true)
        fakeRunAsync.returnResult()

        assertTrue(fakeMapper.progressMapped)
        assertTrue(fakeRepository.vacanciesWithCacheCalled)
        assertEquals("test", fakeRepository.lastSearchParams?.searchText)
    }

    @Test
    fun `init with isFirstRun false should click cached filter`() {
        fakeRepository.cachedVacancies = listOf(FakeVacancyUi())

        viewModel.init(false)
        fakeRunAsync.returnResult()

        assertTrue(fakeRepository.vacanciesFromCacheCalled)
    }

    @Test
    fun `clickFilters with first item should load from database`() {
        viewModel.clickFilters(fakeProvideResource.items[0])
        fakeRunAsync.returnResult()

        assertTrue(fakeRepository.vacanciesFromCacheCalled)
    }

    @Test
    fun `clickFilters with second item should apply decrease filter`() {
        viewModel.clickFilters(fakeProvideResource.items[1])
        fakeRunAsync.returnResult()

        assertTrue(fakeRepository.vacanciesWithDecreaseFilterCalled)
    }

    @Test
    fun `clickFavorite should update status when time passed`() {
        fakeLastTimeButtonClicked.timePassed = true
        val testVacancy = FakeVacancyUi()

        viewModel.clickFavorite(testVacancy)
        fakeRunAsync.returnResult()

        assertTrue(fakeRepository.updateFavoriteStatusCalled)
        assertEquals(testVacancy, fakeRepository.lastUpdatedVacancy)
        assertTrue(fakeLiveDataWrapper.favoriteClicked)
    }

    @Test
    fun `clearVacancies should clear repository and viewModel`() {
        viewModel.clearVacancies()
        fakeRunAsync.returnResult()

        assertTrue(fakeRepository.clearVacanciesCalled)
        assertTrue(fakeClearViewModel.clearCalled)
    }

    @Test
    fun `retry should call searchWithFilters`() {
        fakeFiltersCache.cachedFilters = VacanciesSearchParams.Builder().build()

        viewModel.retry()
        fakeRunAsync.returnResult()

        assertTrue(fakeRepository.vacanciesWithCacheCalled)
    }

    class FakeProvideResource : ProvideResource {
        var items: Array<String> =
            arrayOf("По соответствию", "По убыванию дохода", "По возрастанию дохода")

        override fun string(id: Int) = ""

        override fun array(id: Int): Array<String> = items
    }

    class FakeLastTimeButtonClicked : LastTimeButtonClicked {
        var timePassed = true
        override fun timePassed(): Boolean = timePassed
    }

    class FakeClearViewModel : ClearViewModel {
        var clearCalled = false
        override fun clear(tag: String) {
            clearCalled = true
        }
    }

    class FakeChosenFiltersCache : ChosenFiltersCache {
        var cachedFilters = VacanciesSearchParams.Builder().build()

        override fun read(): VacanciesSearchParams = cachedFilters
        override fun save(vacanciesSearchParams: VacanciesSearchParams) {
            cachedFilters = vacanciesSearchParams
        }
    }

    class FakeVacanciesLiveDataWrapper : VacanciesLiveDataWrapper {
        var favoriteClicked = false
        var vacancyClicked = false

        override fun liveData(): LiveData<VacanciesUiState> = MutableLiveData()
        override fun clickFavorite(vacancyUi: VacancyUi) {
            favoriteClicked = true
        }

        override fun save(bundle: BundleWrapper.Save<VacanciesUiState>) {}

        override fun clickVacancy(vacancyUi: VacancyUi) {
            vacancyClicked = true
        }

        override fun update(data: VacanciesUiState) {}
    }

    class FakeVacanciesRepository : VacanciesRepository {
        var vacanciesWithCacheCalled = false
        var vacanciesFromCacheCalled = false
        var vacanciesWithDecreaseFilterCalled = false
        var vacanciesWithIncreaseFiltersCalled = false
        var updateFavoriteStatusCalled = false
        var clearVacanciesCalled = false
        var lastSearchParams: VacanciesSearchParams? = null
        var lastUpdatedVacancy: VacancyUi? = null
        var cachedVacancies: List<VacancyUi> = emptyList()

        override suspend fun vacanciesWithCache(searchParams: VacanciesSearchParams): LoadVacanciesResult {
            vacanciesWithCacheCalled = true
            lastSearchParams = searchParams
            return LoadVacanciesResult.Success(cachedVacancies)
        }

        override suspend fun vacanciesFromCache(): LoadVacanciesResult {
            vacanciesFromCacheCalled = true
            return LoadVacanciesResult.Success(cachedVacancies)
        }

        override suspend fun vacanciesWithDecreaseFilter(): LoadVacanciesResult {
            vacanciesWithDecreaseFilterCalled = true
            return LoadVacanciesResult.Success(cachedVacancies)
        }

        override suspend fun vacanciesWithIncreaseFilters(): LoadVacanciesResult {
            vacanciesWithIncreaseFiltersCalled = true
            return LoadVacanciesResult.Success(cachedVacancies)
        }

        override suspend fun updateFavoriteStatus(vacancyUi: VacancyUi) {
            updateFavoriteStatusCalled = true
            lastUpdatedVacancy = vacancyUi
        }

        override suspend fun clearVacancies() {
            clearVacanciesCalled = true
        }
    }

    class FakeLoadVacanciesMapper : LoadVacanciesResult.Mapper {
        var progressMapped = false
        var successMapped = false
        var errorMapper = false

        override fun mapProgress() {
            progressMapped = true
        }

        override fun mapSuccess(list: List<VacancyUi>) {
            successMapped = true
        }

        override fun mapError(message: String) {
            errorMapper = true
        }
    }

    class FakeVacancyUi : VacancyUi {
        override fun type(): VacancyUiType {
            return VacancyUiType.Vacancy
        }

        override fun id(): String {
            return ""
        }

        override fun changeFavoriteChosen(): VacancyUi {
            return this
        }

        override fun favoriteChosen(): Boolean {
            return false
        }
    }
}