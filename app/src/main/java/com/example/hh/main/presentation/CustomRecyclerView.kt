package com.example.hh.main.presentation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.hh.main.data.BundleWrapper

class CustomRecyclerView : RecyclerView, UpdateVacanciesRecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var adapter: VacanciesAdapter


    fun init(viewModel: VacanciesViewModel, liveDataWrapper: VacanciesLiveDataWrapper) {

        adapter = VacanciesAdapter(clickActions = viewModel, liveDataWrapper = liveDataWrapper)
        setAdapter(adapter)

        viewModel.liveData().observe(findViewTreeLifecycleOwner()!!) {
            it.handle(viewModel)
            it.show(this)
        }
    }

    fun save(bundle: Bundle) {
        adapter.save(BundleWrapper.Base(bundle))
    }

    fun restore(bundle: Bundle) {
        adapter.restore(BundleWrapper.Base(bundle))
    }

    override fun update(list: List<VacancyUi>) {
        adapter.updateVacancies(list)
    }
}

interface UpdateVacanciesRecyclerView {

    fun update(list: List<VacancyUi>)
}