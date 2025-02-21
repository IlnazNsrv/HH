package com.example.hh.main

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
import org.hamcrest.Matcher

class VacancyUi(
    private val id: Int,
    private val position: Int,
    textTitle: String,
    salary: Int,
    city: String,
    companyName: String,
    experience: String,
) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.itemContainer))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val vacancyTitle: DefaultTextUi = DefaultTextUi(
        viewId = R.id.vacancyTitle,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher,
        textView = textTitle
    )

    private val salary: DefaultTextUi = DefaultTextUi(
        viewId = R.id.salary,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher,
        textView = salary.toString()
    )

    private val city: DefaultTextUi = DefaultTextUi(
        viewId = R.id.city,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher,
        textView = city
    )

    private val companyName: DefaultTextUi = DefaultTextUi(
        viewId = R.id.companyName,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher,
        textView = companyName
    )

    private val experience: DefaultTextUi = DefaultTextUi(
        viewId = R.id.experience,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher,
        textView = experience
    )

    private val respondButton: ButtonUi = ButtonUi(
        id = R.id.respondButton,
        textResId = R.string.respond,
        colorHex = "#74D9A8",
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher
    )

    fun vacancyRespondedState() {
        respondButton.assertRespondedState()
    }

    fun clickRespond() {
        respondButton.click()
    }

    fun assertNotVisible() {
        vacancyTitle.assertNotVisible()
        salary.assertNotVisible()
        city.assertNotVisible()
        companyName.assertNotVisible()
        experience.assertNotVisible()
        respondButton.assertNotVisible()
    }

    fun assertVisible() {
        vacancyTitle.assertVisible()
        salary.assertVisible()
        city.assertVisible()
        companyName.assertVisible()
        experience.assertVisible()
        respondButton.assertVisible()
    }

    fun id() = id

    fun position() = position

}
