package com.example.track.data.viewmodels.mainScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.expenses.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class ExpensesLazyColumnViewModel(
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl,
    private val categoriesListRepositoryImpl: ExpensesCategoriesListRepositoryImpl
) : ViewModel() {
    private val _expensesList = mutableStateListOf<ExpenseItem>()
    val expensesList: List<ExpenseItem> = _expensesList
    private val _categoriesList = mutableStateListOf<ExpenseCategory>()
    val categoriesList: List<ExpenseCategory> = _categoriesList
    private val _isScrolledBelow = MutableStateFlow(value = false)
    val isScrolledBelow = _isScrolledBelow.asStateFlow()
    private val _expandedExpense: MutableStateFlow<ExpenseItem?> = MutableStateFlow(value = null)
    val expandedExpense = _expandedExpense.asStateFlow()

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

    fun setExpandedExpenseCard(value: ExpenseItem?) {
        _expandedExpense.update { value }
    }

    fun setScrolledBelow(firstVisibleIndex: Int) {
        _isScrolledBelow.update { firstVisibleIndex != 0 && _expensesList.size > 8 }
    }

    fun requestCountInMonthNotion(expenseItem: ExpenseItem, expenseCategory: ExpenseCategory): String {
        val calendar = Calendar.getInstance()
        calendar.time = expenseItem.date
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = calendar.time

        val endCalendar = calendar.clone() as Calendar
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = endCalendar.time

        val result = expensesListRepositoryImpl.getExpensesByCategoryInTimeSpan(
            startOfSpan = startDate,
            endOfSpan = endDate,
            category = expenseCategory
        )
        return "${result.size}"
    }

    fun requestSumExpensesInMonthNotion(expenseItem: ExpenseItem, expenseCategory: ExpenseCategory): String {
        val calendar = Calendar.getInstance()
        calendar.time = expenseItem.date
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = calendar.time
        val endCalendar = calendar.clone() as Calendar
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = endCalendar.time
        val result = expensesListRepositoryImpl.getExpensesByCategoryInTimeSpan(
            startOfSpan = startDate,
            endOfSpan = endDate,
            category = expenseCategory
        )
        val sum = result.sumOf { it.value.toInt() } // warning Crypto
        return "$sum"
    }
}