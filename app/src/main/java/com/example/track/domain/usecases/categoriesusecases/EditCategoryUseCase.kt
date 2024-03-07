package com.example.track.domain.usecases.categoriesusecases

import com.example.track.data.implementations.CategoriesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory

class EditCategoryUseCase(private val categoriesListRepository: CategoriesListRepositoryImpl) {
    suspend fun editCategory(category: ExpenseCategory) {
        categoriesListRepository.editCategory(category)
    }
}