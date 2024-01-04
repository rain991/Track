package com.example.expensetracker.data

import com.example.expensetracker.domain.CategoriesListRepository

object CategoriesListImpl : CategoriesListRepository {
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