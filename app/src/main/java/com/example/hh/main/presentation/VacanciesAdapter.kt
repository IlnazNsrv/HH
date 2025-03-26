package com.example.hh.main.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hh.core.UiState
import com.example.hh.databinding.ItemErrorBinding
import com.example.hh.databinding.ItemVacancyBinding
import com.example.hh.filters.presentation.ChooseButton
import com.example.hh.main.data.BundleWrapper

class VacanciesAdapter(
    private val typeList: List<VacancyUiType> = listOf(
        VacancyUiType.Error,
        VacancyUiType.Progress,
        VacancyUiType.Vacancy,
        VacancyUiType.EmptyFavoriteCache
    ),
    private val clickActions: ClickActions,
    private val liveDataWrapper: VacanciesLiveDataWrapper,
) : RecyclerView.Adapter<VacancyViewHolder>(), UpdateItems<VacancyUi>, SaveItems<VacanciesUiState> {

    private val list = mutableListOf<VacancyUi>()

    override fun updateItems(newList: List<VacancyUi>) {
        val callback = Diff(
            list,
            newList
        )
        val result = DiffUtil.calculateDiff(callback)
        list.clear()
        list.addAll(newList)
        result.dispatchUpdatesTo(this)
    }

     override fun save(bundle: BundleWrapper.Save<VacanciesUiState>) {
        liveDataWrapper.save(bundle)
    }

    override fun restore(bundleWrapper: BundleWrapper.Restore<VacanciesUiState>) {
        val uiState = bundleWrapper.restore()
        liveDataWrapper.update(uiState)
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        val type = item.type()
        val index = typeList.indexOf(type)
        if (index == -1)
            throw IllegalStateException("add type $type to typeList $typeList")
        return index //0, 1, 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return typeList[viewType].viewHolder(parent, clickActions)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}

interface SaveItems<T: UiState> {
    fun save(bundle: BundleWrapper.Save<T>)
    fun restore(bundleWrapper: BundleWrapper.Restore<T>)
}

interface UpdateItems<T: ItemsUi> {
    fun updateItems(newList: List<T>)
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

    class EmptyFavoriteCache(
        view: View
    ) : VacancyViewHolder(view)

    class Vacancy(
        private val binding: ItemVacancyBinding,
        private val clickActions: ClickActions
    ) : VacancyViewHolder(binding.root) {

        override fun bind(vacancy: VacancyUi) {

            binding.favoriteButton.setOnClickListener {
                vacancy.changeFavoriteIcon(binding)
                clickActions.clickFavorite(vacancy)
            }
            binding.respondButton.setOnClickListener {
                clickActions.clickRespond(vacancy)
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

interface ClickActions : ChooseButton {

    fun clickFavorite(vacancyUi: VacancyUi) = Unit
    fun retry() = Unit
    fun clickRespond(vacancyUi: VacancyUi) = Unit
}

