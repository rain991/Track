package com.example.track.data.viewmodels.settingsScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.example.track.data.other.constants.LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS
import com.example.track.data.other.constants.LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS
import com.example.track.domain.models.abstractLayer.CategoryEntity
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CategoriesSettingsScreenViewModel(
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl,
    private val expensesCategoriesListRepositoryImpl: ExpensesCategoriesListRepositoryImpl
) : ViewModel() {
    private val _listOfExpensesCategories = mutableStateListOf<ExpenseCategory>()
    val listOfExpensesCategories: List<ExpenseCategory> = _listOfExpensesCategories

    private val _listOfIncomesCategories = mutableStateListOf<IncomeCategory>()
    val listOfIncomesCategories: List<IncomeCategory> = _listOfIncomesCategories

    init {
        viewModelScope.launch {
            async {
                incomesCategoriesListRepositoryImpl.getCategoriesList().collect {
                    setListOfIncomesCategories(it)
                }
            }
            async {
                expensesCategoriesListRepositoryImpl.getCategoriesList().collect {
                    setListOfExpensesCategories(it)
                }
            }
        }
    }

    suspend fun deleteCategory(category: CategoryEntity) {
        if (category is ExpenseCategory && !LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS.contains(category.categoryId)) {
            expensesCategoriesListRepositoryImpl.deleteCategory(category)
        }
        if (category is IncomeCategory && !LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS.contains(category.categoryId)) {
            incomesCategoriesListRepositoryImpl.deleteCategory(category)
        }
    }
    private fun setListOfExpensesCategories(list: List<ExpenseCategory>) {
        _listOfExpensesCategories.clear()
        _listOfExpensesCategories.addAll(list)
    }

    private fun setListOfIncomesCategories(list: List<IncomeCategory>) {
        _listOfIncomesCategories.clear()
        _listOfIncomesCategories.addAll(list)
    }
}