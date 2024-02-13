package com.example.expensetracker.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.launch

class MainScreenViewModel(expensesListRepositoryImpl: ExpensesListRepositoryImpl) : ViewModel() {
    var expensesList = listOf<ExpenseItem>()
    init {
        viewModelScope.launch {
            expensesListRepositoryImpl.getSortedExpensesListDateDesc().collect {
                expensesList = it
                Log.d("MyLog", expensesList.size.toString())
            }
        }
    }
}