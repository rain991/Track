package com.savenko.track.data.implementations.incomes.categories

import com.savenko.track.data.database.incomeRelated.IncomeCategoryDao
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class IncomesCategoriesListRepositoryImpl(private val incomeCategoryDao : IncomeCategoryDao) : IncomesCategoriesListRepository {
    override fun getCategoriesList(): Flow<List<IncomeCategory>> {
        return incomeCategoryDao.getAllIncomeCategories()
    }

    override suspend fun getOtherCategoryId(): Int {
        return incomeCategoryDao.getCategoryOtherId()
    }

    override suspend fun addCategory(category: IncomeCategory, context: CoroutineContext) {
        withContext(context = context) {
            incomeCategoryDao.insert(category)
        }
    }

    override suspend fun editCategory(category: IncomeCategory, context: CoroutineContext) {
        withContext(context = context) {
            incomeCategoryDao.update(incomeCategory = category)
        }
    }

    override suspend fun deleteCategory(category: IncomeCategory, context: CoroutineContext) {
        withContext(context = context) {
            incomeCategoryDao.delete(category)
        }
    }
}