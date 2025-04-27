package com.example.hh.filters

import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.SwitchCompat
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
import com.example.hh.main.ButtonUi
import com.example.hh.main.InputUi
import com.example.hh.main.RecyclerUi
import com.example.hh.vacancydetails.TextUi
import org.hamcrest.Matcher

class FiltersPage {

    private val rootId: Matcher<View> =
        withParent(withId(R.id.rootLayout))
    private val rootClass: Matcher<View> =
        withParent(isAssignableFrom(RelativeLayout::class.java))
    private val topId: Matcher<View> =
        withParent(withId(R.id.topLinearLayout))
    private val topClass: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val inputUi = InputUi(R.id.inputVacancyEditText)
    private val searchButtonUi = ButtonUi(
        withParent(withId(R.id.searchButtonLayout)),
        withParent(isAssignableFrom(FrameLayout::class.java)),
        R.id.searchButton,
        Button::class.java
    )
    private val areaTextUi = TextUi(R.id.cityFragmentButton, topId, topClass)
    private val recyclerNameUi = RecyclerUi(rootId, rootClass, R.id.nameRecyclerView)
    private val recyclerExperienceUi = RecyclerUi(rootId, rootClass, R.id.experienceRecyclerView)
    private val recyclerScheduleUi = RecyclerUi(rootId, rootClass, R.id.scheduleRecyclerView)
    private val recyclerEmploymentUi = RecyclerUi(rootId, rootClass, R.id.employmentRecyclerView)
    private val areaButtonUi = ButtonUi(
        topId,
        topClass,
        R.id.customAreaButton,
        RelativeLayout::class.java
    )
    private val switchCompatUi = ButtonUi(
        topId,
        topClass,
        R.id.onlyWithSalarySwitchButton,
        SwitchCompat::class.java
    )

    fun assertInitialState() {
        recyclerNameUi.checkFiltersButton(0, R.string.name)
        recyclerNameUi.checkFiltersButton(1, R.string.company_name)

        recyclerExperienceUi.checkFiltersButton(0, R.string.noExperience)
        recyclerExperienceUi.checkFiltersButton(1, R.string.between1And3)

        recyclerScheduleUi.checkFiltersButton(0, R.string.fullDay)
        recyclerScheduleUi.checkFiltersButton(1, R.string.shift)

        recyclerEmploymentUi.checkFiltersButton(0, R.string.full)
        recyclerEmploymentUi.checkFiltersButton(1, R.string.part)

        switchCompatUi.checkVisible()
    }

    fun checkAreaButton() {
        areaButtonUi.checkVisible()
    }

    fun clickAddAreaButton() {
        areaTextUi.click()
    }

    fun inputText(text: String) {
        inputUi.inputText(text)
    }

    fun clickSearchButton() {
        searchButtonUi.click()
    }
}