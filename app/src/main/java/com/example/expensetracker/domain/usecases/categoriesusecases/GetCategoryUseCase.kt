package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.domain.repository.CategoriesListRepository

class GetCategoryUseCase(private val categoriesListRepository: CategoriesListRepository) {
    fun getCategory(categoryId : Long): ExpenseCategory? {
        return categoriesListRepository.getCategoryItem(categoryId)
    }
}