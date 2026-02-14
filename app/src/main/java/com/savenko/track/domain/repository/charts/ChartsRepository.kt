package com.savenko.track.domain.repository.charts

import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import kotlin.time.Instant

interface ChartsRepository {
    suspend fun requestCurrentMonthExpensesDateDesc(context: CoroutineContext = Dispatchers.IO): List<Pair<Float, Instant>>
    suspend fun requestCurrentMonthIncomesDateDesc(context: CoroutineContext = Dispatchers.IO): List<Pair<Float, Instant>>
    suspend fun requestCurrentYearExpensesDateDesc(context: CoroutineContext = Dispatchers.IO): List<Pair<Float, Instant>>
    suspend fun requestCurrentYearIncomesDateDesc(context: CoroutineContext = Dispatchers.IO): List<Pair<Float, Instant>>
    suspend fun requestCurrentMonthExpenseCategoriesDistribution(context: CoroutineContext = Dispatchers.IO): Map<ExpenseCategory, Int>
    suspend fun requestCurrentMonthIncomeCategoriesDistribution(context: CoroutineContext = Dispatchers.IO): Map<IncomeCategory, Int>
}