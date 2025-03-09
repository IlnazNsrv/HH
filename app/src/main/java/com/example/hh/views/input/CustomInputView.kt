package com.example.hh.views.input

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.hh.databinding.InputBinding

class CustomInputView : FrameLayout, UpdateCustomInput {

    private var state: CustomInputUiState = CustomInputUiState.Initial("")
    private val binding =
        InputBinding.inflate(LayoutInflater.from(context), this, true)


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun init(viewModel: CustomInputViewModel) {
        viewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            it.show(this)
        }



        binding.inputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.handleClick(text())
                true
            } else {
                false
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = CustomInputSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CustomInputSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: CustomInputUiState) {
        state = uiState
        state.show(this)
    }

    override fun update(userInput: String) {
        binding.inputEditText.setText(userInput)
    }

    fun text(): String {
        return binding.inputEditText.text.toString()
    }
}

interface UpdateCustomInput {

    fun update(uiState: CustomInputUiState)

    fun update(userInput: String)

    //fun update(errorIsEnabled: Boolean, enabled: Boolean)

}