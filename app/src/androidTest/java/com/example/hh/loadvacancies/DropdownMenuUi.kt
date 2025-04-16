package com.example.hh.loadvacancies

import android.widget.AutoCompleteTextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.example.hh.R
import com.example.hh.main.AbstractViewUi
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString

class DropdownMenuUi(id: Int) : AbstractViewUi(
    onView(
        allOf(
            withId(id),
            isAssignableFrom(AutoCompleteTextView::class.java)
        )
    )
) {

    fun clickIncreaseFilters() {

        viewInteraction.perform(ViewActions.click())
        //viewInteraction.perform(ViewActions.typeText("По возрастанию дохода"))
    }

    fun clickIncrease() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val itemToSelect = appContext.resources.getStringArray(R.array.simple_items)[2]
        onView(
            allOf(
                withText(containsString("возрастанию"))
            )
        ).perform(ViewActions.click())
    }
}