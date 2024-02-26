package com.example.expensetracker.data.viewmodels.mainScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.launch
import java.util.Calendar

class ExpensesLazyColumnViewModel(
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl,
    private val categoriesListRepositoryImpl: CategoriesListRepositoryImpl
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

    suspend fun requestCountInMonthNotion(expenseItem: ExpenseItem, expenseCategory: ExpenseCategory): String {
        val calendar = Calendar.getInstance()
        calendar.time = expenseItem.date
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = calendar.time
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = calendar.time
        val result = expensesListRepositoryImpl.getExpensesByCategoryInTimeSpan(
            startOfSpan = startDate,
            endOfSpan = endDate,
            category = expenseCategory
        )
        var resultCount = 0
        result.collect{
            resultCount = it.count()
        }
        return "${expenseCategory.note} trans. this month : {$}"
    }
}