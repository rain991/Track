package com.example.track.data.implementations.incomes

import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem
import com.example.track.domain.repository.incomes.IncomesCategoriesListRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class IncomesCategoriesListRepositoryImpl : IncomesCategoriesListRepository{
    override fun getCategoriesList(): Flow<List<IncomeItem>> {
        TODO("Not yet implemented")
    }
    override suspend fun addCategory(category: IncomeCategory, context: CoroutineContext) {
        TODO("Not yet implemented")
    }
    override suspend fun editCategory(category: IncomeCategory, context: CoroutineContext) {
        TODO("Not yet implemented")
    }
    override suspend fun deleteCategory(category: IncomeCategory, context: CoroutineContext) {
        TODO("Not yet implemented")
    }
}