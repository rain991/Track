package com.example.track.domain.usecases.expenseusecases

import com.example.track.data.implementations.ExpensesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.flow.Flow

class GetExpensesListUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    fun getExpensesList() : Flow<List<ExpenseItem>> {
    return expensesListRepository.getExpensesList()
    }
}