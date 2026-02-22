package com.savenko.track.shared.domain.repository.incomes.categories

import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface IncomesCategoriesListRepository {
    fun getCategoriesList(): Flow<List<IncomeCategory>>
    suspend fun getOtherCategoryId(): Int
    suspend fun addCategory(category: IncomeCategory, context: CoroutineContext = Dispatchers.Default)
    suspend fun editCategory(category: IncomeCategory, context: CoroutineContext = Dispatchers.Default)
    suspend fun deleteCategory(category: IncomeCategory, context: CoroutineContext = Dispatchers.Default)

}