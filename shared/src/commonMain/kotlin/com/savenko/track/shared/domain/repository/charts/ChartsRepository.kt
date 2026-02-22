package com.savenko.track.shared.domain.repository.charts

import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlin.coroutines.CoroutineContext
import kotlin.time.Instant

interface ChartsRepository {
    suspend fun requestCurrentMonthExpensesDateDesc(context: CoroutineContext = Dispatchers.Default): List<Pair<Float, Instant>>
    suspend fun requestCurrentMonthIncomesDateDesc(context: CoroutineContext = Dispatchers.Default): List<Pair<Float, Instant>>
    suspend fun requestCurrentYearExpensesDateDesc(context: CoroutineContext = Dispatchers.Default): List<Pair<Float, Instant>>
    suspend fun requestCurrentYearIncomesDateDesc(context: CoroutineContext = Dispatchers.Default): List<Pair<Float, Instant>>
    suspend fun requestCurrentMonthExpenseCategoriesDistribution(context: CoroutineContext = Dispatchers.Default): Map<ExpenseCategory, Int>
    suspend fun requestCurrentMonthIncomeCategoriesDistribution(context: CoroutineContext = Dispatchers.Default): Map<IncomeCategory, Int>
}