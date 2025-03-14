package com.example.hh.filters.presentation

import com.example.hh.R
import com.example.hh.databinding.ItemSearchFilterButtonBinding
import com.example.hh.main.presentation.ItemsUi

interface FilterButtonUi : ItemsUi {


    fun show(binding: ItemSearchFilterButtonBinding)
    fun id(): String
    fun changeChose(): FilterButtonUi
    fun chosen(): Boolean
    fun query(): String
    fun listId(): String

    data class Base(
        private val textResId: Int,
        private val queryForFilter: String,
        private val listId: String,
        private var chosen: Boolean = false
    ) : FilterButtonUi {

        override fun show(binding: ItemSearchFilterButtonBinding) = with(binding.nameFilterButton) {
            setText(textResId)
            if (chosen) {
                setBackgroundColor(resources.getColor(R.color.white))
                setTextColor(resources.getColor(R.color.black))
            } else {
                setBackgroundColor(resources.getColor(R.color.dimGray))
                setTextColor(resources.getColor(R.color.white))
            }
        }

        override fun id(): String {
            return queryForFilter
        }

        override fun query(): String {
            return queryForFilter
        }

        override fun listId(): String {
            return listId
        }

        override fun changeChose(): FilterButtonUi {
            //chosen = !chosen
            return Base(textResId, queryForFilter, listId, !chosen)
            // return this
        }

        override fun chosen(): Boolean {
            return chosen
        }
    }
}