package com.example.expensetracker.domain

class GetExpensesListUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun getExpensesList() : List<ExpenseItem>{
    return expensesListRepository.getExpensesList()
    }
}