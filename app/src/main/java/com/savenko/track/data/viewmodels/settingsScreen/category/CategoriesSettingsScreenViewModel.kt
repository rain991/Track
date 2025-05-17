package com.savenko.track.data.viewmodels.settingsScreen.category

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
import com.savenko.track.presentation.other.uiText.DatabaseStringResourcesProvider
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenEvent
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenState
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenViewOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * CategoriesSettingsScreenViewModel works with MVI pattern :
 *
 * provides [screenState] for UI
 *
 * receives [onAction] callback to handle user interactions
 */
class CategoriesSettingsScreenViewModel(
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository,
    private val expensesCategoriesListRepositoryImpl: ExpensesCategoriesListRepository,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val databaseStringResourcesProvider: DatabaseStringResourcesProvider
) : ViewModel() {
    private val _screenState = MutableStateFlow(
        CategoriesSettingsScreenState(
            viewOption = CategoriesSettingsScreenViewOptions.CardsView,
            filterOnlyCustomCategories = false,
            nameFilter = "",
            selectedCategory = null,
            listOfExpenseCategories = listOf(),
            listOfIncomeCategories = listOf()
        )
    )
    val screenState = _screenState.asStateFlow()

    private val defaultExpenseCategoryIds = LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS
    private val defaultIncomeCategoryIds = LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS

    init {
        initializeCategories()
    }

    fun onAction(action: CategoriesSettingsScreenEvent) {
        when (action) {
            is CategoriesSettingsScreenEvent.SetFilterOnlyCustomCategories -> {
                setFilterOnlyCustomCategories(action.value)
            }

            is CategoriesSettingsScreenEvent.SetFilteringText -> {
                setNameFilter(action.value)
            }

            is CategoriesSettingsScreenEvent.SetViewOption -> {
                setViewOption(action.viewOption)
            }

            is CategoriesSettingsScreenEvent.DeleteCategory -> {
                deleteCategory(action.category)
            }

            is CategoriesSettingsScreenEvent.SetSelectedCategory -> {
                setSelectedCategory(action.category)
            }
        }
    }

    private fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            val canDelete = when (category) {
                is ExpenseCategory -> !defaultExpenseCategoryIds.contains(category.categoryId)
                is IncomeCategory -> !defaultIncomeCategoryIds.contains(category.categoryId)
                else -> false
            }

            if (canDelete) {
                deleteCategoryUseCase(category)
            }
        }
    }

    private fun setFilterOnlyCustomCategories(value: Boolean) {
        _screenState.update { it.copy(filterOnlyCustomCategories = value) }
        initializeCategories()
    }

    private fun setViewOption(value: CategoriesSettingsScreenViewOptions) {
        _screenState.update { it.copy(viewOption = value) }
        initializeCategories()
    }

    private fun setNameFilter(value: String) {
        _screenState.update { it.copy(nameFilter = value) }
        initializeCategories()
    }

    private fun setListOfExpensesCategories(list: List<ExpenseCategory>) {
        _screenState.update { it.copy(listOfExpenseCategories = list) }
    }

    private fun setListOfIncomesCategories(list: List<IncomeCategory>) {
        _screenState.update { it.copy(listOfIncomeCategories = list) }
    }

    private fun setSelectedCategory(value: CategoryEntity?) {
        _screenState.update { it.copy(selectedCategory = value) }
    }

    private fun initializeCategories() {
        viewModelScope.launch {
            initializeExpenseCategories()
        }
        viewModelScope.launch {
            initializeIncomeCategories()
        }
    }

    private suspend fun initializeExpenseCategories() {
        val currentState = _screenState.value
        expensesCategoriesListRepositoryImpl.getCategoriesList()
            .collect { unfilteredExpenseCategories ->
                val filteredExpenseCategories = unfilteredExpenseCategories
                    .filter {
                        !currentState.filterOnlyCustomCategories ||
                                !defaultExpenseCategoryIds.contains(it.categoryId)
                    }
                    .filter {
                        if (currentState.nameFilter.isNotBlank()) {
                            val localizedName =
                                databaseStringResourcesProvider.getCategoryLocalizedName(it)
                            it.note.contains(currentState.nameFilter, ignoreCase = true) ||
                                    localizedName.contains(
                                        currentState.nameFilter,
                                        ignoreCase = true
                                    )
                        } else true
                    }
                setListOfExpensesCategories(filteredExpenseCategories)
            }
    }

    private suspend fun initializeIncomeCategories() {
        val currentState = _screenState.value
        incomesCategoriesListRepositoryImpl.getCategoriesList()
            .collect { unfilteredIncomeCategories ->
                val filteredIncomeCategories = unfilteredIncomeCategories
                    .filter {
                        !currentState.filterOnlyCustomCategories ||
                                !defaultIncomeCategoryIds.contains(it.categoryId)
                    }
                    .filter {
                        if (currentState.nameFilter.isNotBlank()) {
                            val localizedName =
                                databaseStringResourcesProvider.getCategoryLocalizedName(it)
                            it.note.contains(currentState.nameFilter, ignoreCase = true) ||
                                    localizedName.contains(
                                        currentState.nameFilter,
                                        ignoreCase = true
                                    )
                        } else true
                    }
                setListOfIncomesCategories(filteredIncomeCategories)
            }
    }
}
