package com.example.track.domain.repository.charts

import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem

interface ChartsRepository {
    suspend fun requestCurrentMonthExpensesDateDesc(): List<ExpenseItem>
    suspend fun requestCurrentMonthIncomesDateDesc(): List<IncomeItem>
    suspend fun requestCurrentYearExpensesDateDesc(): List<ExpenseItem>
    suspend fun requestCurrentYearIncomesDateDesc(): List<IncomeItem>
    suspend fun requestCurrentMonthExpenseCategoriesDistribution(): Map<ExpenseCategory, Int>
    suspend fun requestCurrentMonthIncomeCategoriesDistribution(): Map<IncomeCategory, Int>
}