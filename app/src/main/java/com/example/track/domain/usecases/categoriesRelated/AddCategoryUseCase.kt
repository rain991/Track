package com.example.track.domain.usecases.categoriesRelated

import com.example.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.example.track.domain.models.abstractLayer.CategoryEntity
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddCategoryUseCase(
    private val categoriesListRepository: ExpensesCategoriesListRepositoryImpl,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl
) {
    suspend operator fun invoke(category: CategoryEntity) {
        withContext(Dispatchers.IO) {
            if (category is ExpenseCategory) {
                categoriesListRepository.addCategory(category = category)
            }
            if (category is IncomeCategory) {
                incomesCategoriesListRepositoryImpl.addCategory(category = category)
            }
        }
    }
}