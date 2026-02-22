package com.savenko.track.shared.domain.usecases.crud.categoriesRelated

import com.savenko.track.shared.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import com.savenko.track.shared.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.shared.domain.repository.incomes.categories.IncomesCategoriesListRepository

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