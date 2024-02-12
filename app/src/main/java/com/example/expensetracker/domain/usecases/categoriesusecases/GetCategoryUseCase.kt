package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseCategory

class GetCategoryUseCase(private val categoriesListRepository: CategoriesListRepositoryImpl) {
    fun getCategory(categoryId : Int): ExpenseCategory? {
        return categoriesListRepository.getCategoryItem(categoryId)
    }
}