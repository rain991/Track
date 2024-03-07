package com.example.track.data.implementations

import com.example.track.data.database.expensesRelated.ExpenseCategoryDao
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.domain.repository.CategoriesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CategoriesListRepositoryImpl(private val categoryDao: ExpenseCategoryDao) : CategoriesListRepository {
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
}
