package com.example.expensetracker.domain

import com.example.expensetracker.data.ExpenseItem

class GetExpensesListUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun getExpensesList() : List<ExpenseItem>{
    return expensesListRepository.getExpensesList()
    }
}