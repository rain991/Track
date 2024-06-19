package com.example.track.data.implementations.ideas

import com.example.track.data.implementations.expenses.ExpensesCoreRepositoryImpl
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.domain.repository.ideas.uiProviders.BudgetIdeaCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class BudgetIdeaCardRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val expensesCoreRepositoryImpl: ExpensesCoreRepositoryImpl
) : BudgetIdeaCardRepository {
    override suspend fun requestMonthBudget(): Flow<Float> {
        return dataStoreManager.budgetFlow
    }
    override suspend fun requestCurrentMonthExpenses(): Flow<Float> {
        return expensesCoreRepositoryImpl.getCurrentMonthSumOfExpense()
    }
    override suspend fun requestBudgetExpectancy(): Flow<Float> {
        return requestMonthBudget().combine(requestCurrentMonthExpenses()){
            monthBudget, currentMonthExpense -> currentMonthExpense.div(monthBudget)
        }
    }
}