package com.example.expensetracker.domain

class GetExpensesItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun getExpensesItem(expensesItemId: Int): ExpenseItem? {
        return expensesListRepository.getExpensesItem(expensesItemId)
    }
}
