package com.example.hh.views.text

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.hh.R

class CustomTextView : androidx.appcompat.widget.AppCompatTextView, UpdateCustomTextView {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getFreezesText(): Boolean = true

    fun init(viewModel: CustomTextViewModel) {
        viewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            it.show(this)
            it.handle(text.toString(), viewModel)
        }
    }

    private var colorToSaveAndRestore: Int = R.color.black

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = CustomTextSavedState(it)
            savedState.save(TextPermanentState(colorToSaveAndRestore))
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CustomTextSavedState
        super.onRestoreInstanceState(restoredState.superState)
        val permanentState = restoredState.restore()
        colorToSaveAndRestore = permanentState.color
        setTextColor(resources.getColor(colorToSaveAndRestore, null))
    }

    override fun show(text: String, color: Int) {
        colorToSaveAndRestore = color
        setText(text)
        setTextColor(resources.getColor(color, null))
    }
}

interface UpdateCustomTextView {
    fun show(text: String, @ColorRes color: Int)
}