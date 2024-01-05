package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.data.database.ExpenseCategoryDao

interface CategoriesListRepository {
    suspend fun setCategoriesList(expenseCategoryDao: ExpenseCategoryDao)

    fun getCategoriesList()

    fun addCategorie(category : ExpenseCategory)

    fun deleteCategorie(category: ExpenseCategory)
}