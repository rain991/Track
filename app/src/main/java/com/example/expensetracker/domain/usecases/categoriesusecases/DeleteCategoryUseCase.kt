package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.domain.repository.CategoriesListRepository

class DeleteCategoryUseCase(private val categoriesListRepository: CategoriesListRepository) {
    suspend fun deleteCategory(category : ExpenseCategory) {
        categoriesListRepository.deleteCategory(category)
    }
}