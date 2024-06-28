package com.savenko.track.data.viewmodels.settingsScreen

import androidx.lifecycle.ViewModel
import com.savenko.track.domain.models.abstractLayer.CategoriesTypes
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.usecases.crud.categoriesRelated.AddCategoryUseCase

class NewCategoryViewModel(
    private val addCategoryUseCase: AddCategoryUseCase
) : ViewModel() {
    suspend fun addNewFinancialCategory(
        name: String,
        categoryType: CategoriesTypes,
        processedColor: String
    ) {
        // processedColor means color is ready to be saved in Room (format - 9ACD32)
        if (categoryType is CategoriesTypes.ExpenseCategory) {
            addCategoryUseCase(category = ExpenseCategory(note = name, colorId = processedColor))
        }
        if (categoryType is CategoriesTypes.IncomeCategory) {
            addCategoryUseCase(category = IncomeCategory(note = name, colorId = processedColor))
        }
    }
}