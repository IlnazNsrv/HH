package com.example.hh.core

import android.content.Context
import com.example.hh.favorite.data.cache.FavoriteVacanciesCacheModule
import com.example.hh.filters.areafilters.data.cache.AreasCacheModule
import com.example.hh.loadvacancies.data.cache.VacanciesCacheModule
import com.example.hh.main.data.HandleError

class Core(val clearViewModel: ClearViewModel, private val context: Context) {

    val provideResource: ProvideResource = ProvideResource.Base(context)

    val handleDataError: HandleError<Exception> = HandleError.Data()

    val handleDomainError: HandleError<String> = HandleError.Domain(provideResource)

    val runAsync = RunAsync.Base()

    val provideRetrofitBuilder = ProvideRetrofitBuilder.Base()

    val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    val areasCacheModule = AreasCacheModule(context)

    val vacanciesCacheModule = VacanciesCacheModule(context)

    val favoriteVacanciesCacheModule = FavoriteVacanciesCacheModule(context)

    val lastTimeButtonClicked = LastTimeButtonClicked.Base(500)

    companion object {
        private const val SHARED_PREF_NAME = "hhAppData"
    }
}