package com.example.hh.loadvacancies

import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
import com.example.hh.main.ButtonUi
import com.example.hh.main.MainPage
import com.example.hh.main.RecyclerUi
import com.example.hh.main.RetryUi
import org.hamcrest.Matcher

class LoadVacanciesPage {

    private val rootId: Matcher<View> =
        withParent(withId(R.id.vacanciesRootLayout))
    private val rootClass: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))
    private val topLayoutId: Matcher<View> =
        withParent(withId(R.id.vacanciesTopLayout))

    private val backButton =
        ButtonUi(topLayoutId, rootClass, R.id.backButton, ImageButton::class.java)
    private val errorUi = ErrorUi(R.id.errorText)
    private val retryUi = RetryUi()
    private val recyclerUi: RecyclerUi = RecyclerUi(rootId, rootClass, R.id.recyclerView)

    private val list = MainPage.list

    fun assertErrorState() {
        errorUi.checkVisible()
    }

    fun assertSuccessfulStateWithFilters() {
        recyclerUi.checkVacancies(
            position = 0,
            vacancy = list[2]
        )
    }

    fun clickVacancy(position: Int) {
        recyclerUi.clickItem(position)
    }

    fun clickRetry() {
        retryUi.click()
    }
}