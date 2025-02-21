package com.example.hh.main

import com.example.hh.R

class MainPage : AbstractPage(R.id.activityMain) {

    private val inputUi: InputUi = InputUi()
    private val recyclerUi: RecyclerUi = RecyclerUi()

    fun assertInitialState() {
        inputUi.assertTextFieldEnableAndEmpty()
        recyclerUi.checkItemsCount(3)
    }

    fun addUserInput(text: String) {
        inputUi.addText(text = text)
        recyclerUi.clickSearch(text = text)
    }

    fun assertProgressState() {
        recyclerUi.assertProgressState()
    }

    fun waitTillError() {
        recyclerUi.waitTillErrorState()
    }

    fun assertErrorState() {
        recyclerUi.assertErrorState()
    }

    fun clickRetry() {
        recyclerUi.clickRetry()
    }

    fun clickFirstVacancyRespondButton() {
        recyclerUi.clickVacancyRespondButton(0)
    }

    fun assertFirstVacancyRespondedState() {
        recyclerUi.assertVacancyRespondedState(0)
    }

    fun waitTillGone() {
        recyclerUi.assertWaitTillGone()
    }

    fun clickFirstVacancy() {
        recyclerUi.clickVacancy(0)
    }

    fun clickDownloadMoreButton() {
        recyclerUi.clickDownloadMoreButton()
    }

    fun assertLotsVacancies() {
        recyclerUi.checkItemsCount(5)

    }
}
