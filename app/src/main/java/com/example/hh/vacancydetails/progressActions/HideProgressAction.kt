package com.example.hh.vacancydetails.progressActions

import com.example.hh.views.progress.CustomProgressBarUiState
import com.example.hh.views.progress.UpdateCustomProgress

class HideProgressAction : CustomProgressBarUiState {

    override fun show(updateCustomProgress: UpdateCustomProgress) {
        updateCustomProgress.show(false)
    }
}