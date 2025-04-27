package com.example.hh.vacancydetails

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
import com.example.hh.loadvacancies.ErrorUi
import com.example.hh.loadvacancies.ProgressUi
import com.example.hh.main.ButtonUi
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not

class VacancyPage {

    private val rootId: Matcher<View> =
        withParent(withId(R.id.vacancyDetailsLayout))
    private val rootClass: Matcher<View> =
        withParent(isAssignableFrom(ConstraintLayout::class.java))
    private val companyLayoutMatcher = withId(R.id.topLayout)
    private val contactsLayoutMatcher = withId(R.id.contactsLayout)

    private val errorUi = ErrorUi(R.id.errorText)
    private val retryUi =
        ButtonUi(
            withParent(withId(R.id.errorLayout)),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            R.id.retryButton,
            Button::class.java
        )
    private val parentForTextView = withParent(withId(R.id.detailsTopLayout))
    private val vacancyNameUi =
        TextUi(R.id.vacancyName, parentForTextView, rootClass)
    private val vacancySalaryUi = TextUi(R.id.salary, parentForTextView, rootClass)
    private val companyNameUi =
        TextUi(
            R.id.companyName,
            withParent(withId(R.id.companyLayout)),
            withParent(isAssignableFrom(LinearLayout::class.java))
        )

    private val experienceUi = TextUi(R.id.experience, parentForTextView, rootClass)
    private val vacancyDescriptionUi = TextUi(R.id.vacancyDescription, parentForTextView, rootClass)
    private val contactsEmailUi =
        TextUi(
            R.id.contactEmail,
            withParent(withId(R.id.contactsLayout)),
            withParent(isAssignableFrom(LinearLayout::class.java))
        )
    private val progressBarUi = ProgressUi(R.id.progressBarVacancyDetails)
    private val backButtonUi = ButtonUi(rootId, rootClass, R.id.backButton, ImageButton::class.java)

    fun assertProgressState() {
        errorUi.checkNotVisible()
        retryUi.checkNotVisible()
        progressBarUi.checkVisible()
    }

    fun assertErrorState() {
        progressBarUi.checkNotVisible()
        errorUi.checkVisible()
    }

    fun clickRetry() {
        retryUi.click()
    }

    fun assertAndroidVacancy() {
        progressBarUi.checkNotVisible()
        errorUi.checkNotVisible()
        vacancyNameUi.checkText("Android Developer")
        vacancySalaryUi.checkText("100000 - 200000")
        companyLayoutMatcher.matches(isDisplayed())
        companyNameUi.checkText("Yandex")
        vacancyDescriptionUi.checkText("This is a test description for android developer vacancy")
        contactsLayoutMatcher.matches(isDisplayed())
        contactsEmailUi.checkText("testEmail@test.com")
    }

    fun checkSeniorAndroidVacancy() {
        progressBarUi.checkNotVisible()
        errorUi.checkNotVisible()
        vacancyNameUi.checkText("Android Senior Developer")
        vacancySalaryUi.checkText("300000 - 500000")
        companyLayoutMatcher.matches(isDisplayed())
        companyNameUi.checkText("Yandex")
        vacancyDescriptionUi.checkText("This is a test description for senior android developer vacancy")
        contactsLayoutMatcher.matches(isDisplayed())
        contactsEmailUi.checkText("testEmail@test.com")
    }

    fun checkProjectVacancy() {
        progressBarUi.checkNotVisible()
        errorUi.checkNotVisible()
        experienceUi.checkText("Требуемый опыт: Без опыта")
        vacancyNameUi.checkText("Project manager")
        vacancySalaryUi.checkNotVisible()
        companyLayoutMatcher.matches(isDisplayed())
        companyNameUi.checkText("testEmployer")
        vacancyDescriptionUi.checkText("This is the second test description for project manager vacancy")
        contactsLayoutMatcher.matches(not(isDisplayed()))
    }

    fun clickBackButton() {
        backButtonUi.click()
    }
}