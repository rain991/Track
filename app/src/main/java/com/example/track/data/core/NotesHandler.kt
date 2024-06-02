package com.example.track.data.core

import com.example.track.data.implementations.expenses.ExpensesCoreRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeCoreRepositoryImpl
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
    private val expenseCoreRepositoryImpl: ExpensesCoreRepositoryImpl,
    private val incomeCoreRepositoryImpl: IncomeCoreRepositoryImpl
) {
    // used in financial cards
    suspend fun requestCountInMonthNotionForFinancialCard(financialEntity: FinancialEntity, financialCategory: CategoryEntity): Int {
        val startDate = getStartOfMonthDate(financialEntity.date)
        val endDate = getEndOfTheMonth(financialEntity.date)
        if (financialEntity is ExpenseItem && financialCategory is ExpenseCategory) {
            val result = expenseCoreRepositoryImpl.getCountOfExpensesInSpanByCategoriesIds(
                startDate = startDate,
                endDate = endDate,
                categoriesIds = listOf(financialCategory.categoryId)
            ).first()
            return result
        } else if (financialEntity is IncomeItem && financialCategory is IncomeCategory) {
            val result = incomeCoreRepositoryImpl.getCountOfIncomesInSpanByCategoriesIds(
                startDate = startDate,
                endDate = endDate,
                categoriesIds = listOf(financialCategory.categoryId)
            ).first()
            return result
        } else {
            return 0
        }
    }
    // used in financial cards
    suspend fun requestValueSummaryForMonthNotion(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity
    ): Float {
        val startDate = getStartOfMonthDate(financialEntity.date)
        val endDate = getEndOfTheMonth(financialEntity.date)
        if (financialEntity is ExpenseItem && financialCategory is ExpenseCategory) {
            val result =
                expenseCoreRepositoryImpl.getSumOfExpensesByCategories(
                    start = startDate.time,
                    end = endDate.time,
                    listOfCategories = listOf(financialCategory.categoryId)
                )
            return result.first()
        } else if (financialEntity is IncomeItem && financialCategory is IncomeCategory) {
            val result = incomeCoreRepositoryImpl.getSumOfIncomesInTimeSpan(startDate, endDate)
            return result.first()
        } else {
            return 0.0f
        }
    }
}