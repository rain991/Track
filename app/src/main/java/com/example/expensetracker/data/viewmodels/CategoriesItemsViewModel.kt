package com.example.expensetracker.data.viewmodels

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
            ExpenseCategory(categoryId = 1, name = "Groceries", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 2, name = "Utilities", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 3, name = "Housing", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 4, name = "Transportation", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 5, name = "Dining Out", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 6, name = "Entertainment", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 7, name = "Healthcare", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 8, name = "Clothing and Accessories", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 9, name = "Personal Care", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 10, name = "Technology and Electronics", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 11, name = "Savings and Investments", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 12, name = "Unique", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(categoryId = 13, name = "Other", colorId = Random.nextLong(0, Long.MAX_VALUE))
        )
    }
}