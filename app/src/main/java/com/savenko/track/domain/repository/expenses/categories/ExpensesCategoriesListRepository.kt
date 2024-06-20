package com.savenko.track.domain.repository.expenses.categories

import com.savenko.track.domain.models.expenses.ExpenseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface ExpensesCategoriesListRepository {
    fun getCategoriesList() : Flow<List<ExpenseCategory>>
    fun getCategoryById(id : Int) : ExpenseCategory
    fun getCategoriesByIds(listOfIds : List<Int>) : List<ExpenseCategory>
    suspend fun addCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun editCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.IO)
}