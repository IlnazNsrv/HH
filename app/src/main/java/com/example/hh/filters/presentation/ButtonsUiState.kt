package com.example.hh.filters.presentation

import com.example.hh.core.UiState
import com.example.hh.main.presentation.UpdateItemsRecyclerView

interface ButtonsUiState : UiState {

    fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<FilterButtonUi>)
    fun click(buttonUi: FilterButtonUi) : ButtonsUiState = this

    data class Show(private val list: List<FilterButtonUi>) : ButtonsUiState {

        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<FilterButtonUi>) {
            updateItemsRecyclerView.update(list)
        }

        override fun click(buttonUi: FilterButtonUi): ButtonsUiState {
            val newList = list.toMutableList()
            val item = list.find { it.id() == buttonUi.id()}!!
            val index = list.indexOf(item)
            newList[index] = item.changeChose()
            return Show(newList)
        }

    }
}