package com.savenko.track.domain.usecases.crud.expenseRelated

import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.expenses.ExpenseItemRepository

class AddExpenseItemUseCase(private val expenseItemRepositoryImpl: ExpenseItemRepository) {
    suspend operator fun invoke(currentExpensesItem: ExpenseItem) {
        expenseItemRepositoryImpl.addExpensesItem(currentExpensesItem)
    }
}