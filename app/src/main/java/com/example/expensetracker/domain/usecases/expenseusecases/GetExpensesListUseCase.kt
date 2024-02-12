package com.example.expensetracker.domain.usecases.expenseusecases

import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseItem

class GetExpensesListUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    fun getExpensesList() : List<ExpenseItem>{
    return expensesListRepository.getExpensesList()
    }
}