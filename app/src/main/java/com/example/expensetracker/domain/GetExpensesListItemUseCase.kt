package com.example.expensetracker.domain

import com.example.expensetracker.data.ExpenseItem

class GetExpensesItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun getExpensesItem(expensesItemId: Int): ExpenseItem? {
        return expensesListRepository.getExpensesItem(expensesItemId)
    }
}
