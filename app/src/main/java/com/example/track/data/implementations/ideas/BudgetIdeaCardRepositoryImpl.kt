package com.example.track.data.implementations.ideas

import com.example.track.data.DataStoreManager
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.models.idea.Idea
import com.example.track.domain.repository.ideas.BudgetIdeaCardRepository
import kotlinx.coroutines.flow.first

class BudgetIdeaCardRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl
) : BudgetIdeaCardRepository {
    override suspend fun requestMonthBudget(): Int {
      return dataStoreManager.budgetFlow.first()
    }

    override suspend fun requestWeekBudget(): Float {
        return dataStoreManager.budgetFlow.first().toFloat()/4
    }

    override fun requestCurrentMonthExpenses(idea: Idea): Float {
        return expensesListRepositoryImpl.getCurrentMonthSumOfExpenses()
    }

    override suspend fun requestBudgetExpectancy(idea: Idea): Float {
        return expensesListRepositoryImpl.getCurrentMonthSumOfExpenses().div(requestMonthBudget())
    }

}