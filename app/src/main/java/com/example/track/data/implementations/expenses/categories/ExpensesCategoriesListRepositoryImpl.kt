package com.example.track.data.implementations.expenses.categories

import com.example.track.data.database.expensesRelated.ExpenseCategoryDao
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ExpensesCategoriesListRepositoryImpl(private val categoryDao: ExpenseCategoryDao) : ExpensesCategoriesListRepository {
    override fun getCategoriesList(): Flow<List<ExpenseCategory>> {
        return categoryDao.getAllCategories()
    }

    override suspend fun addCategory(category: ExpenseCategory, context: CoroutineContext) {
        withContext(context = context) {
            categoryDao.insert(category)
        }
    }

    override suspend fun editCategory(category: ExpenseCategory, context: CoroutineContext) {
        withContext(context = context) {
            categoryDao.update(category = category)
        }
    }

    override suspend fun deleteCategory(category: ExpenseCategory, context: CoroutineContext) {
        withContext(context = context) {
            categoryDao.delete(category)
        }
    }

    override fun getCategoriesByIds(listOfIds: List<Int>): List<ExpenseCategory> {
        return categoryDao.getCategoriesByIds(listOfIds)
    }

    override fun getCategoryById(id : Int): ExpenseCategory {
        return categoryDao.getCategoryById(id)
    }
}
