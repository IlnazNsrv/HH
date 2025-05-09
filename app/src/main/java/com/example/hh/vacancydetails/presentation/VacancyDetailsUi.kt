package com.example.hh.vacancydetails.presentation

import android.text.Html
import android.view.View
import coil3.load
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.example.hh.R
import com.example.hh.databinding.FragmentVacancyDetailsBinding
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.Salary
import com.example.hh.vacancydetails.data.cloud.VacancyDetailsCloud

interface VacancyDetailsUi {

    fun id(): String
    fun show(binding: FragmentVacancyDetailsBinding) = Unit
    fun changeFavoriteChosen(): VacancyDetailsUi
    fun favoriteChosen(): Boolean

    class Base(
        private val vacancyDetailsCloud: VacancyDetailsCloud,
        private var favoriteChosen: Boolean
    ) : VacancyDetailsUi {

        override fun show(binding: FragmentVacancyDetailsBinding) {
            binding.errorLayout.visibility = View.GONE
            setFavorite(binding)
            setText(binding)
            binding.vacancyName.text = vacancyDetailsCloud.name
            setSalary(binding)
            binding.experience.text = setExperience(vacancyDetailsCloud.experience)
            binding.areaTextView.visibility = View.VISIBLE
            setEmployment(binding)
            setEmployer(binding)
            setContacts(binding)
            binding.areaInformation.text = vacancyDetailsCloud.area.name
            binding.progressBarVacancyDetails.visibility = View.GONE
            setImageView(binding)
        }

        override fun id(): String {
            return vacancyDetailsCloud.id
        }

        override fun changeFavoriteChosen(): VacancyDetailsUi {
            favoriteChosen = !favoriteChosen
            return this
        }

        override fun favoriteChosen(): Boolean {
            return favoriteChosen
        }

        private fun setFavorite(binding: FragmentVacancyDetailsBinding) {
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

        private fun setText(binding: FragmentVacancyDetailsBinding) {
            val spannedText =
                Html.fromHtml(vacancyDetailsCloud.description, Html.FROM_HTML_MODE_LEGACY)
            binding.vacancyDescription.text = spannedText
        }

        private fun setSalary(binding: FragmentVacancyDetailsBinding) {
            binding.salary.apply {
                if (vacancyDetailsCloud.salary == null)
                    visibility = View.GONE
                else {
                    visibility = View.VISIBLE
                    text = setSalary(vacancyDetailsCloud.salary)
                }
            }
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
            return "Требуемый опыт: ${experience?.name ?: "Без опыта"}"
        }

        private fun setEmployment(binding: FragmentVacancyDetailsBinding) {
            if (vacancyDetailsCloud.employment != null) {
                binding.employment.text = vacancyDetailsCloud.employment.name
            } else {
                binding.employment.visibility = View.GONE
            }
        }

        private fun setEmployer(binding: FragmentVacancyDetailsBinding) {
            if (vacancyDetailsCloud.employer != null) {
                binding.companyLayout.visibility = View.VISIBLE
                binding.companyName.text = vacancyDetailsCloud.employer.name
            } else {
                binding.companyLayout.visibility = View.GONE
            }
        }

        private fun setContacts(binding: FragmentVacancyDetailsBinding) {
            if (vacancyDetailsCloud.contacts != null) {
                binding.contactsLayout.visibility = View.VISIBLE
                vacancyDetailsCloud.contacts.name?.let { binding.contactName.text = it }
                vacancyDetailsCloud.contacts.email?.let { binding.contactEmail.text = it }
                setPhoneNumbers(binding)
            }
        }

        private fun setImageView(binding: FragmentVacancyDetailsBinding) {
            if (vacancyDetailsCloud.employer?.logoUrls != null) {
                binding.companyImage.visibility = View.VISIBLE
                binding.companyImage.load(vacancyDetailsCloud.employer.logoUrls.original) {
                    size(200, 200)
                    transformations(CircleCropTransformation())
                }
            }
        }

        private fun setPhoneNumbers(binding: FragmentVacancyDetailsBinding) {
            val vacancyPhone = vacancyDetailsCloud.contacts?.phones?.first()
            binding.contactNumber.text = vacancyPhone?.number
            if (vacancyPhone?.comment != null) {
                binding.contactDescription.visibility = View.VISIBLE
                binding.contactDescription.text = vacancyPhone.comment
            }
        }
    }

    object Progress : VacancyDetailsUi {
        override fun id(): String {
            return this::class.java.simpleName
        }

        override fun show(binding: FragmentVacancyDetailsBinding) {
            binding.progressBarVacancyDetails.visibility = View.VISIBLE
        }

        override fun changeFavoriteChosen(): VacancyDetailsUi {
            return this
        }

        override fun favoriteChosen(): Boolean {
            return false
        }
    }

    class Error(
        private val message: String
    ) : VacancyDetailsUi {

        override fun show(binding: FragmentVacancyDetailsBinding) {
            binding.progressBarVacancyDetails.visibility = View.GONE
            binding.errorLayout.visibility = View.VISIBLE
            binding.errorText.text = message
        }

        override fun id(): String {
            return this::class.java.simpleName
        }

        fun message() = message

        override fun changeFavoriteChosen(): VacancyDetailsUi {
            return this
        }

        override fun favoriteChosen(): Boolean {
            return false
        }
    }
}