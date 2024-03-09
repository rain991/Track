package com.example.track.domain.repository.incomes

import com.example.track.data.models.incomes.IncomeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface IncomesCategoriesListRepository {
    fun getCategoriesList(): Flow<List<IncomeCategory>>
    suspend fun addCategory(category: IncomeCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun editCategory(category: IncomeCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteCategory(category: IncomeCategory, context: CoroutineContext = Dispatchers.IO)
}