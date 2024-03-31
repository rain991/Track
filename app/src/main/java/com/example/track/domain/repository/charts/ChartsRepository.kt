package com.example.track.domain.repository.charts

import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface ChartsRepository {
    suspend fun requestCurrentMonthExpensesDateDesc(context: CoroutineContext = Dispatchers.IO): List<ExpenseItem>
    suspend fun requestCurrentMonthIncomesDateDesc(context: CoroutineContext = Dispatchers.IO): List<IncomeItem>
    suspend fun requestCurrentYearExpensesDateDesc(context: CoroutineContext = Dispatchers.IO): List<ExpenseItem>
    suspend fun requestCurrentYearIncomesDateDesc(context: CoroutineContext = Dispatchers.IO): List<IncomeItem>
    suspend fun requestCurrentMonthExpenseCategoriesDistribution(context: CoroutineContext = Dispatchers.IO): Map<ExpenseCategory, Int>
    suspend fun requestCurrentMonthIncomeCategoriesDistribution(context: CoroutineContext = Dispatchers.IO): Map<IncomeCategory, Int>
}