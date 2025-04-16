package com.example.hh.filters.areafilters.presentation.screen

import com.example.hh.databinding.ItemAreasButtonBinding
import com.example.hh.main.presentation.ItemsUi

interface AreaUi : ItemsUi {

    fun show(binding: ItemAreasButtonBinding)
    fun id() : String
    fun changeChosen() : AreaUi
    fun chosen() : Boolean

    data class Base(
        private val id: String,
        private val value: String,
        private val chosen: Boolean = false
    ) : AreaUi {
        override fun show(binding: ItemAreasButtonBinding) = with(binding.areaButton) {
        }

        override fun id() = id

        override fun changeChosen(): AreaUi {
           return Base(id, value, !chosen)
        }

        override fun chosen(): Boolean {
           return chosen
        }
    }
}