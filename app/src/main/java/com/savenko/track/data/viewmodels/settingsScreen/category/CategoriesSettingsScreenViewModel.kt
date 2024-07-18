package com.savenko.track.data.viewmodels.settingsScreen.category

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS
import com.savenko.track.data.other.constants.LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import com.savenko.track.domain.usecases.crud.categoriesRelated.DeleteCategoryUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CategoriesSettingsScreenViewModel(
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository,
    private val expensesCategoriesListRepositoryImpl: ExpensesCategoriesListRepository,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
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
            deleteCategoryUseCase(category)
        }
        if (category is IncomeCategory && !LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS.contains(category.categoryId)) {
            deleteCategoryUseCase(category)
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