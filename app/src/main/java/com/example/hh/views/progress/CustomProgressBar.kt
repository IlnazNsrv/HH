package com.example.hh.views.progress

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.findViewTreeLifecycleOwner

class CustomProgressBar : ProgressBar, UpdateCustomProgress {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun init(viewModel: CustomProgressViewModel) {
        viewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            it.show(this)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = CustomProgressSavedState(it)
            savedState.save(CustomProgressPermanentState(visibility))
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CustomProgressSavedState
        super.onRestoreInstanceState(restoredState.superState)
        val permanentState = restoredState.restore()
        this.visibility = permanentState.visibility
    }

    override fun show(visible: Boolean) {
        this.visibility = if (visible) View.VISIBLE else View.GONE
    }

}

interface UpdateCustomProgress {
    fun show(visible: Boolean)
}