package com.example.hh.main.presentation

import android.view.View
import com.example.hh.databinding.ItemErrorBinding
import com.example.hh.databinding.ItemVacancyBinding
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.MainVacancyCloud
import com.example.hh.main.data.cloud.Salary
import com.squareup.picasso.Picasso

interface VacancyUi {

    fun type(): VacancyUiType

    fun show(binding: ItemVacancyBinding) = Unit
    fun showError(binding: ItemErrorBinding) = Unit
    fun id(): String
    fun changeFavoriteChosen(): VacancyUi
    fun favoriteChosen(): Boolean
    fun favoriteOrNot(clickActions: ClickActions) = Unit


    class Base(
        private val vacancyCloud: MainVacancyCloud,
        private val favoriteChosen: Boolean
    ) : VacancyUi {

//        override fun favoriteOrNot(clickActions: ClickActions) {
//            if (favoriteChosen)
//                clickActions.stop()
//            else
//                clickActions.favorite(this)
//        }

        override fun type() = VacancyUiType.Vacancy

        override fun show(binding: ItemVacancyBinding) {
            binding.vacancyTitle.text = vacancyCloud.name
            binding.salary.apply {
                if (vacancyCloud.salary == null)
                    visibility = View.GONE
                else {
                    visibility = View.VISIBLE
                    setSalary(vacancyCloud.salary)
                }
            }
            binding.city.text = vacancyCloud.area.name
            binding.companyName.text = vacancyCloud.employer.name
            binding.experience.text = setExperience(vacancyCloud.experience)
            //binding.favoriteButton.setBackgroundResource(if (favoriteChosen) R.drawable.ic_favorite_clicked else R.drawable.favorite_state)
//            binding.favoriteButton.apply {
//                if (favoriteChosen) {
//                    startAnimation(
//                        android.view.animation.AnimationUtils.loadAnimation(
//                            this.context,
//                            R.anim.fill_animation
//                        )
//                    )
//                    setBackgroundResource(R.drawable.ic_favorite_clicked)
//                }
//                else {
//                    startAnimation(
//                        android.view.animation.AnimationUtils.loadAnimation(
//                            this.context,
//                            R.anim.fill_animation
//                        )
//                    )
//                    setBackgroundResource(R.drawable.favorite_state)
//                }
//
//            }

            binding.respondButton.apply {
                if (vacancyCloud.type.id == "closed") {
                    isEnabled = false
                    text = vacancyCloud.type.name
                }
            }
            vacancyCloud.employer.logoUrls?.ninety?.let {
                loadImage(it, binding)
            }
        }

        override fun id(): String {
            return vacancyCloud.id
        }

        override fun changeFavoriteChosen(): VacancyUi {
            return VacancyUi.Base(vacancyCloud, !favoriteChosen)
        }

        override fun favoriteChosen(): Boolean {
            return favoriteChosen
        }

        private fun loadImage(imageUrl: String, binding: ItemVacancyBinding) {
            Picasso.get().load(imageUrl).into(binding.companyImageView)
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


    data class Base2(
        private val id: String,
        private val title: String,
        private val salary: Salary?,
        private val city: String,
        private val companyName: String,
        private val experience: Experience?,
        private val favoriteChosen: Boolean
    ) : VacancyUi {

        override fun show(binding: ItemVacancyBinding) {
            binding.vacancyTitle.text = title
            binding.salary.apply {
                if (salary == null)
                    visibility = View.GONE
                else {
                    visibility = View.VISIBLE
                    text = salary(salary)
                }
            }
            binding.city.text = city
            binding.companyName.text = companyName
            binding.experience.text = setExperience(experience)
        }

        override fun type(): VacancyUiType {
            TODO("Not yet implemented")
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

        private fun salary(salary: Salary?): String {
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


}