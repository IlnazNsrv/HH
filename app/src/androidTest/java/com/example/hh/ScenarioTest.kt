package com.example.hh

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hh.core.waiting
import com.example.hh.filters.AreaPage
import com.example.hh.filters.FiltersPage
import com.example.hh.loadvacancies.LoadVacanciesPage
import com.example.hh.main.MainPage
import com.example.hh.vacancydetails.VacancyPage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mainPage: MainPage
    private lateinit var vacancyPage: VacancyPage
    private lateinit var filtersPage: FiltersPage
    private lateinit var loadVacanciesPage: LoadVacanciesPage
    private lateinit var areaPage: AreaPage

    @Before
    fun setup() {
        mainPage = MainPage()
        vacancyPage = VacancyPage()
        filtersPage = FiltersPage()
        loadVacanciesPage = LoadVacanciesPage()
        areaPage = AreaPage()
    }

    @Test
    fun case_load_state() = with(mainPage) {

        assertInitialState()

        waiting(2000)

        assertErrorState()
        activityScenarioRule.scenario.recreate()
        assertErrorState()

        clickRetry()
        assertProgressState()

        waiting(2000)

        assertSuccessfulState()
        activityScenarioRule.scenario.recreate()
        assertSuccessfulState()
    }

    @Test
    fun case_click_vacancy_and_back() {
        case_load_state()

        mainPage.clickVacancy(0)

        with(vacancyPage) {
            assertProgressState()

            waiting(2000)

            assertErrorState()
            activityScenarioRule.scenario.recreate()
            assertErrorState()

            clickRetry()
            assertProgressState()

            waiting(2000)

            assertAndroidVacancy()
            activityScenarioRule.scenario.recreate()
            assertAndroidVacancy()

            clickBackButton()
        }

        mainPage.assertSuccessfulState()
    }

    @Test
    fun search_vacancies_with_filters() {
        case_load_state()

        mainPage.clickFilters()

        with(filtersPage) {
            assertInitialState()
            clickAddAreaButton()
        }

        with(areaPage) {
            searchArea("Казань")
            saveChosenArea(0)
        }

        with(filtersPage) {
            assertInitialState()
            clickAddAreaButton()
        }

        with(areaPage) {
            searchArea("Мос")
            waiting(500)
            assertAreaButton("Москва")
            saveChosenArea(0)
        }

        with(filtersPage) {
            checkAreaButton()
            inputText("android")
            clickSearchButton()
        }

        waiting(2000)

        with(loadVacanciesPage) {
            assertErrorState()
            activityScenarioRule.scenario.recreate()

            waiting(2000)

            assertErrorState()

            clickRetry()

            waiting(2000)

            assertSuccessfulStateWithFilters()
            activityScenarioRule.scenario.recreate()

            waiting(2000)

            assertSuccessfulStateWithFilters()

            waiting(1000)

            clickVacancy(0)
        }

        with(vacancyPage) {
            assertProgressState()

            waiting(2000)

            assertErrorState()
            activityScenarioRule.scenario.recreate()
            assertErrorState()

            clickRetry()
            assertProgressState()

            waiting(2000)

            checkSeniorAndroidVacancy()
            activityScenarioRule.scenario.recreate()
            checkSeniorAndroidVacancy()

            clickBackButton()
        }

        waiting(1000)

        loadVacanciesPage.assertSuccessfulStateWithFilters()
    }

    @Test
    fun case_open_second_vacancy() {
        case_load_state()

        mainPage.clickVacancy(1)

        with(vacancyPage) {
            assertProgressState()

            waiting(2000)

            assertErrorState()
            activityScenarioRule.scenario.recreate()
            assertErrorState()

            clickRetry()
            assertProgressState()

            waiting(2000)

            checkProjectVacancy()
            activityScenarioRule.scenario.recreate()
            checkProjectVacancy()

            clickBackButton()
        }
        mainPage.assertSuccessfulState()
    }
}
