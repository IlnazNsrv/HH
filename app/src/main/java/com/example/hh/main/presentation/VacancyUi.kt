package com.example.hh.main.presentation

import android.view.View
import coil3.load
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
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


    data class Base(
        private val vacancyCloud: VacancyCloud,
        private var favoriteChosen: Boolean
    ) : VacancyUi {

        override fun type() = VacancyUiType.Vacancy

        override fun show(binding: ItemVacancyBinding) {
            binding.vacancyTitle.text = vacancyCloud.name
            setSalary(binding)
            binding.companyName.text = vacancyCloud.employer.name
            binding.experience.text = setExperience(vacancyCloud.experience)
            setFavorite(binding)
            binding.city.text = vacancyCloud.area.name
            setImageView(binding)
            binding.respondButton.apply {
                if (vacancyCloud.type.id == "closed") {
                    isEnabled = false
                    text = vacancyCloud.type.name
                }
            }
        }

        private fun setImageView(binding: ItemVacancyBinding) {
            if (vacancyCloud.employer.logoUrls != null) {
                binding.companyImageView.visibility = View.VISIBLE
                binding.companyImageView.load(vacancyCloud.employer.logoUrls.ninety) {
                    transformations(CircleCropTransformation())
                }
            }
        }

        override fun id(): String {
            return vacancyCloud.id
        }

        private fun setFavorite(binding: ItemVacancyBinding) {
            binding.favoriteButton.apply {
                if (favoriteChosen) {
                    startAnimation(
                        android.view.animation.AnimationUtils.loadAnimation(
                            this.context,
                            R.anim.fill_animation
                        )
                    )
                    setBackgroundResource(R.drawable.ic_favorite_clicked)
                } else {
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

        private fun setSalary(binding: ItemVacancyBinding) {
            binding.salary.apply {
                if (vacancyCloud.salary == null)
                    visibility = View.GONE
                else {
                    visibility = View.VISIBLE
                    text = setSalary(vacancyCloud.salary)
                }
            }
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
                } else {
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

        override fun changeFavoriteChosen(): VacancyUi {
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
                else -> salary.currency
            }
        }

        private fun setExperience(experience: Experience?): String {
            return experience?.name ?: "Без опыта"
        }
    }

    object Progress : VacancyUi {

        private fun readResolve(): Any = Progress
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

    object EmptyFavoriteCache : VacancyUi {

        private fun readResolve(): Any = EmptyFavoriteCache
        override fun type() = VacancyUiType.EmptyFavoriteCache
        override fun id(): String = javaClass.simpleName
        override fun changeFavoriteChosen(): VacancyUi = EmptyFavoriteCache
        override fun favoriteChosen(): Boolean = false

    }
}

interface ItemsUi : Serializable