package com.savenko.track.shared.data.core

import com.savenko.track.shared.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.shared.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeCoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.time.Instant

/**
 * Use to summarize info about financials.
 */
class FinancialCardNotesProvider(
    private val expenseCoreRepositoryImpl: ExpensesCoreRepository,
    private val incomeCoreRepositoryImpl: IncomeCoreRepository
) {
    fun requestCountNotionForFinancialCard(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity,
        timeSpan: ClosedRange<Instant>
    ): Flow<Int> {
        val startDate = timeSpan.start.toEpochMilliseconds()
        val endDate = timeSpan.endInclusive.toEpochMilliseconds()
        return when {
            financialEntity is ExpenseItem && financialCategory is ExpenseCategory -> {
                expenseCoreRepositoryImpl.getCountOfExpensesInSpanByCategoriesIds(
                    startDate = Instant.fromEpochMilliseconds(startDate),
                    endDate = Instant.fromEpochMilliseconds(endDate),
                    categoriesIds = listOf(financialCategory.categoryId)
                )
            }

            financialEntity is IncomeItem && financialCategory is IncomeCategory -> {
                incomeCoreRepositoryImpl.getCountOfIncomesInSpanByCategoriesIds(
                    startDate = Instant.fromEpochMilliseconds(startDate),
                    endDate = Instant.fromEpochMilliseconds(endDate),
                    categoriesIds = listOf(financialCategory.categoryId)
                )
            }
            else -> {
                emptyFlow()
            }
        }
    }

    fun requestValueSummaryNotionForFinancialCard(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity,
        timeSpan: ClosedRange<Instant>
    ): Flow<Float> {
        val startDate = timeSpan.start
        val endDate = timeSpan.endInclusive
        return when{
            financialEntity is ExpenseItem && financialCategory is ExpenseCategory -> {
                expenseCoreRepositoryImpl.getSumOfExpensesByCategoriesInTimeSpan(
                    start = Instant.fromEpochMilliseconds(startDate.toEpochMilliseconds()),
                    end = Instant.fromEpochMilliseconds(endDate.toEpochMilliseconds()),
                    categoriesIds = listOf(financialCategory.categoryId)
                )
            }
            financialEntity is IncomeItem && financialCategory is IncomeCategory -> {
                incomeCoreRepositoryImpl.getSumOfIncomesInTimeSpanByCategoriesIds(
                    startDate,
                    endDate,
                    listOf(financialCategory.categoryId)
                )
            }
            else -> emptyFlow()
        }
    }
}