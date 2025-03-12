package com.example.hh.views.input

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.hh.databinding.InputBinding
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies

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

    private fun showKeyboard(view: View) {
        val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun init(viewModel: CustomInputViewModel, navigateToLoadVacancies: NavigateToLoadVacancies) {
        viewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            it.show(this)
        }

        binding.inputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                showKeyboard(this)
        }

        binding.inputEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_SPACE) {
                viewModel.handleClick(text(), navigateToLoadVacancies)
                true
            } else {
                false
            }
        }

        binding.inputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == KeyEvent.KEYCODE_SPACE) {
                viewModel.handleClick(text(), navigateToLoadVacancies)
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