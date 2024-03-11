package com.example.track.data.implementations.ideas

import com.example.track.data.DataStoreManager
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.domain.repository.ideas.BudgetIdeaCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

class BudgetIdeaCardRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl
) : BudgetIdeaCardRepository {
    override suspend fun requestMonthBudget(): Int {
        return dataStoreManager.budgetFlow.first()
    }

    override suspend fun requestWeekBudget(): Float {
        return dataStoreManager.budgetFlow.first().toFloat() / 4
    }

    override fun requestCurrentMonthExpenses(): Flow<Float> {
        return expensesListRepositoryImpl.getCurrentMonthSumOfExpenseInFlow()
    }

    override suspend fun requestBudgetExpectancy(): Flow<Float> {
        return expensesListRepositoryImpl.getCurrentMonthSumOfExpenseInFlow().transform { value -> value / 4 }
    }

}