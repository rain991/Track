package com.example.track.domain.repository.charts

import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem

interface ChartsRepository {
    fun requestCurrentMonthExpenses(): List<ExpenseItem>
    fun requestCurrentMonthIncomes(): List<IncomeItem>
    fun requestCurrentYearExpenses(): List<ExpenseItem>
    fun requestCurrentYearIncomes(): List<IncomeItem>
    fun requestCurrentMonthExpenseCategoriesDistribution(): Map<ExpenseCategory, Int>
    fun requestCurrentMonthIncomeCategoriesDistribution(): Map<IncomeCategory, Int>
}