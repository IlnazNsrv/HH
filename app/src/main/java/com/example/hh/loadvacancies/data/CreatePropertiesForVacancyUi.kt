package com.example.hh.loadvacancies.data


import com.example.hh.loadvacancies.data.cache.AddressEntity
import com.example.hh.loadvacancies.data.cache.EmployerEntity
import com.example.hh.loadvacancies.data.cache.ExperienceEntity
import com.example.hh.loadvacancies.data.cache.SalaryEntity
import com.example.hh.loadvacancies.data.cache.WorkFormatEntity
import com.example.hh.loadvacancies.data.cache.WorkScheduleByDaysEntity
import com.example.hh.loadvacancies.data.cache.WorkingHoursEntity
import com.example.hh.main.data.cloud.Address
import com.example.hh.main.data.cloud.Employer
import com.example.hh.main.data.cloud.Experience
import com.example.hh.main.data.cloud.LogoUrls
import com.example.hh.main.data.cloud.Salary
import com.example.hh.main.data.cloud.WorkFormat
import com.example.hh.main.data.cloud.WorkScheduleByDays
import com.example.hh.main.data.cloud.WorkingHours

interface CreatePropertiesForVacancyUi {

    fun createSalary(salary: SalaryEntity?): Salary?
    fun createAddress(address: AddressEntity?): Address?
    fun createExperience(experience: ExperienceEntity?): Experience?
    fun createEmployer(employer: EmployerEntity): Employer
    fun createWorkFormatList(workFormat: List<WorkFormatEntity>): List<WorkFormat>
    fun createWorkingHours(workingHours: List<WorkingHoursEntity>): List<WorkingHours>
    fun createWorkScheduleByDays(workScheduleByDays: List<WorkScheduleByDaysEntity>): List<WorkScheduleByDays>

    class Base() : CreatePropertiesForVacancyUi {

        override fun createSalary(salary: SalaryEntity?): Salary? {
            return salary?.let {
                Salary(it.from, it.to, it.currency, it.gross)
            }
        }

        override fun createAddress(address: AddressEntity?): Address? {
            return address?.let {
                Address(it.city, it.street)
            }
        }

        override fun createExperience(experience: ExperienceEntity?): Experience? {
            return experience?.let {
                Experience(it.id, it.name)
            }
        }

        override fun createEmployer(employer: EmployerEntity): Employer {
            val logoUrls = employer.logoUrls?.let {
                LogoUrls(it.ninety, it.twoHundredForty, it.original)
            }
            return Employer(employer.id, employer.name, logoUrls)
        }

        override fun createWorkFormatList(workFormat: List<WorkFormatEntity>): List<WorkFormat> {
            val list = workFormat.map { WorkFormat(it.id, it.name) }
            return list
        }

        override fun createWorkingHours(workingHours: List<WorkingHoursEntity>): List<WorkingHours> {
            val list = workingHours.map { WorkingHours(it.id, it.name) }
            return list
        }

        override fun createWorkScheduleByDays(workScheduleByDays: List<WorkScheduleByDaysEntity>): List<WorkScheduleByDays> {
            val list = workScheduleByDays.map { WorkScheduleByDays(it.id, it.name) }
            return list
        }
    }
}