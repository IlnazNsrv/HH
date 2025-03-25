package com.example.hh.main.presentation

import android.view.View
import com.example.hh.R
import com.example.hh.databinding.ItemErrorBinding
import com.example.hh.databinding.ItemVacancyBinding
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.data.cloud.VacancyCloud
import java.io.Serializable

interface VacancyUi : ItemsUi {

    fun type(): VacancyUiType

    fun show(binding: ItemVacancyBinding) = Unit
    fun changeFavoriteIcon(binding: ItemVacancyBinding) = Unit
    fun showError(binding: ItemErrorBinding) = Unit
    fun id(): String
    fun changeFavoriteChosen(): VacancyUi
    fun favoriteChosen(): Boolean


    class Base(
        private val vacancyCloud: VacancyCloud,
        private var favoriteChosen: Boolean
    ) : VacancyUi {

        override fun type() = VacancyUiType.Vacancy

        override fun show(binding: ItemVacancyBinding) {
            binding.vacancyTitle.text = vacancyCloud.name
            binding.salary.apply {
                if (vacancyCloud.salary == null)
                    visibility = View.GONE
                else {
                    visibility = View.VISIBLE
                    text = setSalary(vacancyCloud.salary)
                }
            }
            binding.city.text = vacancyCloud.area.name
            binding.companyName.text = vacancyCloud.employer.name
            binding.experience.text = setExperience(vacancyCloud.experience)
            binding.favoriteButton.apply {
                if (favoriteChosen) {
                    startAnimation(
                        android.view.animation.AnimationUtils.loadAnimation(
                            this.context,
                            R.anim.fill_animation
                        )
                    )
                    setBackgroundResource(R.drawable.ic_favorite_clicked)
                }
                else {
                    startAnimation(
                        android.view.animation.AnimationUtils.loadAnimation(
                            this.context,
                            R.anim.fill_animation
                        )
                    )
                    setBackgroundResource(R.drawable.ic_favorite)
                }

            }


            binding.respondButton.apply {
                if (vacancyCloud.type.id == "closed") {
                    isEnabled = false
                    text = vacancyCloud.type.name
                }
            }
        }

        override fun id(): String {
            return vacancyCloud.id
        }

        override fun changeFavoriteIcon(binding: ItemVacancyBinding) {
            binding.favoriteButton.apply {
                if (!favoriteChosen) {
                    startAnimation(
                        android.view.animation.AnimationUtils.loadAnimation(
                            this.context,
                            R.anim.fill_animation
                        )
                    )
                    setBackgroundResource(R.drawable.ic_favorite_clicked)
                }
                else {
                    startAnimation(
                        android.view.animation.AnimationUtils.loadAnimation(
                            this.context,
                            R.anim.fill_animation
                        )
                    )
                    setBackgroundResource(R.drawable.ic_favorite)
                }
            }
        }
        override fun changeFavoriteChosen() : VacancyUi  {
            favoriteChosen = !favoriteChosen
            return this
        }

        override fun favoriteChosen(): Boolean {
            return favoriteChosen
        }

        private fun setSalary(salary: Salary?): String {
            return when {
                (salary!!.from != null && salary.to != null) -> {
                    "${salary.from} - ${salary.to}"
                }

                (salary.from != null) -> "от ${salary.from}"
                (salary.to != null) -> "до ${salary.to}"
                else -> salary.currency!!
            }
        }

        private fun setExperience(experience: Experience?): String {
            return if (experience == null) {
                "Без опыта"
            } else experience.name
        }
    }

    object Progress : VacancyUi {
        override fun type(): VacancyUiType = VacancyUiType.Progress

        override fun id(): String = javaClass.simpleName

        override fun changeFavoriteChosen(): VacancyUi = Progress
        override fun favoriteChosen(): Boolean = false
    }

    data class Error(private val message: String) : VacancyUi {
        override fun type(): VacancyUiType = VacancyUiType.Error

        override fun showError(binding: ItemErrorBinding) {
            binding.errorText.text = message
        }

        override fun id(): String = javaClass.simpleName + message

        override fun changeFavoriteChosen(): VacancyUi = Error(message)
        override fun favoriteChosen(): Boolean = false

    }
}

interface ItemsUi : Serializable