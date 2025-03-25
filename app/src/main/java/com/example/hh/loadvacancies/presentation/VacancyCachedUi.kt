package com.example.hh.loadvacancies.presentation

import android.view.View
import com.example.hh.R
import com.example.hh.databinding.ItemVacancyBinding
import com.example.hh.loadvacancies.data.cache.ExperienceEntity
import com.example.hh.loadvacancies.data.cache.SalaryEntity
import com.example.hh.loadvacancies.data.cache.VacanciesDao
import com.example.hh.main.presentation.VacancyUi
import com.example.hh.main.presentation.VacancyUiType


class VacancyCachedUi(
    private val vacancyCloud: VacanciesDao.VacancyWithDetails,
    private var favoriteChosen: Boolean
) : VacancyUi {

    override fun type() = VacancyUiType.Vacancy

    override fun show(binding: ItemVacancyBinding) {
        binding.testTextForCheck.apply {
            if (vacancyCloud.workingHours != null && vacancyCloud.workingHours.isEmpty())
                visibility = View.GONE
            else {
                visibility = View.VISIBLE
                text = vacancyCloud.workingHours?.first()?.name
            }
        }

        binding.vacancyTitle.text = vacancyCloud.vacancy.name
        checkSalary(binding)
        setFavorite(binding)
        binding.city.text = vacancyCloud.vacancy.area.name
        binding.companyName.text = vacancyCloud.vacancy.employer?.name
        binding.experience.text = setExperience(vacancyCloud.vacancy.experience)

        binding.respondButton.apply {
            if (vacancyCloud.vacancy.id == "closed") {
                isEnabled = false
                text = vacancyCloud.vacancy.name
            }
        }
    }

    override fun id(): String {
        return vacancyCloud.vacancy.id
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
    override fun changeFavoriteChosen() : VacancyUi {
        favoriteChosen = !favoriteChosen
        return this
    }

    override fun favoriteChosen(): Boolean {
        return favoriteChosen
    }

//        private fun loadImage(imageUrl: String, binding: ItemVacancyBinding) {
//            Picasso.get().load(imageUrl).into(binding.companyImageView)
//        }

    private fun checkSalary(binding: ItemVacancyBinding) {
        binding.salary.apply {
            if (vacancyCloud.vacancy.salary == null)
                visibility = View.GONE
            else {
                visibility = View.VISIBLE
                text = setSalary(vacancyCloud.vacancy.salary)
            }
        }
    }

    private fun setSalary(salary: SalaryEntity?): String {
        return when {
            (salary!!.from != null && salary.to != null) -> {
                "${salary.from} - ${salary.to}"
            }

            (salary.from != null) -> "от ${salary.from}"
            (salary.to != null) -> "до ${salary.to}"
            else -> salary.currency!!
        }
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

    private fun setExperience(experience: ExperienceEntity?): String {
        return if (experience == null) {
            "Без опыта"
        } else experience.name
    }
}