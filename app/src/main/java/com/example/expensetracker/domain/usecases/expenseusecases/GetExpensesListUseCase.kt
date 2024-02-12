package com.example.expensetracker.domain.usecases.expenseusecases

import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.flow.Flow

class GetExpensesListUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    fun getExpensesList() : Flow<List<ExpenseItem>> {
    return expensesListRepository.getExpensesList()
    }
}