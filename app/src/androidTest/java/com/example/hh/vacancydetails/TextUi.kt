package com.example.hh.vacancydetails

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.hh.main.AbstractViewUi
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class TextUi(id: Int, parent: Matcher<View>, rootClass: Matcher<View>) : AbstractViewUi(
    onView(
        allOf(
            withId(id),
            parent,
            rootClass,
            isAssignableFrom(TextView::class.java)
        )
    )
)
