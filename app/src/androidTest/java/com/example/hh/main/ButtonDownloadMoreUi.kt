package com.example.hh.main

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ButtonDownloadMoreUi(
    private val position: Int,
    id: Int,
    textResId: Int,
    colorHex: String,
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>,
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
) {
}