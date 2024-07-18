package com.savenko.track.domain.usecases.userData.financialEntities.specified

import android.util.Range
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Date

class GetDesiredFinancialEntitiesUseCase(
    private val incomeListRepositoryImpl: IncomeListRepository,
    private val expensesListRepositoryImpl: ExpensesListRepository
) {
    suspend operator fun invoke(timePeriod: Range<Date>): Flow<MutableList<FinancialEntity>> {
        val expenseItemsFlow =
            expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
                timePeriod.lower,
                timePeriod.upper
            )
        val incomeItemsFlow = incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(
            timePeriod.lower,
            timePeriod.upper
        )
        return combine(expenseItemsFlow, incomeItemsFlow) { expenseItems, incomeItems ->
            val list = mutableListOf<FinancialEntity>()
            list.addAll(expenseItems)
            list.addAll(incomeItems)
            list
        }
    }
}