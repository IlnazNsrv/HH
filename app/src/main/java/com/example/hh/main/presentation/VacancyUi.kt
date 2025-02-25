package com.example.hh.main.presentation

import android.view.View
import com.example.hh.R
import com.example.hh.databinding.ItemVacancyBinding
import com.example.hh.main.data.Experience
import com.example.hh.main.data.MainVacancyCloud
import com.example.hh.main.data.Salary

interface VacancyUi {

    fun show(binding: ItemVacancyBinding)
    fun id() : String
    fun changeFavoriteChosen() : VacancyUi
    abstract fun favoriteChosen() : Boolean


    class Base(
        private val vacancyCloud: MainVacancyCloud,
        private val favoriteChosen: Boolean
    ) : VacancyUi {

        override fun show(binding: ItemVacancyBinding) {
            binding.vacancyTitle.setText(vacancyCloud.name)
            binding.salary.apply {
                if (vacancyCloud.salary == null)
                    visibility = View.GONE
                else {
                    visibility = View.VISIBLE
                    setSalary(vacancyCloud.salary)
                }
            }
            binding.city.setText(vacancyCloud.area.name)
            binding.companyName.setText(vacancyCloud.employer.name)
            binding.experience.setText(setExperience(vacancyCloud.experience))
            binding.favoriteButton.apply {
                if (favoriteChosen)
                    setBackgroundColor(R.color.red)
            }
            binding.respondButton.apply {
                if (vacancyCloud.type.id == "closed"){
                    isEnabled = false
                    text = vacancyCloud.type.name
                }
            }
        }

        override fun id(): String {
            return vacancyCloud.id
        }

        override fun changeFavoriteChosen(): VacancyUi {
            return VacancyUi.Base(vacancyCloud, favoriteChosen)
        }

        override fun favoriteChosen(): Boolean {
           return favoriteChosen
        }

        private fun setSalary(salary: Salary?) : String {
            return when {
                (salary!!.from != null && salary.to != null) -> {
                    "${salary.from} - ${salary.to}"
                }
                (salary.from != null) -> "от ${salary.from}"
                (salary.to != null) -> "до ${salary.to}"
                else -> salary.currency!!
            }
        }

        private fun setExperience(experience: Experience?) : String {
            return if (experience == null) {
                "Без опыта"
            } else experience.name
        }

    }




    data class Base2(
        private val id: String,
        private val title: String,
        private val salary: Salary?,
        private val city: String,
        private val companyName: String,
        private val experience: Experience?,
        private val favoriteChosen: Boolean
    ): VacancyUi {

        override fun show(binding: ItemVacancyBinding) {
            binding.vacancyTitle.setText(title)
            binding.salary.apply {
                if (salary == null)
                    visibility = View.GONE
                else {
                    visibility = View.VISIBLE
                    text = salary(salary)
                }
            }
            binding.city.setText(city)
            binding.companyName.setText(companyName)
            binding.experience.setText(setExperience(experience))
        }

        override fun id(): String {
            TODO("Not yet implemented")
        }

        override fun changeFavoriteChosen(): VacancyUi {
            TODO("Not yet implemented")
        }

        override fun favoriteChosen(): Boolean {
            TODO("Not yet implemented")
        }

        private fun salary(salary: Salary?) : String {
            return when {
                (salary!!.from != null && salary.to != null) -> {
                    "${salary.from} - ${salary.to}"
                }
                (salary.from != null) -> "от ${salary.from}"
                (salary.to != null) -> "до ${salary.to}"
                else -> salary.currency!!
            }
        }

        private fun setExperience(experience: Experience?) : String {
            return if (experience == null) {
                "Без опыта"
            } else experience.name
        }

    }


}