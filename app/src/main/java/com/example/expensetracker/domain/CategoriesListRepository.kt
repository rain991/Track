package com.example.expensetracker.domain

import com.example.expensetracker.data.ExpenseCategory
import com.example.expensetracker.data.ExpenseCategoryDao

interface CategoriesListRepository {
    suspend fun setCategoriesList(expenseCategoryDao: ExpenseCategoryDao)

    fun getCategoriesList()

    fun addCategorie(category : ExpenseCategory)

    fun deleteCategorie(category: ExpenseCategory)
}