package com.example.hh.main

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
import org.hamcrest.Matcher

class ErrorUiMainPage(
    id: Int,
    errorText: String
) {
    private val containerIdMatcher: Matcher<View> = withParent(withId(id))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))


    private val errorText: DefaultTextUi = DefaultTextUi(
        viewId = R.id.errorText,
        textView = errorText,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher
    )

    private val retryButton: ButtonUi = ButtonUi(
        id = R.id.retryButton,
        textResId = R.string.retry,
        colorHex = "#74D9A8",
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher
    )

    fun assertVisible(){
        errorText.assertVisible()
        retryButton.assertVisible()
    }

    fun clickRetry() {
        retryButton.click()
    }

    fun assertNotVisible() {
        errorText.assertNotVisible()
        retryButton.assertNotVisible()
    }

    fun waitTillVisible() {
        errorText.waitTillVisible()
    }

    fun waitTillDoesntExist() {
        errorText.waitTillDoesntExist()
    }

}