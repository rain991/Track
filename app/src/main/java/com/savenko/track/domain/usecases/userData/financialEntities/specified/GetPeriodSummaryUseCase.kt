package com.savenko.track.domain.usecases.userData.financialEntities.specified

import android.util.Range
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Date

class GetPeriodSummaryUseCase(
    private val incomeCoreRepository: IncomeCoreRepository,
    private val expenseCoreRepository: ExpensesCoreRepository
) {
    suspend fun getPeriodSummary(dateRange: Range<Date>, financialTypes: FinancialTypes): Flow<Pair<Int, Float>> {
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
                    Pair(first = count, second = summary)
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
                    Pair(first = count, second = summary)
                }
            }
        }
    }
}