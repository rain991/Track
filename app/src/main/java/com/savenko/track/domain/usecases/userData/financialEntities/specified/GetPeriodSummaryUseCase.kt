package com.savenko.track.domain.usecases.userData.financialEntities.specified

import android.util.Range
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Date

class GetPeriodSummaryUseCase(
    private val incomeCoreRepository: IncomeCoreRepository,
    private val expenseCoreRepository: ExpensesCoreRepository
) {
    suspend fun getPeriodSummaryById(
        dateRange: Range<Date>,
        categoryID: Int,
        financialTypes: FinancialTypes
    ): Flow<FinancialCardNotion> {
        return when (financialTypes) {
            FinancialTypes.Expense -> {
                val countOfExpensesFlow =
                    expenseCoreRepository.getCountOfExpensesInSpanByCategoriesIds(
                        startDate = dateRange.lower,
                        endDate = dateRange.upper,
                        categoriesIds = listOf(categoryID)
                    )
                val expensesSummaryFlow = expenseCoreRepository.getSumOfExpensesByCategoriesInTimeSpan(
                    start = dateRange.lower.time,
                    end = dateRange.upper.time,
                    categoriesIds = listOf(categoryID)
                )
                combine(countOfExpensesFlow, expensesSummaryFlow) { count, summary ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary)
                }
            }

            FinancialTypes.Income -> {
                val countOfIncomesFlow =
                    incomeCoreRepository.getCountOfIncomesInSpanByCategoriesIds(
                        startDate = dateRange.lower,
                        endDate = dateRange.upper,
                        categoriesIds = listOf(categoryID)
                    )
                val incomesSummaryFlow = incomeCoreRepository.getSumOfIncomesInTimeSpanByCategoriesIds(
                    startOfSpan = dateRange.lower,
                    endOfSpan = dateRange.upper,
                    categoriesIds = listOf(categoryID)
                )
                combine(countOfIncomesFlow, incomesSummaryFlow) { count, summary ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary)
                }
            }
        }
    }

    suspend fun getPeriodSummary(
        dateRange: Range<Date>,
        financialTypes: FinancialTypes
    ): Flow<FinancialCardNotion> {
        return when (financialTypes) {
            FinancialTypes.Expense -> {
                val countOfExpensesFlow =
                    expenseCoreRepository.getCountOfExpensesInSpan(
                        startDate = dateRange.lower,
                        endDate = dateRange.upper
                    )
                val expensesSummaryFlow = expenseCoreRepository.getSumOfExpensesInTimeSpan(
                    start = dateRange.lower.time,
                    end = dateRange.upper.time
                )
                combine(countOfExpensesFlow, expensesSummaryFlow) { count, summary ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary)
                }
            }

            FinancialTypes.Income -> {
                val countOfIncomesFlow =
                    incomeCoreRepository.getCountOfIncomesInSpan(startDate = dateRange.lower, endDate = dateRange.upper)
                val incomesSummaryFlow = incomeCoreRepository.getSumOfIncomesInTimeSpan(
                    startOfSpan = dateRange.lower,
                    endOfSpan = dateRange.upper
                )
                combine(countOfIncomesFlow, incomesSummaryFlow) { count, summary ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary)
                }
            }
        }
    }
}