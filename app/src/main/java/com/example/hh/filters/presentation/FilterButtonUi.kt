package com.example.hh.filters.presentation

import com.example.hh.R
import com.example.hh.databinding.ItemAreasButtonBinding
import com.example.hh.databinding.ItemSearchFilterButtonBinding
import com.example.hh.main.presentation.ItemsUi

interface FilterButtonUi : ItemsUi {

    fun type(): FiltersButtonUiType
    fun show(binding: ItemSearchFilterButtonBinding) = Unit
    fun showAreaButton(binding: ItemAreasButtonBinding) = Unit
    fun id(): String
    fun changeChosen(): FilterButtonUi
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

        override fun type(): FiltersButtonUiType {
            return FiltersButtonUiType.FiltersButton
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

        override fun changeChosen(): FilterButtonUi {
            return Base(textResId, queryForFilter, listId, !chosen)
        }

        override fun chosen(): Boolean {
            return chosen
        }
    }

    data class AreaButton(
        private val id: String,
        private val value: String,
        private val chosen: Boolean = false
    ) : FilterButtonUi {

        override fun showAreaButton(binding: ItemAreasButtonBinding) = with(binding.areaButton) {
            text = value
            if (chosen) {
                setBackgroundColor(resources.getColor(R.color.white))
                setTextColor(resources.getColor(R.color.black))
            } else {
                setBackgroundColor(resources.getColor(R.color.dimGray))
                setTextColor(resources.getColor(R.color.white))
                setBackgroundColor(R.drawable.area_button_state)
            }
        }

        override fun type(): FiltersButtonUiType {
            return FiltersButtonUiType.AreasButton
        }

        override fun id() = id

        override fun changeChosen(): FilterButtonUi {
            return AreaButton(id, value, !chosen)
        }

        override fun chosen(): Boolean {
            return chosen
        }

        override fun query(): String {
            return value
        }

        override fun listId() = id
    }
}