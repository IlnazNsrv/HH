package com.example.hh.filters.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hh.databinding.ItemAreasButtonBinding
import com.example.hh.databinding.ItemSearchFilterButtonBinding
import com.example.hh.main.data.BundleWrapper
import com.example.hh.main.presentation.adapter.SaveItems
import com.example.hh.main.presentation.adapter.UpdateItems

class FiltersAdapter(
    private val typeList: List<FiltersButtonUiType> = listOf(
        FiltersButtonUiType.FiltersButton,
        FiltersButtonUiType.AreasButton
    ),
    private val clickListener: ChooseButton,
    private val liveDataWrapper: FilterButtonsLiveDataWrapper<FilterButtonUi>
) : RecyclerView.Adapter<ButtonViewHolder>(),
    UpdateItems<FilterButtonUi>, SaveItems<ButtonsUiState<FilterButtonUi>> {

    private val list = mutableListOf<FilterButtonUi>()

    fun update(newList: List<FilterButtonUi>) {
        val result = DiffUtil.calculateDiff(
            Diff(
                list,
                newList
            )
        )
        list.clear()
        list.addAll(newList)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        return typeList[viewType].viewHolder(parent, clickListener)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        val type = item.type()
        val index = typeList.indexOf(type)
        if (index == -1)
            throw IllegalStateException("add type $type to typeList")
        return index
    }

    override fun getItemCount(): Int = list.size
    override fun updateItems(newList: List<FilterButtonUi>) {
        val result = DiffUtil.calculateDiff(
            Diff(
                list,
                newList
            )
        )
        list.clear()
        list.addAll(newList)
        result.dispatchUpdatesTo(this)
    }

    override fun save(bundle: BundleWrapper.Save<ButtonsUiState<FilterButtonUi>>) {
        liveDataWrapper.save(bundle)
    }

    override fun restore(bundleWrapper: BundleWrapper.Restore<ButtonsUiState<FilterButtonUi>>) {
        val uiState = bundleWrapper.restore()
        liveDataWrapper.update(uiState)
    }
}

interface ChooseButton {

    fun choose(buttonUi: FilterButtonUi) = Unit
}

private class Diff(
    private val old: List<FilterButtonUi>,
    private val new: List<FilterButtonUi>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id() == new[newItemPosition].id()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}

abstract class ButtonViewHolder(
    view: View
) : ViewHolder(view) {

    open fun bind(button: FilterButtonUi) = Unit

    class FiltersButton(
        private val binding: ItemSearchFilterButtonBinding,
        private val chooseButton: ChooseButton
    ) :
        ButtonViewHolder(binding.root) {

        override fun bind(button: FilterButtonUi) {
            binding.nameFilterButton.setOnClickListener {
                chooseButton.choose(button)
            }
            button.show(binding)
        }
    }

    class AreaButton(
        private val binding: ItemAreasButtonBinding,
        private val chooseButton: ChooseButton
    ) : ButtonViewHolder(binding.root) {

        override fun bind(button: FilterButtonUi) {
            binding.areaButton.setOnClickListener {
                chooseButton.choose(button)
            }
            button.showAreaButton(binding)
        }
    }
}