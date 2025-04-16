package com.example.hh.filters

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
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
        R.id.saveArea,
        Button::class.java
    )
    private val inputUi = InputUi(R.id.inputAreaEditText)

    fun findArea(text: String) {
        inputUi.inputText(text)
        recyclerUi.checkAreasButton(0, "Москва")
    }

    fun findKzn() {
        inputUi.inputText("Казань")
        recyclerUi.clickItem(0)
        buttonUi.click()
    }

    fun saveChosenArea() {
        recyclerUi.clickItem(0)
        buttonUi.click()
    }
}