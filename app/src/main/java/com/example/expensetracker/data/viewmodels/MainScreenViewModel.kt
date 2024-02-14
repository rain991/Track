package com.example.expensetracker.data.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.launch

class MainScreenViewModel(expensesListRepositoryImpl: ExpensesListRepositoryImpl) : ViewModel() {
    private val _elements = mutableStateListOf<ExpenseItem>()
    val elements : List<ExpenseItem> = _elements
    init {
        viewModelScope.launch {
            expensesListRepositoryImpl.getSortedExpensesListDateDesc().collect {
                _elements.addAll(it)
            }
        }
    }
}