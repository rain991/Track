package com.savenko.track.shared.data.implementations.ideas

import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import com.savenko.track.shared.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.shared.domain.repository.ideas.uiProviders.BudgetIdeaCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class BudgetIdeaCardRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val expensesCoreRepositoryImpl: ExpensesCoreRepository
) : BudgetIdeaCardRepository {
    override fun requestMonthBudget(): Flow<Float> {
        return dataStoreManager.budgetFlow
    }
    override fun requestCurrentMonthExpenses(): Flow<Float> {
        return expensesCoreRepositoryImpl.getCurrentMonthSumOfExpense()
    }
    override fun requestBudgetExpectancy(): Flow<Float> {
        return requestMonthBudget().combine(requestCurrentMonthExpenses()){
            monthBudget, currentMonthExpense ->
            if (monthBudget > 0f) currentMonthExpense.div(monthBudget) else 0f
        }
    }
}
