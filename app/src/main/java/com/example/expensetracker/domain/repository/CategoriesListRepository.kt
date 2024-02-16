package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface CategoriesListRepository {
    suspend fun getCategoriesList(context: CoroutineContext= Dispatchers.IO) : Flow<List<ExpenseCategory>>
    suspend fun addCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun editCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
}