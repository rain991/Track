package com.savenko.track.domain.usecases.userData.financialEntities.specified

import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlin.time.Instant

class GetPeriodSummaryUseCase(
    private val incomeCoreRepository: IncomeCoreRepository,
    private val expenseCoreRepository: ExpensesCoreRepository
) {
    fun getPeriodSummary(
        periodStartMillis: Long,
        periodEndMillis: Long,
        financialTypes: FinancialTypes
    ): Flow<FinancialCardNotion> {
        return when (financialTypes) {
            FinancialTypes.Expense -> {
                val countOfExpensesFlow =
                    expenseCoreRepository.getCountOfExpensesInSpan(
                        startDate = Instant.fromEpochMilliseconds(periodStartMillis),
                        endDate = Instant.fromEpochMilliseconds(periodEndMillis)
                    )
                val expensesSummaryFlow = expenseCoreRepository.getSumOfExpensesInTimeSpan(
                    start = periodStartMillis,
                    end = periodEndMillis
                )
                val expenseAverageFlow = expenseCoreRepository.getAverageInTimeSpan(
                    startDate = Instant.fromEpochMilliseconds(periodStartMillis),
                    endDate = Instant.fromEpochMilliseconds(periodEndMillis)
                )
                combine(
                    countOfExpensesFlow,
                    expensesSummaryFlow,
                    expenseAverageFlow
                ) { count, summary, average ->
                    FinancialCardNotion(
                        financialsQuantity = count,
                        financialSummary = summary,
                        periodAverage = average
                    )
                }
            }

            FinancialTypes.Income -> {
                val countOfIncomesFlow =
                    incomeCoreRepository.getCountOfIncomesInSpan(
                        startDate = Instant.fromEpochMilliseconds(periodStartMillis),
                        endDate = Instant.fromEpochMilliseconds(periodEndMillis)
                    )
                val incomesSummaryFlow = incomeCoreRepository.getSumOfIncomesInTimeSpan(
                    startOfSpan = Instant.fromEpochMilliseconds(periodStartMillis),
                    endOfSpan = Instant.fromEpochMilliseconds(periodEndMillis)
                )
                val incomesAverageFlow = incomeCoreRepository.getAverageInTimeSpan(
                    startDate = Instant.fromEpochMilliseconds(periodStartMillis),
                    endDate = Instant.fromEpochMilliseconds(periodEndMillis)
                )
                combine(
                    countOfIncomesFlow,
                    incomesSummaryFlow,
                    incomesAverageFlow
                ) { count, summary, average ->
                    FinancialCardNotion(
                        financialsQuantity = count,
                        financialSummary = summary,
                        periodAverage = average
                    )
                }
            }
        }
    }

    fun getPeriodSummaryById(
        periodStartMillis: Long,
        periodEndMillis: Long,
        categoryID: Int,
        financialTypes: FinancialTypes
    ): Flow<FinancialCardNotion> {
        return when (financialTypes) {
            FinancialTypes.Expense -> {
                val countOfExpensesFlow =
                    expenseCoreRepository.getCountOfExpensesInSpanByCategoriesIds(
                        startDate = Instant.fromEpochMilliseconds(periodStartMillis),
                        endDate = Instant.fromEpochMilliseconds(periodEndMillis),
                        categoriesIds = listOf(categoryID)
                    )
                val expensesSummaryFlow =
                    expenseCoreRepository.getSumOfExpensesByCategoriesInTimeSpan(
                        start = Instant.fromEpochMilliseconds(periodStartMillis),
                        end = Instant.fromEpochMilliseconds(periodEndMillis),
                        categoriesIds = listOf(categoryID)
                    )
                combine(countOfExpensesFlow, expensesSummaryFlow) { count, summary ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary)
                }
            }

            FinancialTypes.Income -> {
                val countOfIncomesFlow =
                    incomeCoreRepository.getCountOfIncomesInSpanByCategoriesIds(
                        startDate = Instant.fromEpochMilliseconds(periodStartMillis),
                        endDate = Instant.fromEpochMilliseconds(periodEndMillis),
                        categoriesIds = listOf(categoryID)
                    )
                val incomesSummaryFlow =
                    incomeCoreRepository.getSumOfIncomesInTimeSpanByCategoriesIds(
                        startOfSpan = Instant.fromEpochMilliseconds(periodStartMillis),
                        endOfSpan = Instant.fromEpochMilliseconds(periodEndMillis),
                        categoriesIds = listOf(categoryID)
                    )
                combine(countOfIncomesFlow, incomesSummaryFlow) { count, summary ->
                    FinancialCardNotion(financialsQuantity = count, financialSummary = summary)
                }
            }
        }
    }
}