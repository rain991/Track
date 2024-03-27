package com.example.track.data.viewmodels.common

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.constants.CURRENCY_DEFAULT
import com.example.track.data.converters.convertLocalDateToDate
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomesCategoriesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.currency.Currency
import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem
import com.example.track.data.models.other.CategoryEntity
import com.example.track.domain.usecases.expensesRelated.expenseusecases.AddExpensesItemUseCase
import com.example.track.presentation.states.common.BottomSheetViewState
import kotlinx.coroutines.CoroutineDispatcher
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
    private val addExpensesItemUseCase: AddExpensesItemUseCase,
    private val incomeListRepositoryImpl: IncomeListRepositoryImpl,
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

    private val _expenseViewState = MutableStateFlow(
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
    val expenseViewState = _expenseViewState.asStateFlow()
    init {
        viewModelScope.launch {
            categoryListRepositoryImpl.getCategoriesList().collect {
                _expenseCategoryList.clear()
                _expenseCategoryList.addAll(it)
            }
        }
        viewModelScope.launch {
            incomesCategoriesListRepositoryImpl.getCategoriesList().collect() {
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

    suspend fun addExpense(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            val currentExpenseItem = ExpenseItem(
                categoryId = expenseViewState.value.categoryPicked!!.categoryId,
                note = expenseViewState.value.note,
                date = convertLocalDateToDate(expenseViewState.value.datePicked),
                value = expenseViewState.value.inputExpense!!,
                currencyTicker = selectedCurrency.first()!!.ticker
            )
            addExpensesItemUseCase.addExpensesItem(currentExpenseItem)
            setCategoryPicked(DEFAULT_CATEGORY)
            setInputExpense(DEFAULT_EXPENSE)
            setDatePicked(DEFAULT_DATE)
            setNote(DEFAULT_NOTE)
            setBottomSheetExpanded(false)
        }
    }

    suspend fun addIncome(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            val currentIncomeItem = IncomeItem(
                categoryId = expenseViewState.value.categoryPicked!!.categoryId,
                note = expenseViewState.value.note,
                date = convertLocalDateToDate(expenseViewState.value.datePicked),
                value = expenseViewState.value.inputExpense!!,
                currencyTicker = selectedCurrency.first()!!.ticker
            )
            incomeListRepositoryImpl.addIncomeItem(currentIncomeItem)
            setCategoryPicked(DEFAULT_CATEGORY)
            setInputExpense(DEFAULT_EXPENSE)
            setDatePicked(DEFAULT_DATE)
            setNote(DEFAULT_NOTE)
            setBottomSheetExpanded(false)
        }
    }

    fun setBottomSheetExpanded(value: Boolean) {
        _expenseViewState.value = _expenseViewState.value.copy(isBottomSheetExpanded = value)
    }

    fun setNote(note: String) {
        _expenseViewState.value = _expenseViewState.value.copy(note = note)
    }

    fun setInputExpense(inputExpense: Float) {
        _expenseViewState.value = _expenseViewState.value.copy(inputExpense = inputExpense)
    }

    fun setCategoryPicked(category: CategoryEntity?) {
        _expenseViewState.value = expenseViewState.value.copy(categoryPicked = category)
    }

    fun togglePickerState() {
        _expenseViewState.value = expenseViewState.value.copy(timePickerState = !_expenseViewState.value.timePickerState)
    }

    fun toggleIsAddingExpense() {
        _expenseViewState.value = expenseViewState.value.copy(isAddingExpense = !_expenseViewState.value.isAddingExpense)
    }

    fun setDatePicked(neededDate: LocalDate) {
        _expenseViewState.update {
            expenseViewState.value.copy(
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
}

