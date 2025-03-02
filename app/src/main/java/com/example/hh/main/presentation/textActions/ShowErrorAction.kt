package com.example.hh.main.presentation.textActions

import com.example.hh.R
import com.example.hh.views.text.CustomTextUiState
import com.example.hh.views.text.UpdateCustomTextView

class ShowErrorAction(private val value: String) : CustomTextUiState {

    override fun show(updateCustomTextView: UpdateCustomTextView) {
        updateCustomTextView.show("No internet connection", R.color.red)
    }
}