package com.savenko.track.data.viewmodels.common

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.implementations.expenses.expenseCategories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeCategories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.usecases.crud.expenseRelated.AddExpenseItemUseCase
import com.savenko.track.domain.usecases.crud.incomeRelated.AddIncomeItemUseCase
import com.savenko.track.presentation.other.composableTypes.errors.BottomSheetErrors
import com.savenko.track.presentation.screens.states.core.common.BottomSheetViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class BottomSheetViewModel(
    private val addExpenseItemUseCase: AddExpenseItemUseCase,
    private val addIncomeItemUseCase: AddIncomeItemUseCase,
    private val categoryListRepositoryImpl: ExpensesCategoriesListRepositoryImpl,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : ViewModel() {
    private val _expenseCategoryList = mutableStateListOf<ExpenseCategory>()
    val expenseCategoryList: List<ExpenseCategory> = _expenseCategoryList
    private val _incomeCategoryList = mutableStateListOf<IncomeCategory>()
    val incomeCategoryList = _incomeCategoryList

    private val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = CURRENCY_DEFAULT)
    private val firstAdditionalCurrency = currenciesPreferenceRepositoryImpl.getFirstAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
    private val secondAdditionalCurrency = currenciesPreferenceRepositoryImpl.getSecondAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
    private val thirdAdditionalCurrency = currenciesPreferenceRepositoryImpl.getThirdAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
    private val fourthAdditionalCurrency = currenciesPreferenceRepositoryImpl.getFourthAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
    private val listOfCurrencies = listOf(
        preferableCurrency,
        firstAdditionalCurrency,
        secondAdditionalCurrency,
        thirdAdditionalCurrency,
        fourthAdditionalCurrency
    )
    private val _selectedCurrencyIndex = MutableStateFlow(value = 0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedCurrency: Flow<Currency?> = _selectedCurrencyIndex.flatMapLatest { index ->
        combine(listOfCurrencies.map { it }) { currencies ->
            currencies.getOrNull(index)
        }
    }.distinctUntilChanged()

    private val _bottomSheetViewState = MutableStateFlow(
        BottomSheetViewState(
            isAddingExpense = true,
            isBottomSheetExpanded = false,
            note = DEFAULT_NOTE,
            inputExpense = DEFAULT_EXPENSE,
            categoryPicked = DEFAULT_CATEGORY,
            timePickerState = false,
            datePicked = LocalDate.now(),
            todayButtonActiveState = true,
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
    }

    companion object {
        val DEFAULT_NOTE = ""
        val DEFAULT_EXPENSE = 0.0f
        val DEFAULT_CATEGORY = null
        val DEFAULT_DATE = LocalDate.now()
    }

    suspend fun addFinancialItem() {
        if (bottomSheetViewState.value.inputExpense == null || bottomSheetViewState.value.inputExpense!! == 0.0f) {
            setWarningMessage(BottomSheetErrors.IncorrectInputValue)
            return
        }
        if (bottomSheetViewState.value.categoryPicked == null) {
            setWarningMessage(BottomSheetErrors.CategoryNotSelected)
            return
        }
        if (bottomSheetViewState.value.isAddingExpense) {
            withContext(Dispatchers.IO) {
                val currentExpenseItem = ExpenseItem(
                    categoryId = bottomSheetViewState.value.categoryPicked!!.categoryId,
                    note = bottomSheetViewState.value.note,
                    date = convertLocalDateToDate(bottomSheetViewState.value.datePicked),
                    value = bottomSheetViewState.value.inputExpense!!,
                    currencyTicker = selectedCurrency.first()!!.ticker
                )
                addExpenseItemUseCase(currentExpenseItem)
            }
        } else {
            val currentIncomeItem = IncomeItem(
                categoryId = bottomSheetViewState.value.categoryPicked!!.categoryId,
                note = bottomSheetViewState.value.note,
                date = convertLocalDateToDate(bottomSheetViewState.value.datePicked),
                value = bottomSheetViewState.value.inputExpense!!,
                currencyTicker = selectedCurrency.first()!!.ticker
            )
            addIncomeItemUseCase(currentIncomeItem)
        }
        setCategoryPicked(DEFAULT_CATEGORY)
        setInputExpense(DEFAULT_EXPENSE)
        setDatePicked(DEFAULT_DATE)
        setNote(DEFAULT_NOTE)
        setWarningMessage(null)
        setBottomSheetExpanded(false)
    }

    fun setBottomSheetExpanded(value: Boolean) {
        _bottomSheetViewState.value = _bottomSheetViewState.value.copy(isBottomSheetExpanded = value)
    }

    fun setNote(note: String) {
        _bottomSheetViewState.value = _bottomSheetViewState.value.copy(note = note)
    }

    fun setInputExpense(inputExpense: Float) {
        _bottomSheetViewState.value = _bottomSheetViewState.value.copy(inputExpense = inputExpense)
    }

    fun setCategoryPicked(category: CategoryEntity?) {
        if(_bottomSheetViewState.value.categoryPicked != category){
            _bottomSheetViewState.value = bottomSheetViewState.value.copy(categoryPicked = category)
        }else{
            _bottomSheetViewState.value = bottomSheetViewState.value.copy(categoryPicked = null)
        }
    }

    fun togglePickerState() {
        _bottomSheetViewState.value =
            bottomSheetViewState.value.copy(timePickerState = !_bottomSheetViewState.value.timePickerState)
    }

    fun toggleIsAddingExpense() {
        _bottomSheetViewState.value =
            bottomSheetViewState.value.copy(isAddingExpense = !_bottomSheetViewState.value.isAddingExpense)
    }

    fun setDatePicked(neededDate: LocalDate) {
        _bottomSheetViewState.update {
            bottomSheetViewState.value.copy(
                datePicked = neededDate,
                todayButtonActiveState = (neededDate == LocalDate.now()),
                yesterdayButtonActiveState = (neededDate == (LocalDate.now().minusDays(1)))
            )
        }
    }

    fun isDateInOtherSpan(localDate: LocalDate): Boolean {
        return (localDate != LocalDate.now() && localDate != LocalDate.now().minusDays(1))
    }

    fun changeSelectedCurrency() {
        val listOfCurrenciesValues = listOfCurrencies.map { it.value }
        val selectedCurrencyIndex = _selectedCurrencyIndex.value
        for (i in (selectedCurrencyIndex + 1) until listOfCurrencies.size) {
            if (listOfCurrenciesValues[i] != null) {
                setSelectedCurrency(i)
                return
            }
        }
        for (i in 0 until selectedCurrencyIndex) {
            if (listOfCurrenciesValues[i] != null) {
                setSelectedCurrency(i)
                return
            }
        }
    }

    private fun setSelectedCurrency(index: Int) {
        _selectedCurrencyIndex.value = index
    }

    private fun setWarningMessage(message: BottomSheetErrors?) {
        _bottomSheetViewState.value = _bottomSheetViewState.value.copy(warningMessage = message)
    }
}

