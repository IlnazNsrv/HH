package com.example.hh.load

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
import com.example.hh.main.ButtonUi
import org.hamcrest.Matcher

class LoadPage {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.loadContainer))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val progressUi = ProgressUi(
       R.id.progressView
    )

    private val errorUi = ErrorUi(
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = classTypeMatcher
    )

    private val retryUi = ButtonUi(
        R.id.retryButton,
        R.string.retry,
        "#A020F0",
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher
    )

    fun assertProgressState() {
        errorUi.assertNotVisible()
        progressUi.assertVisible()
        retryUi.assertNotVisible()
    }

    fun waitTillError() {
        errorUi.waitTillVisible()
    }

    fun assertErrorState() {
        errorUi.assertVisible()
        progressUi.assertNotVisible()
        retryUi.assertVisible()
    }

    fun clickRetry() {
        retryUi.click()
    }

    fun waitTillGone() {
        errorUi.waitTillDoesntExist()
    }


}
