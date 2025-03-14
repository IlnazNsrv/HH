package com.example.hh.filters.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hh.databinding.ItemSearchFilterButtonBinding
import com.example.hh.main.presentation.UpdateItems

class FiltersAdapter(
    private val clickListener: ChooseButton
) : RecyclerView.Adapter<ButtonViewHolder>(), UpdateItems<FilterButtonUi> {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ButtonViewHolder(
        ItemSearchFilterButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        clickListener
    )

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.binding(list[position])
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
}

class ButtonViewHolder(
    private val binding: ItemSearchFilterButtonBinding,
    private val clickListener: ChooseButton
) : ViewHolder(binding.root) {

    fun binding(button: FilterButtonUi) {
        binding.nameFilterButton.setOnClickListener {
            clickListener.choose(button)
        }
        button.show(binding)
    }
}

interface ChooseButton {

    companion object {
        const val EXPERIENCE_TAG: String = "experience"
        const val SCHEDULE_TAG = "schedule"
        const val SEARCH_FIELD_TAG = "searchField"
        const val EMPLOYMENT_TAG = "employment"
    }

    fun choose(buttonUi: FilterButtonUi)
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