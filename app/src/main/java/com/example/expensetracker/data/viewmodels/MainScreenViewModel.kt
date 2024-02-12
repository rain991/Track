package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.launch

class MainScreenViewModel(expensesListRepositoryImpl: ExpensesListRepositoryImpl) : ViewModel() {
    var expensesList = listOf<ExpenseItem>()

    init {
        viewModelScope.launch {
            expensesListRepositoryImpl.getExpensesList().collect() {
                expensesList = it
            }
        }
    }


}