package com.example.expensetracker.domain.usecases

import com.example.expensetracker.data.models.ExpenseItem
import com.example.expensetracker.domain.repository.ExpensesListRepository

class GetExpensesItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun getExpensesItem(expensesItemId: Int): ExpenseItem? {
        return expensesListRepository.getExpensesItem(expensesItemId)
    }
}
