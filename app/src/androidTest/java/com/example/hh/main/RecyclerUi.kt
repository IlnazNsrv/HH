package com.example.hh.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
import com.example.hh.loadvacancies.ProgressUi

class RecyclerUi {

    private val errorUi: ErrorUiMainPage = ErrorUiMainPage(
        R.id.errorItem,
        "no internet connection"
    )
    private val progressUi: ProgressUi = ProgressUi(R.id.progressItem)
    private val recyclerViewMatcher: RecyclerViewMatcher = RecyclerViewMatcher(R.id.recyclerView)

    private val vacancies: List<VacancyUi> = listOf(
        VacancyUi(
            id = R.id.itemContainer,
            position = 0,
            textTitle = "Kotlin",
            salary = 10000,
            city = "Moscow",
            companyName = "company1",
            experience = "1 year"
        ),
        VacancyUi(
            id = R.id.itemContainer,
            position = 1,
            textTitle = "Kotlin",
            salary = 20000,
            city = "Moscow",
            companyName = "company2",
            experience = "2 years"
        ),
        VacancyUi(
            id = R.id.itemContainer,
            position = 2,
            textTitle = "Java",
            salary = 10000,
            city = "Moscow",
            companyName = "company3",
            experience = "1 year"
        )
    )

    private val downloadButton: ButtonDownloadMoreUi = ButtonDownloadMoreUi(
        position = vacancies.size,
        R.id.downloadMoreButton,
        R.string.download_more,
        "#74D9A8",
        containerIdMatcher = withParent(withId(R.id.recyclerView)),
        containerClassTypeMatcher = withParent(isAssignableFrom(RecyclerView::class.java)),
    )

    fun assertProgressState() {
        errorUi.assertNotVisible()
        progressUi.assertVisible()
        vacancies.forEach { it.assertNotVisible() }
        downloadButton.assertNotVisible()
    }

    fun waitTillErrorState() {
        errorUi.waitTillVisible()
    }

    fun assertErrorState() {
        errorUi.assertVisible()
        progressUi.assertNotVisible()
        vacancies.forEach { it.assertNotVisible() }
        downloadButton.assertNotVisible()
    }

    fun clickRetry() {
        errorUi.clickRetry()
    }

    fun clickVacancyRespondButton(positionOfVacancy: Int) {
        vacancies.find { it.position() == positionOfVacancy }?.clickRespond()
    }

    fun assertVacancyRespondedState(positionOfVacancy: Int) {
        vacancies.find { it.position() == positionOfVacancy }?.vacancyRespondedState()
        errorUi.assertNotVisible()
        progressUi.assertNotVisible()
    }

    fun assertWaitTillGone() {
        errorUi.waitTillDoesntExist()
    }

    fun checkItemsCount(expectedCount: Int) {
        recyclerViewMatcher.hasItemCount(expectedCount)
    }

    fun clickVacancy(position: Int) {
        onView(
            recyclerViewMatcher.atPosition(
                position
            )
        ).perform(
            click()
        )
        //vacancies.find { it.position() == position }?.clickVacancy()
    }

    fun clickDownloadMoreButton() {
        downloadButton.click()
    }
}

