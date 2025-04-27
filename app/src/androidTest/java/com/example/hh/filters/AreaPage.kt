package com.example.hh.filters

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
import com.example.hh.core.waiting
import com.example.hh.main.ButtonUi
import com.example.hh.main.InputUi
import com.example.hh.main.RecyclerUi
import org.hamcrest.Matcher

class AreaPage {

    private val rootId: Matcher<View> =
        withParent(withId(R.id.standardBottomSheet))
    private val rootClass: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val recyclerUi: RecyclerUi = RecyclerUi(rootId, rootClass, R.id.areaRecyclerView)
    private val buttonUi = ButtonUi(
        withParent(withId(R.id.bottomLayout)),
        rootClass,
        R.id.saveAreaButton,
        Button::class.java
    )
    private val inputUi = InputUi(R.id.inputAreaEditText)

    fun searchArea(text: String) {
        inputUi.inputText(text)
        waiting(500)
    }

    fun assertAreaButton(area: String) {
        recyclerUi.checkAreasButton(0, area)
    }

    fun saveChosenArea(position: Int) {
        recyclerUi.clickItem(position)
        buttonUi.click()
    }
}