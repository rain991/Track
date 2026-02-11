package com.savenko.track.domain.repository.expenses

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ExpensesCoreRepository {
    fun getSumOfExpensesInTimeSpan(start : Long, end : Long) : Flow<Float>
    fun getSumOfExpensesByCategoriesInTimeSpan(start : Long, end : Long, categoriesIds: List<Int>) : Flow<Float>
    fun getCurrentMonthSumOfExpense() : Flow<Float>
    fun getCurrentMonthSumOfExpensesByCategoriesId(listOfCategoriesId : List<Int>) : Flow<Float>
    fun getCountOfExpensesInSpan(startDate: Date, endDate: Date): Flow<Int>
    fun getCountOfExpensesInSpanByCategoriesIds(startDate: Date, endDate: Date,categoriesIds : List<Int>): Flow<Int>
    fun getAverageInTimeSpan(startDate: Date, endDate: Date) : Flow<Float>
}