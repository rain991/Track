package com.savenko.track.domain.repository.expenses

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ExpensesCoreRepository {
    suspend fun getSumOfExpenses(start : Long, end : Long) : Flow<Float>
    suspend fun getSumOfExpensesByCategories(start : Long, end : Long,listOfCategories: List<Int>) : Flow<Float>
    suspend fun getCurrentMonthSumOfExpense() : Flow<Float>
    suspend fun getCurrentMonthSumOfExpensesByCategoriesId(listOfCategoriesId : List<Int>) : Flow<Float>
    suspend fun getCountOfExpensesInSpan(startDate: Date, endDate: Date): Flow<Int>
    suspend fun getCountOfExpensesInSpanByCategoriesIds(startDate: Date, endDate: Date,categoriesIds : List<Int>): Flow<Int>
}