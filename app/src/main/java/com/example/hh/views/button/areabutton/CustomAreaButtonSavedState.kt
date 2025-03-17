package com.example.hh.views.button.areabutton

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import java.io.Serializable

class CustomAreaButtonSavedState : View.BaseSavedState {

    private lateinit var state: CustomAreaPermanentState

    constructor(superState: Parcelable) : super(superState)

      private constructor(parcelIn: Parcel) : super(parcelIn) {
       state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             parcelIn.readSerializable(CustomAreaPermanentState::class.java.classLoader, CustomAreaPermanentState::class.java) as CustomAreaPermanentState
        } else {
           parcelIn.readSerializable() as CustomAreaPermanentState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): CustomAreaPermanentState = state

    fun save(uiState: CustomAreaPermanentState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CustomAreaButtonSavedState> {
        override fun createFromParcel(parcel: Parcel): CustomAreaButtonSavedState =
            CustomAreaButtonSavedState(parcel)

        override fun newArray(size: Int): Array<CustomAreaButtonSavedState?> =
            arrayOfNulls(size)
    }
}

data class CustomAreaPermanentState(val visibility: Int, val text: String) : Serializable