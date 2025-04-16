package com.example.hh.loadvacancies

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.hh.main.AbstractViewUi
import org.hamcrest.Matchers.allOf

class ErrorUi(id: Int) : AbstractViewUi(
    onView(
        allOf(
            withId(id),
            isAssignableFrom(TextView::class.java)
        )
    )
)
