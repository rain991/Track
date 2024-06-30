package com.savenko.track.domain.usecases.crud.categoriesRelated

import com.savenko.track.data.implementations.expenses.expenseCategories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeCategories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory

class DeleteCategoryUseCase(
    private val categoriesListRepository: ExpensesCategoriesListRepositoryImpl,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl
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