package com.savenko.track.domain.usecases.categoriesRelated

import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository

class DeleteCategoryUseCase(
    private val categoriesListRepository: ExpensesCategoriesListRepository,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository
) {
    suspend operator fun invoke(category: CategoryEntity) {
        if (category is ExpenseCategory) {
            categoriesListRepository.deleteCategory(category = category)
        }
        if (category is IncomeCategory) {
            incomesCategoriesListRepositoryImpl.deleteCategory(category = category)
        }
    }
}