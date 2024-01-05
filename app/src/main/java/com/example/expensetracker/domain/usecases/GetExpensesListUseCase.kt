package com.example.expensetracker.domain.usecases

import com.example.expensetracker.data.models.ExpenseItem
import com.example.expensetracker.domain.repository.ExpensesListRepository

class GetExpensesListUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun getExpensesList() : List<ExpenseItem>{
    return expensesListRepository.getExpensesList()
    }
}