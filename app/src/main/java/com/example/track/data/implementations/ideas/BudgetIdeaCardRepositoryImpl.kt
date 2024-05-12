package com.example.track.data.implementations.ideas

import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.domain.repository.ideas.BudgetIdeaCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.zip

class BudgetIdeaCardRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl
) : BudgetIdeaCardRepository {
    override suspend fun requestMonthBudget(): Flow<Int> {
        return dataStoreManager.budgetFlow
    }

    override suspend fun requestWeekBudget(): Float {
        return dataStoreManager.budgetFlow.first().toFloat() / 4
    }

    override suspend fun requestCurrentMonthExpenses(): Flow<Float> {
        return expensesListRepositoryImpl.getCurrentMonthSumOfExpenseInFlow()
    }

    override suspend fun requestBudgetExpectancy(): Flow<Float> {
        return requestMonthBudget().zip(
            other = requestCurrentMonthExpenses(),
            transform = { first, second ->
                second.div(first)
            })
    }

}