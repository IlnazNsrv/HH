package com.example.hh.main.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hh.databinding.ItemErrorBinding
import com.example.hh.databinding.ItemProgressBinding
import com.example.hh.databinding.ItemVacancyBinding

interface VacancyUiType {

    fun viewHolder(parent: ViewGroup, clickActions: ClickActions): VacancyViewHolder

    object Progress : VacancyUiType {

        override fun viewHolder(
            parent: ViewGroup,
            clickActions: ClickActions
        ): VacancyViewHolder {
            return VacancyViewHolder.Progress(
                ItemProgressBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                ).root
            )
        }
    }

    object Error : VacancyUiType {

        override fun viewHolder(
            parent: ViewGroup,
            clickActions: ClickActions
        ): VacancyViewHolder {
            return VacancyViewHolder.Error(
                ItemErrorBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                ), clickActions
            )
        }
    }

    object Vacancy : VacancyUiType {

        override fun viewHolder(
            parent: ViewGroup,
            clickActions: ClickActions
        ): VacancyViewHolder {
            return VacancyViewHolder.Vacancy(
                ItemVacancyBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                ), clickActions
            )
        }
    }
}