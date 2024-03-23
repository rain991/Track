package com.example.track.domain.repository.charts

import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem

interface ChartsRepository {
    suspend fun requestCurrentMonthExpensesDesc(): List<ExpenseItem>
    suspend fun requestCurrentMonthIncomesDesc(): List<IncomeItem>
    suspend fun requestCurrentYearExpensesDesc(): List<ExpenseItem>
    suspend fun requestCurrentYearIncomesDesc(): List<IncomeItem>
    suspend fun requestCurrentMonthExpenseCategoriesDistribution(): Map<ExpenseCategory, Int>
    suspend fun requestCurrentMonthIncomeCategoriesDistribution(): Map<IncomeCategory, Int>
}