package com.example.hh.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.CoreMatchers.allOf

class InputUi(
    resId: Int
) : AbstractViewUi(
    onView(
        allOf(
            withId(resId),
            isAssignableFrom(TextInputEditText::class.java),
        )
    )
) {
    fun inputText(text: String) {
        viewInteraction.perform(replaceText(text), closeSoftKeyboard())
    }
}
