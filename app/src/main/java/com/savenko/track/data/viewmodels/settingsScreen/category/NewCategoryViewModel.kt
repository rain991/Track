package com.savenko.track.data.viewmodels.settingsScreen.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.domain.models.abstractLayer.CategoriesTypes
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import com.savenko.track.domain.usecases.crud.categoriesRelated.CreateCategoryUseCase
import com.savenko.track.presentation.components.dialogs.newCategoryDialog.NewCategoryDialog
import com.savenko.track.presentation.other.composableTypes.errors.NewCategoryDialogErrors
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.NewCategoryDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * NewCategoryViewModel should be used with [NewCategoryDialog]
 */
class NewCategoryViewModel(
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository,
    private val expensesCategoriesListRepositoryImpl: ExpensesCategoriesListRepository
) : ViewModel() {
    private val listOfExpenseCategories = mutableListOf<ExpenseCategory>()
    private val listOfIncomeCategories = mutableListOf<IncomeCategory>()

    private val _newCategoryDialogState =
        MutableStateFlow(NewCategoryDialogState(isDialogVisible = false, dialogError = null))
    val newCategoryDialogState = _newCategoryDialogState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                incomesCategoriesListRepositoryImpl.getCategoriesList().collect {
                    setListOfIncomeCategories(it)
                }
            }
            launch {
                expensesCategoriesListRepositoryImpl.getCategoriesList().collect {
                    setListOfExpenseCategories(it)
                }
            }
        }
    }

    suspend fun addNewFinancialCategory(
        categoryName: String,
        categoryType: CategoriesTypes,
        rawCategoryColor: String
    ) {
        val processedCategoryColor = rawCategoryColor.substring(4)
        if ((categoryType is CategoriesTypes.ExpenseCategory && !listOfExpenseCategories.map { it.note }
                .contains(categoryName)) || (categoryType is CategoriesTypes.IncomeCategory && !listOfIncomeCategories.map { it.note }
                .contains(categoryName))) {

            if (_newCategoryDialogState.value.dialogError is NewCategoryDialogErrors.CategoryAlreadyExist) {
                setDialogError(null)
            }
            val category = if (categoryType is CategoriesTypes.ExpenseCategory) {
                ExpenseCategory(note = categoryName, colorId = processedCategoryColor)
            } else {
                IncomeCategory(note = categoryName, colorId = processedCategoryColor)
            }
            createCategoryUseCase(category = category)
            setDialogVisibility(false)
        } else {
            setDialogError(value = NewCategoryDialogErrors.CategoryAlreadyExist)
        }
    }

    fun setDialogVisibility(value: Boolean) {
        _newCategoryDialogState.update { _newCategoryDialogState.value.copy(isDialogVisible = value) }
    }

    private fun setListOfExpenseCategories(list: List<ExpenseCategory>) {
        listOfExpenseCategories.clear()
        listOfExpenseCategories.addAll(list)
    }

    private fun setListOfIncomeCategories(list: List<IncomeCategory>) {
        listOfIncomeCategories.clear()
        listOfIncomeCategories.addAll(list)
    }

    private fun setDialogError(value: NewCategoryDialogErrors?) {
        _newCategoryDialogState.update { _newCategoryDialogState.value.copy(dialogError = value) }
    }
}