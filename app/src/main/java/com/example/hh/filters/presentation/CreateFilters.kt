package com.example.hh.filters.presentation

import com.example.hh.R

interface CreateFilters {

    companion object {
        const val EXPERIENCE_TAG = "experience"
        const val SCHEDULE_TAG = "schedule"
        const val SEARCH_FIELD_TAG = "searchField"
        const val EMPLOYMENT_TAG = "employment"
    }

    fun initExperience(): List<FilterButtonUi.Base>
    fun initEmployment(): List<FilterButtonUi.Base>
    fun initSchedule(): List<FilterButtonUi.Base>
    fun initSearchField(): List<FilterButtonUi.Base>

    class Base : CreateFilters {
        override fun initExperience(): List<FilterButtonUi.Base> {
            return listOf(
                FilterButtonUi.Base(
                    R.string.noExperience,
                    "noExperience",
                    EXPERIENCE_TAG
                ),
                FilterButtonUi.Base(
                    R.string.between1And3,
                    "between1And3",
                    EXPERIENCE_TAG
                ),
                FilterButtonUi.Base(
                    R.string.between3And6,
                    "between3And6",
                    EXPERIENCE_TAG
                ),
                FilterButtonUi.Base(
                    R.string.moreThan6,
                    "moreThan6",
                    EXPERIENCE_TAG
                )
            )
        }

        override fun initEmployment(): List<FilterButtonUi.Base> {
            return listOf(
                FilterButtonUi.Base(
                    R.string.full,
                    "full",
                    EMPLOYMENT_TAG
                ),
                FilterButtonUi.Base(
                    R.string.part,
                    "part",
                    EMPLOYMENT_TAG
                ),
                FilterButtonUi.Base(
                    R.string.project,
                    "project",
                    EMPLOYMENT_TAG
                ),
                FilterButtonUi.Base(
                    R.string.volunteer,
                    "volunteer",
                    EMPLOYMENT_TAG
                ),
                FilterButtonUi.Base(
                    R.string.probation,
                    "probation",
                    EMPLOYMENT_TAG
                )
            )
        }

        override fun initSchedule(): List<FilterButtonUi.Base> {
            return listOf(
                FilterButtonUi.Base(
                    R.string.fullDay,
                    "fullDay",
                    SCHEDULE_TAG
                ),
                FilterButtonUi.Base(
                    R.string.shift,
                    "shift",
                    SCHEDULE_TAG
                ),
                FilterButtonUi.Base(
                    R.string.flexible,
                    "flexible",
                    SCHEDULE_TAG
                ),
                FilterButtonUi.Base(
                    R.string.remote,
                    "remote",
                    SCHEDULE_TAG
                ),
                FilterButtonUi.Base(
                    R.string.flyInFlyOut,
                    "flyInFlyOut",
                    SCHEDULE_TAG
                )
            )
        }

        override fun initSearchField(): List<FilterButtonUi.Base> {
            return listOf(
                FilterButtonUi.Base(
                    R.string.name,
                    "name",
                    SEARCH_FIELD_TAG
                ),
                FilterButtonUi.Base(
                    R.string.company_name,
                    "company_name",
                    SEARCH_FIELD_TAG
                ),
                FilterButtonUi.Base(
                    R.string.description,
                    "description",
                    SEARCH_FIELD_TAG
                )
            )
        }
    }
}
