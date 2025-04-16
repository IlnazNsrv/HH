package com.example.hh.filters.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hh.databinding.ItemAreasButtonBinding
import com.example.hh.databinding.ItemSearchFilterButtonBinding

interface FiltersButtonUiType {

    fun viewHolder(parent: ViewGroup, clickActions: ChooseButton): ButtonViewHolder

    object AreasButton : FiltersButtonUiType {
        override fun viewHolder(
            parent: ViewGroup,
            clickActions: ChooseButton
        ): ButtonViewHolder {
            return ButtonViewHolder.AreaButton(
                ItemAreasButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                ), clickActions
            )
        }
    }

    object FiltersButton : FiltersButtonUiType {

        override fun viewHolder(
            parent: ViewGroup,
            clickActions: ChooseButton
        ): ButtonViewHolder {
            return ButtonViewHolder.FiltersButton(
                ItemSearchFilterButtonBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                ), clickActions
            )
        }
    }
}