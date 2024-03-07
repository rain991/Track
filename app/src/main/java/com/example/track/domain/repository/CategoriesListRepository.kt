package com.example.track.domain.repository

import com.example.track.data.models.Expenses.ExpenseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface CategoriesListRepository {
    fun getCategoriesList() : Flow<List<ExpenseCategory>>
    suspend fun addCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun editCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
}