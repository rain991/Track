package com.example.track.data.viewmodels.settingsScreen

import androidx.lifecycle.ViewModel
import com.example.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.example.track.domain.models.abstractLayer.CategoriesTypes
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.incomes.IncomeCategory

class NewCategoryViewModel(
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl,
    private val expensesCategoriesListRepositoryImpl: ExpensesCategoriesListRepositoryImpl
) : ViewModel() {
    suspend fun addNewFinancialCategory(
        name: String,
        categoryType: CategoriesTypes,
        processedColor: String
    ) {
        // processedColor means color is ready to be saved in Room (format - 9ACD32)
        if (categoryType is CategoriesTypes.ExpenseCategory) {
            expensesCategoriesListRepositoryImpl.addCategory(category = ExpenseCategory(note = name, colorId = processedColor))
        }
        if (categoryType is CategoriesTypes.IncomeCategory) {
            incomesCategoriesListRepositoryImpl.addCategory(category = IncomeCategory(note = name, colorId = processedColor))
        }
    }
}