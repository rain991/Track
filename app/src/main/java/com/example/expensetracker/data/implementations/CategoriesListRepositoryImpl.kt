package com.example.expensetracker.data.implementations

import android.content.Context
import com.example.expensetracker.R
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.domain.repository.CategoriesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class CategoriesListRepositoryImpl(private val categoryDao: ExpenseCategoryDao) : CategoriesListRepository {
    private var categoriesList = mutableListOf<ExpenseCategory>()
    private var categoriesNames = categoriesList.map { it.name }
    override suspend fun setCategoriesList(categoryDao: ExpenseCategoryDao) {
        withContext(Dispatchers.IO) { categoriesList = categoryDao.getAllCategories().toMutableList() }
    }

    override fun getCategoriesList(): MutableList<ExpenseCategory> {
        return categoriesList
    }

    override fun getCategoryItem(categoryItemId: Long): ExpenseCategory? {
        if (categoriesList.find { it.categoryId.toLong() == categoryItemId } == null) {
            return null
        } else {
            return categoriesList.find { it.categoryId.toLong() == categoryItemId } // WARNING !! call, to be checked afterwards
        }
    }

    override suspend fun editCategory(category: ExpenseCategory) {
        val olderCategory = getCategoryItem(category.categoryId.toLong())
        if (olderCategory != null) {
            categoriesList.remove(olderCategory)
            addCategory(category)
            withContext(Dispatchers.IO) {
                categoryDao.delete(olderCategory)
                categoryDao.insert(category)
            }
        }
    }

    override suspend fun addCategory(category: ExpenseCategory) {
        if (categoriesList.none { it.categoryId == category.categoryId && it.name == category.name }) {
            categoriesList.add(category)
            withContext(Dispatchers.IO) {
                categoryDao.insert(category)
            }
        } else {
            category.categoryId++
            categoriesList.add(category)
            withContext(Dispatchers.IO) {
                categoryDao.insert(category)
            }
        }
    }

    override suspend fun deleteCategory(category: ExpenseCategory) {
        categoriesList.remove(category)
        withContext(Dispatchers.IO) {
            categoryDao.delete(category)
        }
    }

    override fun checkDefaultCategories(context: Context): Boolean {
        val categoriesNamesFromResources = context.resources.getStringArray(R.array.default_expenses)
        return categoriesNames.toSet() == categoriesNamesFromResources
    }

    override suspend fun addDefaultCategories(context: Context) {
        if (!checkDefaultCategories(context)) {
            val categoriesNamesFromResources = context.resources.getStringArray(R.array.default_expenses)
            categoriesNames = categoriesNames.plus(categoriesNamesFromResources)
            categoriesNames = categoriesNames.toSet().toList()
            categoriesNames.forEach { it ->
                withContext(Dispatchers.IO) {
                    addCategory(ExpenseCategory(name = it, colorId = Random.nextLong(0, Long.MAX_VALUE)))
                }
            }
        }
    }
}