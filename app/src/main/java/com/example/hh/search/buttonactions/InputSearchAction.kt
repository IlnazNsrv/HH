package com.example.hh.search.buttonactions

import com.example.hh.core.RunAsync
import com.example.hh.loadvacancies.data.VacanciesRepository
import com.example.hh.main.presentation.LoadVacancies
import com.example.hh.main.presentation.VacanciesResultMapper
import com.example.hh.views.input.HandleInputAction
import kotlinx.coroutines.CoroutineScope

class InputSearchAction(
    private val runAsync: RunAsync,
    private val repository: VacanciesRepository,
    private val resultMapper: VacanciesResultMapper
) : HandleInputAction, LoadVacancies {

   override fun handle(text: String, viewModelScope: CoroutineScope) {
//        resultMapper.mapProgress()
//        runAsync.runAsync(viewModelScope, {
//            repository.vacancies(text)
//        }) {
//            it.map(resultMapper)
//        }
   }

    override fun loadVacancies() {
    //   handle()
//        Опытные ребята, подскажите пж. Приемлемо ли использование одной общей вью модель для нескольких фрагментов(включая диалоговые)? Вот допустим у меня есть :
//
//        Диалоговое окно 1: Только поле ввода.
//
//        Диалоговое окно 2: Поле ввода + различные фильтры поиска.
//
//        Основной фрагмент: Результаты поиска.
//        Просто тяжело передавать данные между различными VM и если я использую 1 общую, то считается ли это допустимой практикой ?)
    }
}