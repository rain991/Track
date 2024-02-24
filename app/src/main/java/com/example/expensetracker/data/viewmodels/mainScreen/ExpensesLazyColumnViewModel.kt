package com.example.expensetracker.data.viewmodels.mainScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.launch

class ExpensesLazyColumnViewModel(
    expensesListRepositoryImpl: ExpensesListRepositoryImpl,
    categoriesListRepositoryImpl: CategoriesListRepositoryImpl
) : ViewModel() {
    private val _expensesList = mutableStateListOf<ExpenseItem>()
    val expensesList: List<ExpenseItem> = _expensesList

    private val _categoriesList = mutableStateListOf<ExpenseCategory>()
    val categoriesList: List<ExpenseCategory> = _categoriesList
    init {
        viewModelScope.launch {
            categoriesListRepositoryImpl.getCategoriesList().collect {
                _categoriesList.clear()
                _categoriesList.addAll(it)
            }
        }
        viewModelScope.launch {
            expensesListRepositoryImpl.getSortedExpensesListDateDesc().collect {
                _expensesList.clear()
                _expensesList.addAll(it)
            }
        }
    }
}