package com.savenko.track.data.viewmodels.common

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.EXPENSE_CATEGORY_GROUPING_ID_DEFAULT
import com.savenko.track.data.other.constants.GROUPING_CATEGORY_ID_DEFAULT
import com.savenko.track.data.other.constants.INCOME_CATEGORY_GROUPING_ID_DEFAULT
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import com.savenko.track.domain.usecases.crud.expenseRelated.AddExpenseItemUseCase
import com.savenko.track.domain.usecases.crud.incomeRelated.AddIncomeItemUseCase
import com.savenko.track.presentation.other.composableTypes.errors.BottomSheetErrors
import com.savenko.track.presentation.screens.states.core.common.BottomSheetViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

/**
 * Provides state for [BottomSheet](com.savenko.track.presentation.components.bottomSheet.BottomSheet)
 */
class BottomSheetViewModel(
    private val addExpenseItemUseCase: AddExpenseItemUseCase,
    private val addIncomeItemUseCase: AddIncomeItemUseCase,
    private val categoryListRepositoryImpl: ExpensesCategoriesListRepository,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _expenseCategoryList = mutableStateListOf<ExpenseCategory>()
    val expenseCategoryList: List<ExpenseCategory> = _expenseCategoryList

    private val _incomeCategoryList = mutableStateListOf<IncomeCategory>()
    val incomeCategoryList = _incomeCategoryList

    private val _listOfPreferableCurrencies = mutableStateListOf<Currency>()
    val listOfPreferableCurrencies: List<Currency> = _listOfPreferableCurrencies

    private val _bottomSheetViewState = MutableStateFlow(
        BottomSheetViewState(
            isAddingExpense = true,
            isBottomSheetExpanded = false,
            note = DEFAULT_NOTE,
            inputValue = DEFAULT_INPUT_VALUE,
            currentSelectedCurrencyIndex = 0,
            categoryPicked = DEFAULT_CATEGORY,
            timePickerState = false,
            datePicked = null,
            todayButtonActiveState = false,
            yesterdayButtonActiveState = false
        )
    )
    val bottomSheetViewState = _bottomSheetViewState.asStateFlow()

    init {
        viewModelScope.launch {
            categoryListRepositoryImpl.getCategoriesList().collect {
                _expenseCategoryList.clear()
                _expenseCategoryList.addAll(it)
            }
        }
        viewModelScope.launch {
            incomesCategoriesListRepositoryImpl.getCategoriesList().collect {
                _incomeCategoryList.clear()
                _incomeCategoryList.addAll(it)
            }
        }
        viewModelScope.launch {
            currenciesPreferenceRepositoryImpl.getCurrenciesPreferenceConverted()
                .collect { currenciesPreference ->
                    _listOfPreferableCurrencies.clear()
                    val listOfNonNullCurrenciesNames = listOfNotNull(
                        currenciesPreference.preferableCurrency,
                        currenciesPreference.firstAdditionalCurrency,
                        currenciesPreference.secondAdditionalCurrency,
                        currenciesPreference.thirdAdditionalCurrency,
                        currenciesPreference.fourthAdditionalCurrency
                    )
                    _listOfPreferableCurrencies.addAll(listOfNonNullCurrenciesNames)
                }
        }
    }

    companion object {
        const val DEFAULT_NOTE = ""
        const val DEFAULT_INPUT_VALUE = 0.0f
        val DEFAULT_CATEGORY = null
        val DEFAULT_DATE = null
    }

    /**
     * Add financial item
     */
    suspend fun addFinancialItem() {
        val nonCategorisedExpenses = dataStoreManager.nonCategoryExpenses.first()
        val nonCategorisedIncomes = dataStoreManager.nonCategoryIncomes.first()
        val groupingExpenseCategoryId = dataStoreManager.groupingExpenseCategoryId.first()
        val groupingIncomeCategoryId = dataStoreManager.groupingIncomeCategoryId.first()
        val selectedCurrency =
            listOfPreferableCurrencies[_bottomSheetViewState.value.currentSelectedCurrencyIndex]
        handleWarnings(
            nonCategorisedExpenses = nonCategorisedExpenses,
            nonCategorisedIncomes = nonCategorisedIncomes,
            groupingExpenseCategoryId = groupingExpenseCategoryId,
            groupingIncomeCategoryId = groupingIncomeCategoryId
        )
        withContext(Dispatchers.IO) {
            if (bottomSheetViewState.value.isAddingExpense) {
                handleAddingExpenses(
                    nonCategorisedExpenses = nonCategorisedExpenses,
                    groupingExpenseCategoryId = groupingExpenseCategoryId,
                    selectedCurrency = selectedCurrency
                )
            } else {
                handleAddingIncomes(
                    nonCategorisedIncomes = nonCategorisedIncomes,
                    groupingIncomeCategoryId = groupingIncomeCategoryId,
                    selectedCurrency = selectedCurrency
                )
            }
        }

        setCategoryPicked(DEFAULT_CATEGORY)
        setInputValue(DEFAULT_INPUT_VALUE)
        setDatePicked(DEFAULT_DATE)
        setNote(DEFAULT_NOTE)
        setWarningMessage(null)
        setBottomSheetExpanded(false)
    }

    /**
     * Set bottom sheet expanded
     *
     * @param value
     */
    fun setBottomSheetExpanded(value: Boolean) {
        _bottomSheetViewState.value =
            _bottomSheetViewState.value.copy(isBottomSheetExpanded = value)
    }

    /**
     * Set input value
     *
     * @param inputValue
     */
    fun setInputValue(inputValue: Float) {
        _bottomSheetViewState.value = _bottomSheetViewState.value.copy(inputValue = inputValue)
        if (_bottomSheetViewState.value.warningMessage is BottomSheetErrors.IncorrectInputValue && inputValue > 0) {
            setWarningMessage(null)
        }
    }

    /**
     * Set note
     *
     * @param note
     */
    fun setNote(note: String) {
        _bottomSheetViewState.value = _bottomSheetViewState.value.copy(note = note)
    }

    /**
     * Set category picked
     *
     * @param category
     */
    fun setCategoryPicked(category: CategoryEntity?) {
        if (_bottomSheetViewState.value.categoryPicked != category) {
            _bottomSheetViewState.value = bottomSheetViewState.value.copy(categoryPicked = category)
        } else {
            _bottomSheetViewState.value = bottomSheetViewState.value.copy(categoryPicked = null)
        }
        if (category != null && _bottomSheetViewState.value.warningMessage is BottomSheetErrors.CategoryNotSelected) {
            setWarningMessage(null)
        }
    }

    /**
     * Toggle picker state
     *
     */
    fun togglePickerState() {
        _bottomSheetViewState.value =
            bottomSheetViewState.value.copy(timePickerState = !_bottomSheetViewState.value.timePickerState)
    }

    /**
     * Toggle is adding expense
     *
     */
    fun toggleIsAddingExpense() {
        _bottomSheetViewState.value =
            bottomSheetViewState.value.copy(isAddingExpense = !_bottomSheetViewState.value.isAddingExpense)
    }

    /**
     * Set is adding expense
     *
     * @param value
     */
    fun setIsAddingExpense(value: Boolean) {
        _bottomSheetViewState.value =
            bottomSheetViewState.value.copy(isAddingExpense = value)
    }

    /**
     * Set date picked
     *
     * @param date
     */
    fun setDatePicked(date: LocalDate?) {
        _bottomSheetViewState.update {
            bottomSheetViewState.value.copy(
                datePicked = date,
                todayButtonActiveState = (date == LocalDate.now()),
                yesterdayButtonActiveState = (date == (LocalDate.now().minusDays(1)))
            )
        }
    }

    fun isDateInOtherSpan(localDate: LocalDate): Boolean {
        return (localDate != LocalDate.now() && localDate != LocalDate.now().minusDays(1))
    }

    fun changeSelectedCurrency() {
        val selectedCurrencyIndex = _bottomSheetViewState.value.currentSelectedCurrencyIndex
        for (i in (selectedCurrencyIndex + 1) until listOfPreferableCurrencies.size) {
            setSelectedCurrency(i)
            return
        }
        for (i in 0 until selectedCurrencyIndex) {
            setSelectedCurrency(i)
            return
        }
    }

    private suspend fun handleAddingExpenses(
        nonCategorisedExpenses: Boolean,
        groupingExpenseCategoryId: Int,
        selectedCurrency: Currency
    ) {
        val expenseCategoryPickedId =
            if (nonCategorisedExpenses && bottomSheetViewState.value.categoryPicked == null && groupingExpenseCategoryId != GROUPING_CATEGORY_ID_DEFAULT) {
                groupingExpenseCategoryId
            } else {
                EXPENSE_CATEGORY_GROUPING_ID_DEFAULT
            }
        val currentExpenseItem = ExpenseItem(
            categoryId = if (bottomSheetViewState.value.categoryPicked != null) {
                bottomSheetViewState.value.categoryPicked!!.categoryId
            } else {
                expenseCategoryPickedId
            },
            note = bottomSheetViewState.value.note,
            date = convertLocalDateToDate(bottomSheetViewState.value.datePicked!!),
            value = bottomSheetViewState.value.inputValue!!,
            currencyTicker = selectedCurrency.ticker
        )
        addExpenseItemUseCase(currentExpenseItem)
    }

    private suspend fun handleAddingIncomes(
        nonCategorisedIncomes: Boolean,
        groupingIncomeCategoryId: Int,
        selectedCurrency: Currency
    ) {
        val incomeCategoryPickedId =
            if (nonCategorisedIncomes && bottomSheetViewState.value.categoryPicked == null && groupingIncomeCategoryId != GROUPING_CATEGORY_ID_DEFAULT) {
                groupingIncomeCategoryId
            } else {
                INCOME_CATEGORY_GROUPING_ID_DEFAULT
            }
        val currentIncomeItem = IncomeItem(
            categoryId = if (bottomSheetViewState.value.categoryPicked != null) {
                bottomSheetViewState.value.categoryPicked!!.categoryId
            } else {
                incomeCategoryPickedId
            },
            note = bottomSheetViewState.value.note,
            date = convertLocalDateToDate(bottomSheetViewState.value.datePicked!!),
            value = bottomSheetViewState.value.inputValue!!,
            currencyTicker = selectedCurrency.ticker
        )
        addIncomeItemUseCase(currentIncomeItem)
    }

    private fun setSelectedCurrency(index: Int) {
        _bottomSheetViewState.value =
            _bottomSheetViewState.value.copy(currentSelectedCurrencyIndex = index)
    }

    private fun setWarningMessage(message: BottomSheetErrors?) {
        _bottomSheetViewState.value = _bottomSheetViewState.value.copy(warningMessage = message)
    }

    private fun handleWarnings(
        nonCategorisedExpenses: Boolean,
        nonCategorisedIncomes: Boolean,
        groupingExpenseCategoryId: Int,
        groupingIncomeCategoryId: Int
    ) {
        if (bottomSheetViewState.value.inputValue == null || bottomSheetViewState.value.inputValue == 0.0f) {
            setWarningMessage(BottomSheetErrors.IncorrectInputValue)
            return
        }
        if (nonCategorisedExpenses && bottomSheetViewState.value.isAddingExpense && (bottomSheetViewState.value.categoryPicked == null && groupingExpenseCategoryId == GROUPING_CATEGORY_ID_DEFAULT)) {
            setWarningMessage(BottomSheetErrors.ExpenseGroupingCategoryIsNotSelected)
            return
        }
        if (nonCategorisedIncomes && !bottomSheetViewState.value.isAddingExpense && bottomSheetViewState.value.categoryPicked == null && groupingIncomeCategoryId == GROUPING_CATEGORY_ID_DEFAULT) {
            setWarningMessage(BottomSheetErrors.IncomeGroupingCategoryIsNotSelected)
            return
        }
        if (bottomSheetViewState.value.categoryPicked == null && ((bottomSheetViewState.value.isAddingExpense && !nonCategorisedExpenses) || (!bottomSheetViewState.value.isAddingExpense && !nonCategorisedIncomes))) {
            setWarningMessage(BottomSheetErrors.CategoryNotSelected)
            return
        }

        if (bottomSheetViewState.value.datePicked == null) {
            setWarningMessage(BottomSheetErrors.DateNotSelected)
            return
        }
    }
}