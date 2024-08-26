package com.savenko.track.domain.usecases.userData.financialEntities.specified

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
    suspend fun getPeriodSummary(
        periodStartMillis: Long,
        periodEndMillis: Long,
        financialTypes: FinancialTypes
    ): Flow<FinancialCardNotion> {
        return when (financialTypes) {
            FinancialTypes.Expense -> {
                val countOfExpensesFlow =
                    expenseCoreRepository.getCountOfExpensesInSpan(
                        startDate = Date(periodStartMillis),
                        endDate = Date(periodEndMillis)
                    )
                val expensesSummaryFlow = expenseCoreRepository.getSumOfExpensesInTimeSpan(
                    start = periodStartMillis,
                    end = periodEndMillis
                )
                val expenseAverageFlow = expenseCoreRepository.getAverageInTimeSpan(
                    startDate = Date(periodStartMillis),
                    endDate = Date(periodEndMillis)
                )
                combine(countOfExpensesFlow, expensesSummaryFlow, expenseAverageFlow) { count, summary, average ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary, periodAverage = average)
                }
            }

            FinancialTypes.Income -> {
                val countOfIncomesFlow =
                    incomeCoreRepository.getCountOfIncomesInSpan(
                        startDate = Date(periodStartMillis),
                        endDate = Date(periodEndMillis)
                    )
                val incomesSummaryFlow = incomeCoreRepository.getSumOfIncomesInTimeSpan(
                    startOfSpan = Date(periodStartMillis),
                    endOfSpan = Date(periodEndMillis)
                )
                val incomesAverageFlow = incomeCoreRepository.getAverageInTimeSpan(
                    startDate = Date(periodStartMillis),
                    endDate = Date(periodEndMillis)
                )
                combine(countOfIncomesFlow, incomesSummaryFlow, incomesAverageFlow) { count, summary, average ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary, periodAverage = average)
                }
            }
        }
    }

    suspend fun getPeriodSummaryById(
        periodStartMillis: Long,
        periodEndMillis: Long,
        categoryID: Int,
        financialTypes: FinancialTypes
    ): Flow<FinancialCardNotion> {
        return when (financialTypes) {
            FinancialTypes.Expense -> {
                val countOfExpensesFlow =
                    expenseCoreRepository.getCountOfExpensesInSpanByCategoriesIds(
                        startDate = Date(periodStartMillis),
                        endDate = Date(periodEndMillis),
                        categoriesIds = listOf(categoryID)
                    )
                val expensesSummaryFlow = expenseCoreRepository.getSumOfExpensesByCategoriesInTimeSpan(
                    start = periodStartMillis,
                    end = periodEndMillis,
                    categoriesIds = listOf(categoryID)
                )
                combine(countOfExpensesFlow, expensesSummaryFlow) { count, summary ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary)
                }
            }

            FinancialTypes.Income -> {
                val countOfIncomesFlow =
                    incomeCoreRepository.getCountOfIncomesInSpanByCategoriesIds(
                        startDate = Date(periodStartMillis),
                        endDate = Date(periodEndMillis),
                        categoriesIds = listOf(categoryID)
                    )
                val incomesSummaryFlow = incomeCoreRepository.getSumOfIncomesInTimeSpanByCategoriesIds(
                    startOfSpan = Date(periodStartMillis),
                    endOfSpan = Date(periodEndMillis),
                    categoriesIds = listOf(categoryID)
                )
                combine(countOfIncomesFlow, incomesSummaryFlow) { count, summary ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary)
                }
            }
        }
    }
}