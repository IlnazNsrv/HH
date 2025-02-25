package com.example.hh.main.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hh.databinding.ItemVacancyBinding

class VacanciesAdapter(

) : RecyclerView.Adapter<VacanciesAdapter.VacancyViewHolder>() {

    private val list = mutableListOf<VacancyUi>()

    fun updateVacancies(newList: List<VacancyUi>) {
        val result = DiffUtil.calculateDiff(Diff(
            list,
            newList
        ))
        list.clear()
        list.addAll(newList)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder = VacancyViewHolder(
        ItemVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class VacancyViewHolder(
        private val binding: ItemVacancyBinding
    ) : ViewHolder(binding.root) {

        fun bind(vacancy: VacancyUi) {
            vacancy.show(binding)
        }
    }

}

private class Diff(
    private val old: List<VacancyUi>,
    private val new: List<VacancyUi>
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

