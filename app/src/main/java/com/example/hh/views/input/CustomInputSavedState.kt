package com.example.hh.views.input

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class CustomInputSavedState : View.BaseSavedState {

    private lateinit var state: CustomInputUiState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(
                CustomInputUiState::class.java.classLoader,
                CustomInputUiState::class.java
            ) as CustomInputUiState
        } else {
            parcelIn.readSerializable() as CustomInputUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): CustomInputUiState = state

    fun save(uiState: CustomInputUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CustomInputSavedState> {
        override fun createFromParcel(parcel: Parcel): CustomInputSavedState =
            CustomInputSavedState(parcel)

        override fun newArray(size: Int): Array<CustomInputSavedState?> =
            arrayOfNulls(size)
    }
}