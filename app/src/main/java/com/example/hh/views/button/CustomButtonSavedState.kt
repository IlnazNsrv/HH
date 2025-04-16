package com.example.hh.views.button

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.os.Build
import java.io.Serializable

class CustomButtonSavedState : View.BaseSavedState {

    private lateinit var state: CustomButtonPermanentState

    constructor(superState: Parcelable) : super(superState)

      private constructor(parcelIn: Parcel) : super(parcelIn) {
       state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             parcelIn.readSerializable(CustomButtonPermanentState::class.java.classLoader, CustomButtonPermanentState::class.java) as CustomButtonPermanentState
        } else {
           parcelIn.readSerializable() as CustomButtonPermanentState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): CustomButtonPermanentState = state

    fun save(uiState: CustomButtonPermanentState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CustomButtonSavedState> {
        override fun createFromParcel(parcel: Parcel): CustomButtonSavedState =
            CustomButtonSavedState(parcel)

        override fun newArray(size: Int): Array<CustomButtonSavedState?> =
            arrayOfNulls(size)
    }
}

data class CustomButtonPermanentState(val visibility: Int, val enabled: Boolean) : Serializable