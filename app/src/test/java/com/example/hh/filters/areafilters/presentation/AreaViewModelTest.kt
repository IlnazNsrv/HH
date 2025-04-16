package com.example.hh.filters.areafilters.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.VacanciesSearchParams
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.main.data.BundleWrapper
import com.example.hh.views.button.CustomButtonLiveDataWrapper
import com.example.hh.views.button.areabutton.CustomAreaButtonUiState
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue


class AreaViewModelTest {

    private lateinit var viewModel: CustomAreaButtonViewModel
    private lateinit var fakeLastTimeButtonClicked: FakeLastTimeButtonClicked
    private lateinit var fakeLiveDataWrapper: FakeCustomButtonLiveDataWrapper
    private lateinit var areaNameCache: VacanciesSearchParams.Builder
    private lateinit var fakeRepository: FakeFiltersRepository

    @Before
    fun setup() {

        fakeLastTimeButtonClicked = FakeLastTimeButtonClicked()
        fakeLiveDataWrapper = FakeCustomButtonLiveDataWrapper()
        areaNameCache = VacanciesSearchParams.Builder()
        fakeRepository = FakeFiltersRepository()

        viewModel = CustomAreaButtonViewModel(
            lastTimeButtonClicked = fakeLastTimeButtonClicked,
            customButtonLiveDataWrapper = fakeLiveDataWrapper,
            areaNameCache = areaNameCache,
            repository = fakeRepository
        )
    }

    @Test
    fun `liveData should return wrapper liveData`() {
        val testLiveData = MutableLiveData<CustomAreaButtonUiState>()
        fakeLiveDataWrapper.liveData = testLiveData

        val result = viewModel.liveData()

        assertEquals(testLiveData, result)
    }

    @Test
    fun `handleCloseAction should clear area and update state`() {
        fakeRepository.savedArea = Pair("1", "Moscow")

        viewModel.handleCloseAction()

        assertNull(areaNameCache.build().area)
        assertNull(fakeRepository.savedArea)
        assertTrue(fakeLiveDataWrapper.updateCalled)
    }

    @Test
    fun `updateState should update liveData with current area`() {
        val testArea = Pair("2", "St. Petersburg")
        fakeRepository.savedArea = testArea

        viewModel.updateState()

        assertTrue(fakeLiveDataWrapper.updateCalled)
        assertEquals(testArea, (fakeLiveDataWrapper.lastState as CustomAreaButtonUiState.Show).chosenArea())
    }

    class FakeLastTimeButtonClicked : LastTimeButtonClicked {
        var timePassed = true
        var timePassedCalled = false

        override fun timePassed(): Boolean {
            timePassedCalled = true
            return timePassed
        }
    }

    class FakeCustomButtonLiveDataWrapper : CustomButtonLiveDataWrapper<CustomAreaButtonUiState> {
        var liveData: LiveData<CustomAreaButtonUiState> = MutableLiveData()
        var updateCalled = false
        var lastState: CustomAreaButtonUiState? = null
        var saveCalled = false

        override fun liveData(): LiveData<CustomAreaButtonUiState> = liveData
        override fun save(bundleWrapper: BundleWrapper.Save<CustomAreaButtonUiState>) {
            saveCalled = true
        }

        override fun update(state: CustomAreaButtonUiState) {
            updateCalled = true
            lastState = state
        }
    }

    class FakeFiltersRepository : FiltersRepository {
        var savedArea: Pair<String, String>? = null
        var saveParamsCalled = false

        override fun saveParams(vacanciesSearchParams: VacanciesSearchParams) {
            saveParamsCalled = true
            savedArea = vacanciesSearchParams.area
        }

        override fun restoreArea(): Pair<String, String>? = savedArea
    }
}