package com.example.expensetracker.domain.usecases.expenseusecases

import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.ExpenseItem

class GetExpensesItemUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    fun getExpensesItem(expensesItemId: Int): ExpenseItem? {
        return expensesListRepository.getExpensesItem(expensesItemId)
    }
}
