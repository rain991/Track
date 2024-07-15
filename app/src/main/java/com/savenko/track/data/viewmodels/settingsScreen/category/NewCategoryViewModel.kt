package com.savenko.track.data.viewmodels.settingsScreen.category

import androidx.lifecycle.ViewModel
import com.savenko.track.domain.models.abstractLayer.CategoriesTypes
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.usecases.crud.categoriesRelated.CreateCategoryUseCase

class NewCategoryViewModel(
    private val createCategoryUseCase: CreateCategoryUseCase
) : ViewModel() {
    suspend fun addNewFinancialCategory(
        name: String,
        categoryType: CategoriesTypes,
        processedColor: String
    ) {
        // processedColor means color is ready to be saved in Room (format - 9ACD32)
        if (categoryType is CategoriesTypes.ExpenseCategory) {
            createCategoryUseCase(category = ExpenseCategory(note = name, colorId = processedColor))
        }
        if (categoryType is CategoriesTypes.IncomeCategory) {
            createCategoryUseCase(category = IncomeCategory(note = name, colorId = processedColor))
        }
    }
}