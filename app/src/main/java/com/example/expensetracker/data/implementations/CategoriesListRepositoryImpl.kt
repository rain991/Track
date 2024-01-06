package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.domain.repository.CategoriesListRepository
import kotlinx.coroutines.coroutineScope

class CategoriesListRepositoryImpl (categoryDao: ExpenseCategoryDao): CategoriesListRepository {
    private var categoriesList = mutableListOf<ExpenseCategory>()

    override suspend fun setCategoriesList(categoryDao: ExpenseCategoryDao) {
        coroutineScope { categoriesList =  categoryDao.getAllCategories().toMutableList()}
    }

    override fun getCategoriesList() : MutableList<ExpenseCategory>{
        return categoriesList
    }

    override fun getCategoryItem(categoryItemId : Long): ExpenseCategory? {
        if (categoriesList.find { it.categoryId == categoryItemId } == null) {
            return null
        } else {
            return categoriesList.find { it.categoryId == categoryItemId } // WARNING !! call, to be checked afterwards
        }
    }

    override suspend fun editCategory(category: ExpenseCategory) {
        val olderCategory = getCategoryItem(category.categoryId)
        if(olderCategory != null){
            categoriesList.remove(olderCategory)
            addCategory(category)
            coroutineScope {

            }
        }
    }

    override suspend fun addCategory(category: ExpenseCategory) {
        TODO("Not yet implemented")
    }

    override fun deleteCategory(category: ExpenseCategory) {
        TODO("Not yet implemented")
    }
}