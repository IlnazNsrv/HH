package com.example.hh.main.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hh.databinding.ItemErrorBinding
import com.example.hh.databinding.ItemVacancyBinding
import com.example.hh.main.presentation.VacancyUi

abstract class VacancyViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

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

            binding.root.setOnClickListener {
                clickActions.clickVacancy(vacancy)
            }

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