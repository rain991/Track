package com.example.expensetracker.domain.repository

import android.content.Context
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.models.ExpenseCategory
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface CategoriesListRepository {
    suspend fun setCategoriesList(categoryDao: ExpenseCategoryDao, context: CoroutineContext= Dispatchers.IO)
    suspend fun addCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun editCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun addDefaultCategories(context: Context, coroutineContext: CoroutineContext = Dispatchers.IO)

    fun getCategoriesList(): List<ExpenseCategory>
    fun getCategoryItem(categoryItemId: Int): ExpenseCategory?
    fun checkDefaultCategories(context: Context): Boolean
}