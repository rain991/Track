package com.savenko.track.shared.domain.repository.expenses.categories

import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface ExpensesCategoriesListRepository {
    fun getCategoriesList() : Flow<List<ExpenseCategory>>
    suspend fun getCategoryById(id : Int) : ExpenseCategory
    suspend fun getCategoriesByIds(listOfIds : List<Int>) : List<ExpenseCategory>
    suspend fun addCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.Default)
    suspend fun editCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.Default)
    suspend fun deleteCategory(category: ExpenseCategory, context: CoroutineContext = Dispatchers.Default)
}
