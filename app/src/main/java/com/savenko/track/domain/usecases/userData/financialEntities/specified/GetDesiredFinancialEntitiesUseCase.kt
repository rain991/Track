package com.savenko.track.domain.usecases.userData.financialEntities.specified

import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetDesiredFinancialEntitiesUseCase(
    private val incomeListRepositoryImpl: IncomeListRepository,
    private val expensesListRepositoryImpl: ExpensesListRepository
) {
    suspend operator fun invoke(startOfSpan : Long, endOfSpan : Long): Flow<List<FinancialEntity>> {
        val expenseItemsFlow =
            expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
                startOfSpan,
                endOfSpan
            )
        val incomeItemsFlow = incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(
            startOfSpan,
            endOfSpan
        )
        return combine(expenseItemsFlow, incomeItemsFlow) { expenseItems, incomeItems ->
            val list = mutableListOf<FinancialEntity>()
            list.addAll(expenseItems)
            list.addAll(incomeItems)
            list.toList()
        }
    }
}
