package com.example.hh.filters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hh.core.ClearViewModel
import com.example.hh.core.LastTimeButtonClicked
import com.example.hh.core.VacanciesSearchParams
import com.example.hh.filters.data.FiltersRepository
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.main.data.BundleWrapper
import com.example.hh.views.button.CustomButtonLiveDataWrapper
import com.example.hh.views.button.areabutton.CustomAreaButtonUiState
import com.example.hh.views.button.areabutton.CustomAreaButtonViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class FiltersViewModelTest {

    private lateinit var viewModel: FiltersViewModel
    private lateinit var fakeLastTimeButtonClicked: FakeLastTimeButtonClicked
    private lateinit var fakeClearViewModel: FakeClearViewModel
    private lateinit var searchParamsBuilder: VacanciesSearchParams.Builder
    private lateinit var fakeFiltersRepository: FakeFiltersRepository
    private lateinit var fakeFiltersCreate: CreateFilters
    private lateinit var fakeCustomAreaButtonViewModel: CustomAreaButtonViewModel
    private lateinit var fakeNavigation: FakeNavigation

    private lateinit var experienceButtonsWrapper: FakeFilterButtonsLiveDataWrapper
    private lateinit var scheduleButtonsWrapper: FakeFilterButtonsLiveDataWrapper
    private lateinit var employmentButtonsWrapper: FakeFilterButtonsLiveDataWrapper
    private lateinit var searchFieldButtonsWrapper: FakeFilterButtonsLiveDataWrapper

    @Before
    fun setup() {

        fakeLastTimeButtonClicked = FakeLastTimeButtonClicked()
        fakeClearViewModel = FakeClearViewModel()
        searchParamsBuilder = VacanciesSearchParams.Builder()
        fakeFiltersRepository = FakeFiltersRepository()
        fakeFiltersCreate = CreateFilters.Base()
        fakeCustomAreaButtonViewModel = CustomAreaButtonViewModel(
            fakeLastTimeButtonClicked,
            FakeCustomAreaButtonLiveDataWrapper(),
            searchParamsBuilder,
            fakeFiltersRepository
        )
        fakeNavigation = FakeNavigation()

        experienceButtonsWrapper = FakeFilterButtonsLiveDataWrapper()
        scheduleButtonsWrapper = FakeFilterButtonsLiveDataWrapper()
        employmentButtonsWrapper = FakeFilterButtonsLiveDataWrapper()
        searchFieldButtonsWrapper = FakeFilterButtonsLiveDataWrapper()

        viewModel = FiltersViewModel(
            lastTimeButtonClicked = fakeLastTimeButtonClicked,
            clearViewModel = fakeClearViewModel,
            searchParams = searchParamsBuilder,
            experienceButtonsLiveDataWrapper = experienceButtonsWrapper,
            scheduleButtonsLiveDataWrapper = scheduleButtonsWrapper,
            employmentButtonLiveDataWrapper = employmentButtonsWrapper,
            searchFieldButtonLiveDataWrapper = searchFieldButtonsWrapper,
            filtersRepository = fakeFiltersRepository,
            filtersCreate = fakeFiltersCreate,
            customAreaButtonViewModel = fakeCustomAreaButtonViewModel
        )
    }

    @Test
    fun `init with isFirstRun true should initialize all filters`() {
        viewModel.init(true)

        assertTrue(experienceButtonsWrapper.updateCalled)
        assertTrue(scheduleButtonsWrapper.updateCalled)
        assertTrue(employmentButtonsWrapper.updateCalled)
        assertTrue(searchFieldButtonsWrapper.updateCalled)
    }

    @Test
    fun `init with isFirstRun false should not initialize filters`() {
        viewModel.init(false)

        assertFalse(experienceButtonsWrapper.updateCalled)
        assertFalse(scheduleButtonsWrapper.updateCalled)
        assertFalse(employmentButtonsWrapper.updateCalled)
        assertFalse(searchFieldButtonsWrapper.updateCalled)
    }

    @Test
    fun `searchVacancies should build params and navigate`() {

        val testQuery = "Android Developer"
        val testSalary = 100000

        viewModel.searchVacancies(testQuery, testSalary, fakeNavigation)


        assertEquals(testQuery, searchParamsBuilder.build().searchText)
        assertEquals(testSalary, searchParamsBuilder.build().salary)
        assertTrue(fakeNavigation.navigateCalled)
        assertTrue(fakeClearViewModel.clearCalled)
    }

    @Test
    fun `choose experience button should update params and wrapper`() {

        val testButton = FakeFilterButtonUi(CreateFilters.EXPERIENCE_TAG, "1-3 years")

        viewModel.choose(testButton)

        assertEquals("1-3 years", searchParamsBuilder.build().experience?.first())
        assertTrue(experienceButtonsWrapper.clickButtonCalled)
    }

    @Test
    fun `switchSalaryParams should update onlyWithSalary flag`() {
        viewModel.switchSalaryParams(true)

        assertTrue(searchParamsBuilder.build().onlyWithSalary)
    }

    @Test
    fun `resetAllParams should reset all filters when time passed`() {
        fakeLastTimeButtonClicked.timePassed = true

        viewModel.resetAllParams()

        assertEquals(null, searchParamsBuilder.build().experience)
        assertEquals(null, searchParamsBuilder.build().employment)
        assertEquals(null, searchParamsBuilder.build().schedule)
        assertEquals("", searchParamsBuilder.build().searchText)
        assertFalse(searchParamsBuilder.build().onlyWithSalary)

        assertTrue(experienceButtonsWrapper.updateCalled)
    }

    class FakeLastTimeButtonClicked : LastTimeButtonClicked {
        var timePassed = true
        override fun timePassed(): Boolean = timePassed
    }

    class FakeClearViewModel : ClearViewModel {
        var clearCalled = false
        override fun clear(key: String) {
            clearCalled = true
        }
    }

    class FakeFiltersRepository : FiltersRepository {
        override fun saveParams(params: VacanciesSearchParams) {}
        override fun restoreArea(): Pair<String, String>? = null
    }

    class FakeCustomAreaButtonLiveDataWrapper :
        CustomButtonLiveDataWrapper<CustomAreaButtonUiState> {

        var saveCalled = false
        var updateCalled = false

        override fun save(bundleWrapper: BundleWrapper.Save<CustomAreaButtonUiState>) {
            saveCalled = true
        }

        override fun liveData(): LiveData<CustomAreaButtonUiState> = MutableLiveData()

        override fun update(data: CustomAreaButtonUiState) {
            updateCalled = true
        }
    }

    class FakeFilterButtonsLiveDataWrapper : FilterButtonsLiveDataWrapper<FilterButtonUi> {
        var updateCalled = false
        var clickButtonCalled = false
        private var saveCalled = false

        override fun liveData(): LiveData<ButtonsUiState<FilterButtonUi>> = MutableLiveData()

        override fun save(bundleWrapper: BundleWrapper.Save<ButtonsUiState<FilterButtonUi>>) {
            saveCalled = true
        }

        override fun update(state: ButtonsUiState<FilterButtonUi>) {
            updateCalled = true
        }

        override fun clickButton(buttonUi: FilterButtonUi) {
            clickButtonCalled = true
        }
    }

    class FakeFilterButtonUi(
       private val listId: String = "",
       private val query: String = ""
    ) : FilterButtonUi {

        override fun listId(): String = listId
        override fun type(): FiltersButtonUiType {
           return FiltersButtonUiType.FiltersButton
        }

        override fun id(): String {
            return ""
        }

        override fun changeChosen(): FilterButtonUi {
            return FakeFilterButtonUi()
        }

        override fun chosen(): Boolean {
            return false
        }

        override fun query(): String = query
    }

    class FakeNavigation : NavigateToLoadVacancies {

        var navigateCalled = false

        override fun navigateToLoadVacancies() {
            navigateCalled = true
        }
    }
}