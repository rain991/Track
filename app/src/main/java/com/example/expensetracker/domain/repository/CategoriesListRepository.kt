package com.example.expensetracker.domain.repository

import android.content.Context
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.models.ExpenseCategory

interface CategoriesListRepository {
    suspend fun setCategoriesList(categoryDao: ExpenseCategoryDao)

    fun getCategoriesList(): MutableList<ExpenseCategory>

    fun getCategoryItem(categoryItemId: Long): ExpenseCategory?

    suspend fun addCategory(category: ExpenseCategory)

    suspend fun editCategory(category: ExpenseCategory)

    suspend fun deleteCategory(category: ExpenseCategory)

    fun checkDefaultCategories(context: Context): Boolean

    suspend fun addDefaultCategories(context: Context)
}