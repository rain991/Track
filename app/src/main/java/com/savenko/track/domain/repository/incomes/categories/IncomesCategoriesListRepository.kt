package com.savenko.track.domain.repository.incomes.categories

import com.savenko.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface IncomesCategoriesListRepository {
    fun getCategoriesList(): Flow<List<IncomeCategory>>
    suspend fun getOtherCategoryId(): Int
    suspend fun addCategory(category: IncomeCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun editCategory(category: IncomeCategory, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteCategory(category: IncomeCategory, context: CoroutineContext = Dispatchers.IO)

}