package com.example.hh.main

import android.widget.Button
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.hh.R
import org.hamcrest.Matchers.allOf

class RetryUi : AbstractViewUi(
    onView(
        allOf(
            withId(R.id.retryButton),
            isAssignableFrom(Button::class.java)
        )
    )
)
