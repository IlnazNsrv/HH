package com.example.hh.main

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotClickable
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.hh.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

class ButtonUi(
    private val id: Int,
    textResId: Int,
    colorHex: String,
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) : AbstractButton(
    onView(
        allOf(
            containerIdMatcher,
            containerClassTypeMatcher,
            withId(id),
            withText(textResId),
            ButtonColorMatcher(colorHex)
        )
    )
)  {
    fun assertRespondedState() {
        interaction.check(matches(ButtonColorMatcher("#A4A7FE")))
            .check(matches(isNotEnabled()))
            .check(matches(withText(R.string.responded)))
            .check(matches(isNotClickable()))
    }

    fun id() = id
}

abstract class AbstractButton(
    protected val interaction: ViewInteraction
) {
    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
            .check(matches(isEnabled()))
    }
}