package com.example.expensetracker.domain

import com.example.expensetracker.data.ExpenseItem

class AddExpensesItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    suspend fun addExpensesItem(currentExpensesItem : ExpenseItem){
    expensesListRepository.addExpensesItem(currentExpensesItem)
    }
}