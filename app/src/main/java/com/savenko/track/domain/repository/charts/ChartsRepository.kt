package com.savenko.track.domain.repository.charts

import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.Dispatchers
import java.util.Date
import kotlin.coroutines.CoroutineContext

interface ChartsRepository {
    suspend fun requestCurrentMonthExpensesDateDesc(context: CoroutineContext = Dispatchers.IO): List<Pair<Float, Date>>
    suspend fun requestCurrentMonthIncomesDateDesc(context: CoroutineContext = Dispatchers.IO): List<Pair<Float, Date>>
    suspend fun requestCurrentYearExpensesDateDesc(context: CoroutineContext = Dispatchers.IO): List<Pair<Float, Date>>
    suspend fun requestCurrentYearIncomesDateDesc(context: CoroutineContext = Dispatchers.IO): List<Pair<Float, Date>>
    suspend fun requestCurrentMonthExpenseCategoriesDistribution(context: CoroutineContext = Dispatchers.IO): Map<ExpenseCategory, Int>
    suspend fun requestCurrentMonthIncomeCategoriesDistribution(context: CoroutineContext = Dispatchers.IO): Map<IncomeCategory, Int>
}