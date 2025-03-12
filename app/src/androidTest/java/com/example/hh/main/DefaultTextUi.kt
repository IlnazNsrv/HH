package com.example.hh.main

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.hh.loadvacancies.waitTillDisplayed
import com.example.hh.loadvacancies.waitTillDoesntExist
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

class DefaultTextUi(
    private val viewId: Int,
    private val textView: String,
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) {

    private val interaction: ViewInteraction = onView(
        allOf(
            containerIdMatcher,
            containerClassTypeMatcher,
            withId(viewId),
            isAssignableFrom(TextView::class.java)
        )
    )

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
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

    fun getText(): String {
        var text = ""
        interaction.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Get text from TextView"
            }

            override fun perform(uiController: androidx.test.espresso.UiController?, view: View?) {
                val textView = view as TextView
                text = textView.text.toString()
            }
        })
        return text
    }

    fun getTextView() : String {
        return textView
    }

}
