package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.models.expenses.ExpenseCategory

class EditCategoryUseCase(private val categoriesListRepository: CategoriesListRepositoryImpl) {
    suspend fun editCategory(category: ExpenseCategory) {
        categoriesListRepository.editCategory(category)
    }
}