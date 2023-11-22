package com.example.expensetracker.domain

class AddExpensesItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun addExpensesItem(currentExpensesItem : ExpenseItem){
    expensesListRepository.addExpensesItem(currentExpensesItem)
    }
}