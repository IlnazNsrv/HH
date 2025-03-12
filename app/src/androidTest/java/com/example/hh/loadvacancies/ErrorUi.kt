package com.example.hh.loadvacancies

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.hh.R
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ErrorUi(containerIdMatcher: Matcher<View>, classTypeMatcher: Matcher<View>) {

    private val viewId = R.id.errorTextView
    private val interaction: ViewInteraction = onView(
        allOf(
            withId(viewId),
            isAssignableFrom(TextView::class.java),
            containerIdMatcher,
            classTypeMatcher
        )
    )

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
            .check(matches(withText(R.string.no_internet_connection)))
    }

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }

    fun waitTillVisible() {
        onView(isRoot()).perform(waitTillDisplayed(viewId, 5000))
    }

    fun waitTillDoesntExist() {
        onView(isRoot()).perform(waitTillDoesntExist(viewId, 5000))
    }
}
