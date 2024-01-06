package com.example.expensetracker.domain.usecases.expenseusecases

import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.ExpenseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddExpensesItemUseCase(private val expensesListRepositoryImpl: ExpensesListRepositoryImpl) {
    suspend fun addExpensesItem(currentExpensesItem : ExpenseItem){
        withContext(Dispatchers.IO){
            expensesListRepositoryImpl.addExpensesItem(currentExpensesItem)
        }

    }
}