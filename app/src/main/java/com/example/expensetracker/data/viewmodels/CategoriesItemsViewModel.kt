package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import kotlinx.coroutines.launch
import kotlin.random.Random

class CategoriesItemsViewModel(private val categoriesListRepositoryImpl: CategoriesListRepositoryImpl) : ViewModel() {
    var categoryList = listOf<ExpenseCategory>().plus(DEFAULT_CATEGORIES)

    init {
        viewModelScope.launch {   // SHOULD BE REPLACED WITH INSERTALL IN CATEGORY DAO
            DEFAULT_CATEGORIES.forEach{
                categoriesListRepositoryImpl.addCategory(it)
            }
        }
        viewModelScope.launch {

            categoriesListRepositoryImpl.getCategoriesList().collect() {
                categoryList += it
                categoryList.distinct()
            }
        }
    }

    companion object {
        val DEFAULT_CATEGORIES = listOf<ExpenseCategory>(
            ExpenseCategory(name = "Groceries", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Utilities", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Housing", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Transportation", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Dining Out", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Entertainment", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Healthcare", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Clothing and Accessories", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Personal Care", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Technology and Electronics", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Savings and Investments", colorId = Random.nextLong(0, Long.MAX_VALUE)),
            ExpenseCategory(name = "Unique", colorId = Random.nextLong(0, Long.MAX_VALUE))
        )
    }
}