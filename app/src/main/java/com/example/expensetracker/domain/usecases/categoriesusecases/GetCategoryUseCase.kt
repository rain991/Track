package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.models.ExpenseCategory

class GetCategoryUseCase(private val categoriesListRepository: CategoriesListRepositoryImpl) {
    fun getCategory(categoryId : Long): ExpenseCategory? {
        return categoriesListRepository.getCategoryItem(categoryId)
    }
}