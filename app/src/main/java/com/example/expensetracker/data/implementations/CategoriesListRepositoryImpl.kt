package com.example.expensetracker.data.implementations

import android.content.Context
import com.example.expensetracker.R
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.domain.repository.CategoriesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class CategoriesListRepositoryImpl(private val categoryDao: ExpenseCategoryDao) : CategoriesListRepository {
    private var categoriesList = listOf<ExpenseCategory>()
    private var categoriesNames = categoriesList.map { it.name }

    override suspend fun setCategoriesList(categoryDao: ExpenseCategoryDao, context: CoroutineContext) {
        withContext(context = context) {
            categoryDao.getAllCategories().collect {
                categoriesList = it
            }
        }
    }

    override fun getCategoriesList(): List<ExpenseCategory> {
        return categoriesList
    }

    override fun getCategoryItem(categoryItemId: Int): ExpenseCategory? {
        return categoriesList.find { it.categoryId == categoryItemId }
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

    override suspend fun addDefaultCategories(context: Context, coroutineContext: CoroutineContext) {
        val categoriesNamesFromResources = context.resources.getStringArray(R.array.default_expenses)
        if (!checkDefaultCategories(context)) {
            categoriesNames = categoriesNames.plus(categoriesNamesFromResources)
            categoriesNames = categoriesNames.toSet().distinct()
            categoriesNames.forEach { it ->
                withContext(Dispatchers.IO) {
                    addCategory(ExpenseCategory(name = it, colorId = Random.nextLong(0, Long.MAX_VALUE)))
                }
            }
        }
    }

    override fun checkDefaultCategories(context: Context): Boolean {
        val categoriesNamesFromResources = context.resources.getStringArray(R.array.default_expenses)
        return categoriesNames.toSet() == categoriesNamesFromResources
    }
}