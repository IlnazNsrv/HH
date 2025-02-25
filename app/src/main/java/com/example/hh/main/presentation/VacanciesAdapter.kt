package com.example.hh.main.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hh.databinding.ItemErrorBinding
import com.example.hh.databinding.ItemProgressBinding
import com.example.hh.databinding.ItemVacancyBinding

class VacanciesAdapter(
    private val typeList: List<VacancyUiType> = listOf(
        VacancyUiType.Error,
        VacancyUiType.Progress,
        VacancyUiType.Vacancy
    )
) : RecyclerView.Adapter<VacancyViewHolder>() {

    private val list = mutableListOf<VacancyUi>()

    fun updateVacancies(newList: List<VacancyUi>) {
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

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        val type = item.type()
        val index = typeList.indexOf(type)
        if (index == -1)
            throw IllegalStateException("add type $type to typeList $typeList")\
        return index //0, 1, 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return typeList[viewType].viewHolder(parent, clickActions)
    }
//        VacancyViewHolder(
//            ItemVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        )

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}

abstract class VacancyViewHolder(
    view: View
) : ViewHolder(view) {

    open fun bind(vacancy: VacancyUi) = Unit

    class Progress(view: View) : VacancyViewHolder(view)

    class Error(
        private val binding: ItemErrorBinding,
        private val clickActions: ClickActions
    ) : VacancyViewHolder(binding.root) {

        override fun bind(vacancy: VacancyUi) {
            vacancy.showError(binding)
            binding.retryButton.setOnClickListener {
                clickActions.retry()
            }
        }
    }

    class Vacancy(
        private val binding: ItemVacancyBinding,
        private val clickActions: ClickActions
    ) : VacancyViewHolder(binding.root) {

        override fun bind(vacancy: VacancyUi) {
            binding.favoriteButton.setOnClickListener {
                vacancy.favoriteOrNot(clickActions)
            }
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

