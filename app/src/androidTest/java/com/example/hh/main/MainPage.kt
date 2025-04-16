package com.example.hh.main

import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.hh.R
import com.example.hh.core.Wait
import com.example.hh.loadvacancies.ErrorUi
import com.example.hh.loadvacancies.ProgressUi
import com.example.hh.main.data.cloud.Address
import com.example.hh.main.data.cloud.Area
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.data.cloud.Type
import com.example.hh.main.data.cloud.VacancyCloud
import com.example.hh.main.data.cloud.WorkFormat
import com.example.hh.main.data.cloud.WorkScheduleByDays
import com.example.hh.main.data.cloud.WorkingHours
import org.hamcrest.Matcher

class MainPage {

    companion object {
        val list = listOf(
            VacancyCloud(
                id = "1",
                name = "Android Developer",
                area = Area("1", "Казань"),
                salary = Salary(100000, 200000, "RUR", true),
                address = Address("Moscow", "Tverskaya"),
                employer = Employer("1", "Yandex", null),
                workFormat = listOf(WorkFormat("1", "Remote")),
                workingHours = listOf(WorkingHours("1", "Full day")),
                workScheduleByDays = listOf(WorkScheduleByDays("1", "5/2")),
                experience = Experience("1", "1-3 years"),
                url = "http://example.com",
                type = Type("1", "Direct")
            ),
            VacancyCloud(
                id = "2",
                name = "Project manager",
                area = Area("1", "Москва"),
                salary = Salary(50000, 100000, "RUR", true),
                address = Address("Moscow", "Tverskaya"),
                employer = Employer("25", "Ozon", null),
                workFormat = listOf(WorkFormat("2", "Remote2")),
                workingHours = listOf(WorkingHours("2", "Full day2")),
                workScheduleByDays = listOf(WorkScheduleByDays("2", "6/1")),
                experience = null,
                url = "http://example.com",
                type = Type("2", "Direct2")
            ),
            VacancyCloud(
                id = "3",
                name = "Android Senior Developer",
                area = Area("1", "Москва"),
                salary = Salary(300000, 500000, "RUR", true),
                address = Address("Moscow", "Tverskaya"),
                employer = Employer("1", "Yandex", null),
                workFormat = listOf(WorkFormat("1", "Remote")),
                workingHours = listOf(WorkingHours("1", "Full day")),
                workScheduleByDays = listOf(WorkScheduleByDays("1", "5/2")),
                experience = Experience("3", "6 years+"),
                url = "http://example.com",
                type = Type("1", "Direct")
            )
        )
    }

    private val rootId: Matcher<View> =
        withParent(withId(R.id.rootLayout))
    private val rootClass: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val inputUi: InputUi = InputUi(R.id.textInputForSearch)
    private val recyclerUi: RecyclerUi = RecyclerUi(rootId, rootClass, R.id.recyclerView)
    private val errorUi = ErrorUi(R.id.errorText)
    private val buttonUi =
        ButtonUi(withParent(withId(R.id.topLayout)), rootClass, R.id.filterButton, ImageButton::class.java)
    private val progressUi = ProgressUi()
    private val retryUi = RetryUi()

    fun assertInitialState() {
        inputUi.checkVisible()
        buttonUi.checkVisible()
        assertProgressState()
    }

    fun assertProgressState() {
        progressUi.checkVisible()
    }

    fun waiting(timeInMillis: Long = 300) {
        Espresso.onView(ViewMatchers.isRoot()).perform(Wait(timeInMillis))
    }

    fun assertErrorState() {
        errorUi.checkVisible()
    }

    fun clickRetry() {
        retryUi.click()
    }

    fun clickFilters() {
        buttonUi.click()
    }

    fun clickFavorite(position: Int) {
        recyclerUi.clickFavorite(position = position)
    }

    fun clickVacancy(position: Int) {
        recyclerUi.clickItem(position)
    }

    fun assertSuccessfulState() {
        recyclerUi.checkVacancies(
            position = 0,
            vacancy = list[0]
        )
        recyclerUi.checkVacancies(
            position = 1,
            vacancy = list[1]
        )
    }
}
