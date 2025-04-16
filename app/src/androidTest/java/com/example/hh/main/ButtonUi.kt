package com.example.hh.main

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class ButtonUi(
    rootId: Matcher<View>,
    rootClass: Matcher<View>,
    resId: Int,
    type: Class<out View>
) : AbstractViewUi(
    onView(
        Matchers.allOf(
            withId(resId),
            isAssignableFrom(type),
            rootId,
            rootClass,
        )
    )
)