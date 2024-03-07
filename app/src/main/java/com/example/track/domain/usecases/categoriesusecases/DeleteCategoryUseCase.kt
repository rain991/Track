package com.example.track.domain.usecases.categoriesusecases

import com.example.track.data.implementations.CategoriesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory

class DeleteCategoryUseCase(private val categoriesListRepository: CategoriesListRepositoryImpl) {
    suspend fun deleteCategory(category : ExpenseCategory) {
        categoriesListRepository.deleteCategory(category)
    }
}