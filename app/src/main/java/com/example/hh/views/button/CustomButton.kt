package com.example.hh.views.button

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.google.android.material.button.MaterialButton

class CustomButton : MaterialButton, UpdateCustomButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getFreezesText(): Boolean = true

    fun init(viewModel: CustomButtonViewModel) {
        viewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            it.show(this)
        }

        setOnClickListener {
            viewModel.handleClick()
        }
    }

    override fun onSaveInstanceState(): Parcelable {
         super.onSaveInstanceState().let {
            val savedState = CustomButtonSavedState(it)
            savedState.save(CustomButtonPermanentState(visibility, isEnabled))
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CustomButtonSavedState
        super.onRestoreInstanceState(restoredState.superState)
        val permanentState = restoredState.restore()
        this.visibility = permanentState.visibility
        this.isEnabled = permanentState.enabled
    }

    override fun update(@StringRes textResId: Int, visible: Boolean, enabled: Boolean) {
        setText(textResId)
        visibility = if (visible) View.VISIBLE else View.GONE
        isEnabled = enabled
    }
}

interface UpdateCustomButton {

    fun update(@StringRes textResId: Int, visible: Boolean, enabled: Boolean = true)
}