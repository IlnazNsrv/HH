package com.example.hh.views.progress

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import java.io.Serializable

class CustomProgressSavedState : View.BaseSavedState {

    private lateinit var state: CustomProgressPermanentState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(CustomProgressPermanentState::class.java.classLoader, CustomProgressPermanentState::class.java) as CustomProgressPermanentState
        } else {
            parcelIn.readSerializable() as CustomProgressPermanentState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): CustomProgressPermanentState = state

    fun save(uiState: CustomProgressPermanentState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CustomProgressSavedState> {
        override fun createFromParcel(parcel: Parcel): CustomProgressSavedState =
            CustomProgressSavedState(parcel)

        override fun newArray(size: Int): Array<CustomProgressSavedState?> =
            arrayOfNulls(size)
    }
}

data class CustomProgressPermanentState(val visibility: Int) : Serializable