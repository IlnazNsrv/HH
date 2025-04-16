package com.example.hh.vacancydetails.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.hh.core.ClearViewModel
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.RunAsync
import com.example.hh.main.data.BundleWrapper
import com.example.hh.vacancydetails.data.LoadVacancyDetailsResult
import com.example.hh.vacancydetails.data.VacancyDetailsRepository
import com.example.hh.vacancydetails.presentation.screen.VacancyDetailsUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.never
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class VacancyDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: VacancyDetailsViewModel
    private lateinit var clearViewModel: ClearViewModel
    private lateinit var lastTimeButtonClicked: LastTimeButtonClicked
    private lateinit var vacancyDetailsLiveDataWrapper: VacancyDetailsLiveDataWrapper
    private lateinit var mapper: LoadVacancyDetailsResult.Mapper
    private lateinit var repository: VacancyDetailsRepository
    private lateinit var runAsync: FakeRunAsync

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        clearViewModel = mock()
        lastTimeButtonClicked = mock()
        vacancyDetailsLiveDataWrapper = mock()
        mapper = mock()
        repository = mock()
        runAsync = FakeRunAsync()

        viewModel = VacancyDetailsViewModel(
            clearViewModel,
            lastTimeButtonClicked,
            vacancyDetailsLiveDataWrapper,
            mapper,
            repository,
            runAsync
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `clickFavorite should update favorite status if time passed`() = runTest {
        val vacancyId = "123"
        whenever(lastTimeButtonClicked.timePassed()).thenReturn(true)

        doAnswer { }.whenever(repository).updateFavoriteStatus(vacancyId)

        viewModel.init(isFirstRun = true, vacancyId = vacancyId)
        viewModel.clickFavorite()

        runAsync.returnResult()

        verify(repository).updateFavoriteStatus(vacancyId)
        verify(vacancyDetailsLiveDataWrapper).clickFavorite()
    }

    @Test
    fun `restore should update LiveData`() {
        val bundleWrapper = mock<BundleWrapper.Restore<VacancyDetailsUiState>>()
        whenever(bundleWrapper.restore()).thenReturn(mock())

        viewModel.restore(bundleWrapper)

        verify(vacancyDetailsLiveDataWrapper).update(any())
    }

    @Test
    fun `clearViewModel should call clear`() {
        viewModel.clearViewModel()

        verify(clearViewModel).clear("VacancyDetailsViewModel")
    }

    @Test
    fun `liveData should return LiveData from wrapper`() {
        val liveData = mock<LiveData<VacancyDetailsUiState>>()
        whenever(vacancyDetailsLiveDataWrapper.liveData()).thenReturn(liveData)

        val result = viewModel.liveData("test")

        assert(result == liveData)
    }

    @Test
    fun `init with firstRun should set vacancyId and load details`() = runBlocking {
        runAsync = mock()
        viewModel = VacancyDetailsViewModel(
            clearViewModel,
            lastTimeButtonClicked,
            vacancyDetailsLiveDataWrapper,
            mapper,
            repository,
            runAsync
        )
        val vacancyId = "123"

        viewModel.init(isFirstRun = true, vacancyId = vacancyId)

        verify(vacancyDetailsLiveDataWrapper).update(any())
        verify(runAsync).runAsync<Any>(any(), any(), any())
    }

    @Test
    fun `init without firstRun should not load details`() = runBlocking {
        runAsync = mock()
        viewModel = VacancyDetailsViewModel(
            clearViewModel,
            lastTimeButtonClicked,
            vacancyDetailsLiveDataWrapper,
            mapper,
            repository,
            runAsync
        )
        viewModel.init(isFirstRun = false, vacancyId = "123")

        verify(vacancyDetailsLiveDataWrapper, never()).update(any())
        verify(runAsync, never()).runAsync<Any>(any(), any(), any())
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
}