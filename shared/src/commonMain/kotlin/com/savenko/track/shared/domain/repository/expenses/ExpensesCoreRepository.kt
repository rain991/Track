package com.savenko.track.shared.domain.repository.expenses

import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

interface ExpensesCoreRepository {
    fun getSumOfExpensesInTimeSpan(start: Long, end: Long): Flow<Float>
    fun getSumOfExpensesByCategoriesInTimeSpan(
        start: Instant,
        end: Instant,
        categoriesIds: List<Int>
    ): Flow<Float>

    fun getCurrentMonthSumOfExpense(): Flow<Float>
    fun getCurrentMonthSumOfExpensesByCategoriesId(listOfCategoriesId: List<Int>): Flow<Float>
    fun getCountOfExpensesInSpan(startDate: Instant, endDate: Instant): Flow<Int>
    fun getCountOfExpensesInSpanByCategoriesIds(
        startDate: Instant,
        endDate: Instant,
        categoriesIds: List<Int>
    ): Flow<Int>

    fun getAverageInTimeSpan(startDate: Instant, endDate: Instant): Flow<Float>
}