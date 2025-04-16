package com.example.hh.views.input

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class CustomInputSavedState : View.BaseSavedState {

    private val savedStates = mutableMapOf<String, CustomInputUiState>()

    private lateinit var state: CustomInputUiState
    private var uniqueId: String? = null

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        val size = parcelIn.readInt()
        for (i in 0 until size) {
            val key = parcelIn.readString() ?: continue // Читаем uniqueId
            val state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                parcelIn.readSerializable(
                    CustomInputUiState::class.java.classLoader,
                    CustomInputUiState::class.java
                ) as CustomInputUiState
            } else {
                parcelIn.readSerializable() as CustomInputUiState
            }
            savedStates[key] = state // Сохраняем состояние в Map
        }
    }
//        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            parcelIn.readSerializable(
//                CustomInputUiState::class.java.classLoader,
//                CustomInputUiState::class.java
//            ) as CustomInputUiState
//        } else {
//            parcelIn.readSerializable() as CustomInputUiState
//        }
//        uniqueId = parcelIn.readString()
//    }

//    override fun writeToParcel(out: Parcel, flags: Int) {
//        super.writeToParcel(out, flags)
//        out.writeSerializable(state)
//        out.writeString(uniqueId)
//    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(savedStates.size) // Сохраняем количество состояний
        savedStates.forEach { (key, state) ->
            out.writeString(key) // Сохраняем uniqueId
            out.writeSerializable(state) // Сохраняем состояние
        }
    }

    fun restore(uniqueId: String): CustomInputUiState {
        return if (this.uniqueId != null && this.uniqueId == uniqueId){
            state
        } else {
            CustomInputUiState.Initial("")
        }
    }

    fun saveState(uniqueId: String, uiState: CustomInputUiState) {
        savedStates[uniqueId] = uiState
    }

    fun restoreState(uniqueId: String): CustomInputUiState {
        return savedStates[uniqueId] ?: CustomInputUiState.Initial("22")
    }

    fun save(uiState: CustomInputUiState, uniqueId: String) {
        state = uiState
        this.uniqueId = uniqueId
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CustomInputSavedState> {
        override fun createFromParcel(parcel: Parcel): CustomInputSavedState =
            CustomInputSavedState(parcel)

        override fun newArray(size: Int): Array<CustomInputSavedState?> =
            arrayOfNulls(size)
    }
}