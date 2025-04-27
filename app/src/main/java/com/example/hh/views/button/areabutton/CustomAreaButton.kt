package com.example.hh.views.button.areabutton

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.hh.R
import com.example.hh.databinding.AreaButtonBinding

class CustomAreaButton : RelativeLayout, UpdateCustomAreaButton {

    private val binding =
        AreaButtonBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun init(viewModel: CustomAreaButtonViewModel, fragmentManager: FragmentManager) {

        viewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            it.show(this)
        }
        
        viewModel.updateState()

        binding.cityNameButton.setOnClickListener {
            viewModel.handleClickArea(fragmentManager)
        }

        binding.removeCityButton.setOnClickListener {
            viewModel.handleCloseAction()
            this.visibility = GONE
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = CustomAreaButtonSavedState(it)
            savedState.save(CustomAreaPermanentState(visibility, binding.cityNameButton.text.toString()))
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CustomAreaButtonSavedState
        super.onRestoreInstanceState(restoredState.superState)
        val permanentState = restoredState.restore()
        this.visibility = permanentState.visibility
        binding.cityNameButton.text = permanentState.text
    }

    override fun update(areaPair: Pair<String, String>?) {
        if (areaPair == null) {
            this.visibility = View.GONE
            binding.cityNameButton.text = ""
        } else {
            binding.cityNameButton.setTextColor(resources.getColor(R.color.white))
            binding.cityNameButton.text = areaPair.second
            this.visibility = View.VISIBLE
        }
    }
}

interface UpdateCustomAreaButton {
    fun update(areaPair: Pair<String, String>?)
}