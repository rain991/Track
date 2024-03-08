package com.example.track.domain.usecases.categoriesusecases

import com.example.track.data.implementations.expenses.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory

class DeleteCategoryUseCase(private val categoriesListRepository: ExpensesCategoriesListRepositoryImpl) {
    suspend fun deleteCategory(category : ExpenseCategory) {
        categoriesListRepository.deleteCategory(category)
    }
}