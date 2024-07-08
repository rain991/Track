package com.savenko.track.data.viewmodels.settingsScreen.additionalPreferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.implementations.expenses.expenseCategories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeCategories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.data.other.constants.GROUPING_CATEGORY_ID_DEFAULT
import com.savenko.track.data.other.constants.NON_CATEGORY_FINANCIALS_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.usecases.crud.userRelated.UpdateUserDataUseCase
import com.savenko.track.presentation.screens.states.additional.settings.additionalSettings.AdditionalSettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdditionalPreferencesSettingsViewModel(
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val dataStoreManager: DataStoreManager,
    private val expensesCategoriesListRepositoryImpl: ExpensesCategoriesListRepositoryImpl,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl
) : ViewModel() {
    private val _additionalPreferencesState =
        MutableStateFlow(
            AdditionalSettingsState(
                nonCategorisedExpenses = NON_CATEGORY_FINANCIALS_DEFAULT,
                nonCategorisedIncomes = NON_CATEGORY_FINANCIALS_DEFAULT,
                groupingExpensesCategoryId = GROUPING_CATEGORY_ID_DEFAULT,
                groupingIncomeCategoryId = GROUPING_CATEGORY_ID_DEFAULT
            )
        )
    val additionalPreferencesState = _additionalPreferencesState.asStateFlow()

    init {
        viewModelScope.launch {
            expensesCategoriesListRepositoryImpl.getCategoriesList().collect { listOfExpenseCategories ->
                setExpenseCategories(listOfExpenseCategories)
            }
        }
        viewModelScope.launch {
            incomesCategoriesListRepositoryImpl.getCategoriesList().collect { listOfIncomeCategories ->
                setIncomeCategories(listOfIncomeCategories)
            }
        }
        viewModelScope.launch {
            expensesCategoriesListRepositoryImpl.getCategoriesList().collect { listOfExpenseCategories ->
                setExpenseCategories(listOfExpenseCategories)
            }
        }
        viewModelScope.launch {
            dataStoreManager.nonCategoryExpenses.collect{nonCategoryExpenses ->
                _additionalPreferencesState.update{
                    _additionalPreferencesState.value.copy(nonCategorisedExpenses = nonCategoryExpenses)
                }
            }
        }
        viewModelScope.launch {
            dataStoreManager.nonCategoryIncomes.collect{nonCategoryIncomes ->
                _additionalPreferencesState.update{
                    _additionalPreferencesState.value.copy(nonCategorisedIncomes = nonCategoryIncomes)
                }
            }
        }
        viewModelScope.launch {
            dataStoreManager.groupingExpenseCategoryId.collect{ groupingExpenseCategoryId ->
                _additionalPreferencesState.update{
                    _additionalPreferencesState.value.copy(groupingExpensesCategoryId = groupingExpenseCategoryId)
                }
            }
        }
        viewModelScope.launch {
            dataStoreManager.groupingIncomeCategoryId.collect{ groupingIncomeCategoryId ->
                _additionalPreferencesState.update{
                    _additionalPreferencesState.value.copy(groupingIncomeCategoryId = groupingIncomeCategoryId)
                }
            }
        }
    }


    suspend fun setNonCategorisedExpenses(value: Boolean) {
        updateUserDataUseCase(key = DataStoreManager.NON_CATEGORISED_EXPENSES, value = value)
    }

    suspend fun setNonCategorisedIncomes(value: Boolean) {
        updateUserDataUseCase(key = DataStoreManager.NON_CATEGORISED_INCOMES, value = value)
    }

    suspend fun setGroupingExpensesCategoryId(value: Int) {
        updateUserDataUseCase(key = DataStoreManager.GROUPING_EXPENSES_CATEGORY_ID, value = value)
    }

    suspend fun setGroupingIncomesCategoryId(value: Int) {
        updateUserDataUseCase(key = DataStoreManager.GROUPING_INCOMES_CATEGORY_ID, value = value)
    }

    private fun setIncomeCategories(value: List<IncomeCategory>) {
        _additionalPreferencesState.value = _additionalPreferencesState.value.copy(incomeCategories = value)
    }

    private fun setExpenseCategories(value: List<ExpenseCategory>) {
        _additionalPreferencesState.value = _additionalPreferencesState.value.copy(expenseCategories = value)
    }
}