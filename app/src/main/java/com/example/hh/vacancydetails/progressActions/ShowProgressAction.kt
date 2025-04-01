package com.example.hh.vacancydetails.progressActions

import com.example.hh.views.progress.CustomProgressBarUiState
import com.example.hh.views.progress.UpdateCustomProgress

class ShowProgressAction : CustomProgressBarUiState {

    override fun show(updateCustomProgress: UpdateCustomProgress) {
        updateCustomProgress.show(true)
    }
}