package com.example.hh.views.input

import android.content.Context
import android.os.Parcelable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.hh.R
import com.example.hh.databinding.InputBinding
import java.util.UUID

class CustomInputView : FrameLayout, UpdateCustomInput {

    companion object {
        const val STRING_INPUT_VIEW_TYPE_TAG = "string"
        const val INT_INPUT_VIEW_TYPE_TAG = "number"
    }

    private var uniqueId: String = UUID.randomUUID().toString()

    private var state: CustomInputUiState = CustomInputUiState.Initial("")
    private val binding =
        InputBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var inputViewModel: CustomInputViewModel
    private lateinit var textWatcherString: TextWatcher
    private lateinit var textWatcherNumber: TextWatcher

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    private fun showKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun textWatcherCreator(type: String): TextWatcher {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                when (type) {
                    STRING_INPUT_VIEW_TYPE_TAG -> handleTextInput(s)
                    INT_INPUT_VIEW_TYPE_TAG -> handleNumberInput(s)
                }
            }
        }
        return textWatcher
    }


    private fun handleTextInput(s: Editable?) {
        inputViewModel.cacheInputText(s.toString())
    }

    private fun handleNumberInput(s: Editable?) {
        val input = s.toString()
        inputViewModel.cacheInputNumber(input.toIntOrNull())
    }

    fun init(
        viewModel: CustomInputViewModel,
        type: String,
    ) {
        //uniqueId = type

        inputViewModel = viewModel

        when (type) {
            INT_INPUT_VIEW_TYPE_TAG -> setNumberTypeFeatures()
            STRING_INPUT_VIEW_TYPE_TAG -> setStringTypeFeatures()
        }


        binding.inputEditText.setTextColor(resources.getColor(R.color.black))

        // binding.inputEditText.setBackgroundColor(resources.getColor(R.color.white))

        inputViewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            it.show(this)
        }

        binding.inputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                showKeyboard(this)
//            else {
//                hideKeyboard(this)
//            }
        }
    }

    private fun setNumberTypeFeatures() {
        binding.inputEditText.inputType =
            InputType.TYPE_CLASS_NUMBER or
                    InputType.TYPE_NUMBER_VARIATION_NORMAL or
                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.inputEditText.hint = "Уровень дохода от"
    }

    private fun setStringTypeFeatures() {
        binding.inputEditText.inputType =
            InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_NORMAL or
                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.inputEditText.hint = "Должность, ключевые слова"
    }

    fun addTextChangedListener() {
        // binding.inputEditText.addTextChangedListener(textWatcher)
    }

    // fun removeTextChangedListener() = binding.inputEditText.removeTextChangedListener(textWatcher)

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = CustomInputSavedState(it)
            savedState.saveState(uniqueId, state)
            return savedState
        }
        Log.d("CustomInputView", "Saved state for $uniqueId: $state")
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CustomInputSavedState
        super.onRestoreInstanceState(restoredState.superState)
        binding.inputEditText.text?.clear()
  //      update(restoredState.restore(uniqueId = uniqueId))
        val restoredUiState = restoredState.restoreState(uniqueId)
        binding.inputEditText.setText(restoredUiState.text())
       // update(restoredUiState)
        Log.d("CustomInputView", "Restored state for $uniqueId: $restoredUiState")
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

    fun update(uiState: CustomInputUiState) = Unit

    fun update(userInput: String)
}

class CustomTextWatcher(private val onTextChanged: (String) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(s: Editable?) {
        onTextChanged(s.toString())
    }
}