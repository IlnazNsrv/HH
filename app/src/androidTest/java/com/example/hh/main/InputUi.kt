package com.example.hh.main

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.hh.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.CoreMatchers.allOf

class InputUi() {


    private val layoutInteraction: ViewInteraction = onView(
        allOf(
            withId(R.id.inputLayout),
            isAssignableFrom(TextInputLayout::class.java)
        )
    )

    private val inputInteraction: ViewInteraction = onView(
        allOf(
            isAssignableFrom(TextInputEditText::class.java),
            withId(R.id.inputEditText),

        )
    )

    fun assertTextFieldEnableAndEmpty() {
        layoutInteraction.check(matches(isEnabled()))
            .check(matches(InputLayoutErrorMatcher(false)))
    }

    fun addText(text: String) {
        inputInteraction.perform(
            typeText(text),
            closeSoftKeyboard()
        )
    }

    fun removeLastLetter() {
        inputInteraction.perform(click(), pressKey(KeyEvent.KEYCODE_DEL), closeSoftKeyboard())

    }

    fun assertDoesntExist() {
        inputInteraction.check(doesNotExist())
    }

}
