package com.savenko.track.data.core

import android.util.Range
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.util.Date

class FinancialCardNotesProvider(
    private val expenseCoreRepositoryImpl: ExpensesCoreRepository,
    private val incomeCoreRepositoryImpl: IncomeCoreRepository
) {
    suspend fun requestCountNotionForFinancialCard(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity,
        timeSpan: Range<Date>
    ): Flow<Int> {
        val startDate = timeSpan.lower
        val endDate = timeSpan.upper
        if (financialEntity is ExpenseItem && financialCategory is ExpenseCategory) {
            val result = expenseCoreRepositoryImpl.getCountOfExpensesInSpanByCategoriesIds(
                startDate = startDate,
                endDate = endDate,
                categoriesIds = listOf(financialCategory.categoryId)
            )
            return result
        } else if (financialEntity is IncomeItem && financialCategory is IncomeCategory) {
            val result = incomeCoreRepositoryImpl.getCountOfIncomesInSpanByCategoriesIds(
                startDate = startDate,
                endDate = endDate,
                categoriesIds = listOf(financialCategory.categoryId)
            )
            return result
        } else {
            return emptyFlow()
        }
    }
    suspend fun requestValueSummaryNotionForFinancialCard(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity,
        timeSpan: Range<Date>
    ): Flow<Float> {
        val startDate = timeSpan.lower
        val endDate = timeSpan.upper
        if (financialEntity is ExpenseItem && financialCategory is ExpenseCategory) {
            val result =
                expenseCoreRepositoryImpl.getSumOfExpensesByCategoriesInTimeSpan(
                    start = startDate.time,
                    end = endDate.time,
                    listOfCategories = listOf(financialCategory.categoryId)
                )
            return result
        }
        if (financialEntity is IncomeItem && financialCategory is IncomeCategory) {
            val result =
                incomeCoreRepositoryImpl.getSumOfIncomesInTimeSpanByCategoriesIds(startDate, endDate, listOf(financialCategory.categoryId))
            return result
        }
        return emptyFlow()
    }
}