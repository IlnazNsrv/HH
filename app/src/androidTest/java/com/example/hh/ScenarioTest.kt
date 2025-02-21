package com.example.hh

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mainPage: MainPage
    private lateinit var vacancyPage: VacancyPage

    @Before
    fun setup() {
        mainPage = MainPage()
    }

    @Test
    fun case_load_state() {
        mainPage.assertProgressState()

        mainPage.waitTillError()

        mainPage.assertErrorState()
        activityScenarioRule.scenario.recreate()
        mainPage.assertErrorState()

        mainPage.clickRetry()

        mainPage.assertProgressState()

        mainPage.waitTillGone()

        mainPage.assertInitialState()
    }

    @Test
    fun caseNumber1() {
        case_load_state()

        mainPage.assertInitialState()

        mainPage.clickFirstVacancyRespondButton()

        mainPage.assertFirstVacancyRespondedState()
        activityScenarioRule.scenario.recreate()
        mainPage.assertFirstVacancyRespondedState()

        mainPage.clickFirstVacancy()
        mainPage.checkNotVisibleNow()

        case_load_details_page()

        vacancyPage.clickBack()
        vacancyPage.checkNotVisibleNow()

        mainPage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        mainPage.assertInitialState()
    }

    @Test
    fun case_load_details_page() {
        val loadPage = LoadPage()

        loadPage.assertProgressState()
        activityScenarioRule.scenario.recreate()
        loadPage.assertProgressState()

        loadPage.waitTillError()

        loadPage.assertErrorState()
        activityScenarioRule.scenario.recreate()
        loadPage.assertErrorState()

        loadPage.clickRetry()

        loadPage.assertProgressState()
        activityScenarioRule.scenario.recreate()
        loadPage.assertProgressState()

        loadPage.waitTillGone()

        vacancyPage = VacancyPage()

        vacancyPage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        vacancyPage.assertInitialState()
    }

    @Test
    fun case_download_more_vacancy() {
        case_load_state()

        mainPage.assertInitialState()

        mainPage.clickDownloadMoreButton()

        mainPage.assertLotsVacancies()
        activityScenarioRule.scenario.recreate()
        mainPage.assertLotsVacancies()
    }
}
