package com.example.expensetracker.data.viewmodels.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import kotlinx.coroutines.launch
import kotlin.random.Random

class CategoriesItemsViewModel(private val categoriesListRepositoryImpl: CategoriesListRepositoryImpl) : ViewModel() {
    var categoryList = listOf<ExpenseCategory>()

    init {
        viewModelScope.launch {   // SHOULD BE REPLACED WITH INSERTALL IN CATEGORY DAO
            DEFAULT_CATEGORIES.forEach {
                categoriesListRepositoryImpl.addCategory(it)
            }
        }
    }

    companion object {
        val DEFAULT_CATEGORIES = listOf<ExpenseCategory>(
            ExpenseCategory(categoryId = 1, note = "Groceries", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 2, note = "Utilities", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 3, note = "Housing", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 4, note = "Transportation", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 5, note = "Dining Out", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 6, note = "Entertainment", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 7, note = "Healthcare", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 8, note = "Clothing and Accessories", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 9, note = "Personal Care", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 10, note = "Technology and Electronics", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 11, note = "Savings and Investments", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 12, note = "Unique", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 13, note = "Other", colorId = Random.nextLong(0, Long.MAX_VALUE))
        )
    }
}