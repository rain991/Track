package com.example.expensetracker.domain.usecases

import com.example.expensetracker.data.models.ExpenseItem
import com.example.expensetracker.domain.repository.ExpensesListRepository

class AddExpensesItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    suspend fun addExpensesItem(currentExpensesItem : ExpenseItem){
    expensesListRepository.addExpensesItem(currentExpensesItem)
    }
}