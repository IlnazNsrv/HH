package com.example.hh.main

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.hh.R
import com.example.hh.core.RecyclerViewMatcher
import com.example.hh.main.data.cloud.VacancyCloud
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class RecyclerUi(
    rootId: Matcher<View>,
    rootClass: Matcher<View>,
    id: Int
) {

    private val recyclerViewMatcher: RecyclerViewMatcher = RecyclerViewMatcher(id)

    private val recyclerRootInteraction = onView(
        allOf(
            rootId,
            rootClass,
            withId(R.id.recyclerView)
        )
    )

    fun checkVacancies(position: Int, vacancy: VacancyCloud) {
        recyclerRootInteraction.check(matches(isDisplayed()))

        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                recyclerViewMatcher.atPosition(position, R.id.favoriteButton)
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                recyclerViewMatcher.atPosition(position, R.id.vacancyTitle)
            )
        ).check(matches(withText(vacancy.name)))

        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                recyclerViewMatcher.atPosition(position, R.id.city)
            )
        ).check(matches(withText(vacancy.area.name)))

        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                recyclerViewMatcher.atPosition(position, R.id.experience)
            )
        ).check(
            matches(
                withText(vacancy.experience?.name ?: "Без опыта")
            )
        )
    }

    fun checkFiltersButton(position: Int, buttonTextId: Int) {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                recyclerViewMatcher.atPosition(position, R.id.nameFilterButton)
            )
        ).check(
            matches(
                withText(buttonTextId)
            )
        )
    }

    fun checkAreasButton(position: Int, buttonText: String) {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                recyclerViewMatcher.atPosition(position, R.id.areaButton)
            )
        ).check(
            matches(
                withText(buttonText)
            )
        )
    }

    fun clickFavorite(position: Int) {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                recyclerViewMatcher.atPosition(position, R.id.favoriteButton)
            )
        ).perform(click())
    }

    fun clickItem(position: Int) {
        onView(
            recyclerViewMatcher.atPosition(position)
        ).perform(click())
    }
}

