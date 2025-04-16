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
    private val vacancySalary = TextUi(R.id.salary, parentForTextView, rootClass)
    private val companyNameUi =
        TextUi(
            R.id.companyName,
            withParent(withId(R.id.companyLayout)),
            withParent(isAssignableFrom(LinearLayout::class.java))
        )
    private val companyLayout = withId(R.id.topLayout)
    private val experienceUi = TextUi(R.id.experience, parentForTextView, rootClass)
    private val vacancyDescription = TextUi(R.id.vacancyDescription, parentForTextView, rootClass)
    private val contactsLayout = withId(R.id.contactsLayout)
    private val contactsEmail =
        TextUi(
            R.id.contactEmail,
            withParent(withId(R.id.contactsLayout)),
            withParent(isAssignableFrom(LinearLayout::class.java))
        )
    private val progressBar = ProgressUi(R.id.progressBarVacancyDetails)
    private val backButton = ButtonUi(rootId, rootClass, R.id.backButton, ImageButton::class.java)

    fun checkProgress() {
        progressBar.checkVisible()
    }

    fun checkError() {
        errorUi.checkVisible()
    }

    fun clickRetry() {
        retryUi.click()
    }

    fun checkAndroidVacancy() {
        progressBar.checkNotVisible()
        errorUi.checkNotVisible()
        vacancyNameUi.checkText("Android Developer")
        vacancySalary.checkText("100000 - 200000")
        companyLayout.matches(isDisplayed())
        companyNameUi.checkText("Yandex")
        vacancyDescription.checkText("This is a test description for android developer vacancy")
        contactsLayout.matches(isDisplayed())
        contactsEmail.checkText("testEmail@test.com")
    }

    fun checkSeniorAndroidVacancy() {
        progressBar.checkNotVisible()
        errorUi.checkNotVisible()
        vacancyNameUi.checkText("Android Senior Developer")
        vacancySalary.checkText("300000 - 500000")
        companyLayout.matches(isDisplayed())
        companyNameUi.checkText("Yandex")
        vacancyDescription.checkText("This is a test description for senior android developer vacancy")
        contactsLayout.matches(isDisplayed())
        contactsEmail.checkText("testEmail@test.com")
    }

    fun checkProjectVacancy() {
        progressBar.checkNotVisible()
        errorUi.checkNotVisible()
        experienceUi.checkText("Требуемый опыт: Без опыта")
        vacancyNameUi.checkText("Project manager")
        vacancySalary.checkNotVisible()
        companyLayout.matches(isDisplayed())
        companyNameUi.checkText("testEmployer")
        vacancyDescription.checkText("This is the second test description for project manager vacancy")
        contactsLayout.matches(not(isDisplayed()))
    }

    fun clickBack() {
        backButton.click()
    }
}