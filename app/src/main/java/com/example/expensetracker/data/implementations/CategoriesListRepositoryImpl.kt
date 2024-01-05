package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.domain.repository.CategoriesListRepository

object CategoriesListRepositoryImpl : CategoriesListRepository {
    override suspend fun setCategoriesList(expenseCategoryDao: ExpenseCategoryDao) {
        TODO("Not yet implemented")
    }

    override fun getCategoriesList() {
        TODO("Not yet implemented")
    }

    override fun addCategorie(category: ExpenseCategory) {
        TODO("Not yet implemented")
    }

    override fun deleteCategorie(category: ExpenseCategory) {
        TODO("Not yet implemented")
    }
}