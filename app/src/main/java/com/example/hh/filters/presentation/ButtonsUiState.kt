package com.example.hh.filters.presentation

import com.example.hh.core.UiState
import com.example.hh.filters.areafilters.presentation.screen.AreaUi
import com.example.hh.main.presentation.ItemsUi
import com.example.hh.main.presentation.UpdateItemsRecyclerView

interface ButtonsUiState<T : ItemsUi> : UiState {

    fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<T>)
    fun click(buttonUi: T) : ButtonsUiState<T> = this

    data class Show(private val list: List<FilterButtonUi>) : ButtonsUiState<FilterButtonUi> {

        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<FilterButtonUi>) {
            updateItemsRecyclerView.update(list)
        }

        override fun click(buttonUi: FilterButtonUi): ButtonsUiState<FilterButtonUi> {
            val newList = list.toMutableList()
            val item = list.find { it.id() == buttonUi.id()}!!
            val index = list.indexOf(item)
            newList[index] = item.changeChose()
            return Show(newList)
        }

    }

    data class ShowAreaButtons(private val list: List<AreaUi>) : ButtonsUiState<AreaUi> {

        override fun show(updateItemsRecyclerView: UpdateItemsRecyclerView<AreaUi>) {
            updateItemsRecyclerView.update(list)
        }

        override fun click(buttonUi: AreaUi): ButtonsUiState<AreaUi> {
            val newList = list.toMutableList()
            list.find { it.chosen() }?.let {
                val chosenIndex = list.indexOf(it)
                newList[chosenIndex] = it.changeChosen()
            }

            val item = list.find { it.id() == buttonUi.id() }!!
            val index = list.indexOf(item)
            newList[index] = item.changeChosen()
            return ShowAreaButtons(newList)
        }
    }
}