package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.domain.repository.CategoriesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class CategoriesListRepositoryImpl(private val categoryDao: ExpenseCategoryDao) : CategoriesListRepository {
    private var categoriesList = mutableListOf<ExpenseCategory>()

    override suspend fun setCategoriesList(categoryDao: ExpenseCategoryDao) {
        coroutineScope { categoriesList = categoryDao.getAllCategories().toMutableList() }
    }

    override fun getCategoriesList(): MutableList<ExpenseCategory> {
        return categoriesList
    }

    override fun getCategoryItem(categoryItemId: Long): ExpenseCategory? {
        if (categoriesList.find { it.categoryId == categoryItemId } == null) {
            return null
        } else {
            return categoriesList.find { it.categoryId == categoryItemId } // WARNING !! call, to be checked afterwards
        }
    }

    override suspend fun editCategory(category: ExpenseCategory) {
        val olderCategory = getCategoryItem(category.categoryId)
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
        if (categoriesList.none { it.categoryId == category.categoryId }) {
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
}