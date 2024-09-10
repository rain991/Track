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
 * CategoriesSettingsScreenViewModel works with MVI pattern : provides [screenState] for UI and receives
 *  [onAction] callback to handle user interactions
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

    init {
        viewModelScope.launch {
            initializeCategories()
        }
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
        if (category is ExpenseCategory && !LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS.contains(category.categoryId)) {
            viewModelScope.launch {
                deleteCategoryUseCase(category)
            }
        }
        if (category is IncomeCategory && !LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS.contains(category.categoryId)) {
            viewModelScope.launch {
                deleteCategoryUseCase(category)
            }
        }
    }

    private fun setFilterOnlyCustomCategories(value: Boolean) {
        _screenState.update { _screenState.value.copy(filterOnlyCustomCategories = value) }
        initializeCategories()
    }

    private fun setViewOption(value: CategoriesSettingsScreenViewOptions) {
        _screenState.update { _screenState.value.copy(viewOption = value) }
        initializeCategories()
    }

    private fun setNameFilter(value: String) {
        _screenState.update { _screenState.value.copy(nameFilter = value) }
        initializeCategories()
    }

    private fun setListOfExpensesCategories(list: List<ExpenseCategory>) {
        _screenState.update { _screenState.value.copy(listOfExpenseCategories = list) }
    }

    private fun setListOfIncomesCategories(list: List<IncomeCategory>) {
        _screenState.update { _screenState.value.copy(listOfIncomeCategories = list) }
    }

    private fun setSelectedCategory(value: CategoryEntity?) {
        _screenState.update { _screenState.value.copy(selectedCategory = value) }
    }

    private fun initializeCategories() {
        viewModelScope.launch {
            launch {
                expensesCategoriesListRepositoryImpl.getCategoriesList().collect { unfilteredExpenseCategories ->
                    val filteredExpenseCategories = unfilteredExpenseCategories.filter {
                        if (_screenState.value.filterOnlyCustomCategories) {
                            !LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS.contains(it.categoryId)
                        } else {
                            true
                        }
                    }.filter {
                        if (_screenState.value.nameFilter != "") {
                            val localizedName = databaseStringResourcesProvider.getCategoryLocalizedName(it)
                            it.note.contains(
                                _screenState.value.nameFilter,
                                ignoreCase = true
                            ) || localizedName.contains(_screenState.value.nameFilter, ignoreCase = true)
                        } else {
                            true
                        }
                    }
                    setListOfExpensesCategories(filteredExpenseCategories)
                }
            }
            launch {
                incomesCategoriesListRepositoryImpl.getCategoriesList().collect { unfilteredIncomeCategories ->
                    val filteredIncomeCategories = unfilteredIncomeCategories.filter {
                        if (_screenState.value.filterOnlyCustomCategories) {
                            !LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS.contains(it.categoryId)
                        } else {
                            true
                        }
                    }.filter {
                        if (_screenState.value.nameFilter != "") {
                            if (_screenState.value.nameFilter != "") {
                                val localizedName = databaseStringResourcesProvider.getCategoryLocalizedName(it)
                                it.note.contains(
                                    _screenState.value.nameFilter,
                                    ignoreCase = true
                                ) || localizedName.contains(_screenState.value.nameFilter, ignoreCase = true)
                            } else {
                                true
                            }
                        } else {
                            true
                        }
                    }
                    setListOfIncomesCategories(filteredIncomeCategories)
                }
            }
        }
    }
}