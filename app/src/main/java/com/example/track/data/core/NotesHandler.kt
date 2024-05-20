package com.example.track.data.core

import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.example.track.data.other.converters.getEndOfTheMonth
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.domain.models.abstractLayer.CategoryEntity
import com.example.track.domain.models.abstractLayer.FinancialEntity
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.expenses.ExpenseItem
import com.example.track.domain.models.incomes.IncomeCategory
import com.example.track.domain.models.incomes.IncomeItem
import kotlinx.coroutines.flow.first

class NotesHandler(
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl,
    private val incomeListRepositoryImpl: IncomeListRepositoryImpl
) {
    suspend fun requestCountInMonthNotionForFinancialCard(financialEntity: FinancialEntity, financialCategory: CategoryEntity): String {
        val startDate = getStartOfMonthDate(financialEntity.date)
        val endDate = getEndOfTheMonth(financialEntity.date)
        if (financialEntity is ExpenseItem && financialCategory is ExpenseCategory) {
            val result = expensesListRepositoryImpl.getExpensesByCategoryInTimeSpan(
                startOfSpan = startDate,
                endOfSpan = endDate,
                category = financialCategory
            )
            return "${result.size}"
        } else if (financialEntity is IncomeItem && financialCategory is IncomeCategory) {
            val result = incomeListRepositoryImpl.getIncomesByCategoryInTimeSpan(
                startOfSpan = startDate,
                endOfSpan = endDate,
                category = financialCategory
            )
            return "${result.size}"
        } else {
            return ""
        }
    }

    suspend fun requestSumExpensesInMonthNotionForFinancialCard(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity
    ): String {
        val startDate = getStartOfMonthDate(financialEntity.date)
        val endDate = getEndOfTheMonth(financialEntity.date)

        if (financialEntity is ExpenseItem && financialCategory is ExpenseCategory) {
            val result = expensesListRepositoryImpl.getCurrentMonthSumOfExpensesByCategories(listOf(financialCategory))
            return result.first().toString()
        } else if (financialEntity is IncomeItem && financialCategory is IncomeCategory) {
            val result = incomeListRepositoryImpl.getSumOfIncomesInTimeSpan(startDate, endDate)
            return result.first().toString()
        } else {
            return ""
        }
    }
}