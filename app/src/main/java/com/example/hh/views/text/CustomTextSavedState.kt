package com.example.hh.views.text

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.os.Build
import androidx.annotation.ColorRes
import java.io.Serializable

class CustomTextSavedState : View.BaseSavedState {

    private lateinit var state: TextPermanentState

    constructor(superState: Parcelable) : super(superState)

      private constructor(parcelIn: Parcel) : super(parcelIn) {
       state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             parcelIn.readSerializable(TextPermanentState::class.java.classLoader, TextPermanentState::class.java) as TextPermanentState
        } else {
           parcelIn.readSerializable() as TextPermanentState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): TextPermanentState = state

    fun save(uiState: TextPermanentState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CustomTextSavedState> {
        override fun createFromParcel(parcel: Parcel): CustomTextSavedState =
            CustomTextSavedState(parcel)

        override fun newArray(size: Int): Array<CustomTextSavedState?> =
            arrayOfNulls(size)
    }
}

data class TextPermanentState(val color: Int) : Serializable