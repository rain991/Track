package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.models.expenses.ExpenseCategory

class DeleteCategoryUseCase(private val categoriesListRepository: CategoriesListRepositoryImpl) {
    suspend fun deleteCategory(category : ExpenseCategory) {
        categoriesListRepository.deleteCategory(category)
    }
}