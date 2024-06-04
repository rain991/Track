package com.example.track.domain.usecases.expensesRelated.expenseusecases

import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.flow.Flow

class GetExpensesListUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    fun getExpensesList() : Flow<List<ExpenseItem>> {
    return expensesListRepository.getExpensesList()
    }
}