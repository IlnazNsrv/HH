package com.example.hh.loadvacancies

import android.widget.ProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.hh.R
import com.example.hh.main.AbstractViewUi
import org.hamcrest.Matchers.allOf

class ProgressUi(id: Int = R.id.progressBar) : AbstractViewUi(
    onView(
        allOf(
            withId(id),
            isAssignableFrom(ProgressBar::class.java)
        )
    )
)
