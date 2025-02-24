package com.example.hh.main.presentation

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView : RecyclerView, UpdateVacanciesRecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var adapter: VacanciesAdapter

    fun init(viewModel: VacanciesViewModel) {

        adapter = VacanciesAdapter()
        setAdapter(adapter)

        viewModel.liveData().observe(findViewTreeLifecycleOwner()) {
            it.show(this)
        }
    }

    override fun update(list: List<VacancyUi>) {
        adapter.updateVacancies(list)
    }
}

interface UpdateVacanciesRecyclerView {

    fun update(list: List<VacancyUi>)
}