package com.example.hh.main

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

class InputLayoutErrorMatcher(private val expectingEnabled: Boolean) : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
    override fun describeTo(description: Description) {
        description.appendText("error enabled doesnt match with expected: $expectingEnabled")
    }

    override fun matchesSafely(item: TextInputLayout): Boolean {
        return item.isErrorEnabled == expectingEnabled
    }
}