package com.example.track.data.viewmodels.common

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.constants.CURRENCY_DEFAULT
import com.example.track.data.converters.convertLocalDateToDate
import com.example.track.data.implementations.CategoriesListRepositoryImpl
import com.example.track.data.implementations.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.CurrencyListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.currency.Currency
import com.example.track.domain.usecases.expenseusecases.AddExpensesItemUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

// Could be simplified by using dataClass for state
class BottomSheetViewModel(
    private val addExpensesItemUseCase: AddExpensesItemUseCase,
    private val categoryListRepositoryImpl: CategoriesListRepositoryImpl,
    private val currencyListRepositoryImpl: CurrencyListRepositoryImpl,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : ViewModel() {
    private val _categoryList = mutableStateListOf<ExpenseCategory>()
    val categoryList: List<ExpenseCategory> = _categoryList
    private val _currencyList = mutableStateListOf<Currency>()
    val currencyList: List<Currency> = _currencyList
    var prefferableCurrency = CURRENCY_DEFAULT

    init {
        viewModelScope.launch {
            categoryListRepositoryImpl.getCategoriesList().collect {
                _categoryList.clear()
                _categoryList.addAll(it)
            }
        }
        viewModelScope.launch {
            _currencyList.clear()
            _currencyList.addAll(currencyListRepositoryImpl.getCurrencyList().first())
        }
    }

    suspend fun addExpense(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        withContext(dispatcher) {
            val currentExpenseItem = ExpenseItem(
                categoryId = _categoryPicked.value!!.categoryId,
                note = _note.value,
                date = convertLocalDateToDate(_datePicked.value),
                value = _inputExpense.value!!,
                currencyTicker = prefferableCurrency.ticker
            )
            addExpensesItemUseCase.addExpensesItem(currentExpenseItem)
            setCategoryPicked(DEFAULT_CATEGORY)
            setInputExpense(DEFAULT_EXPENSE)
            setDatePicked(DEFAULT_DATE)
            setNote(DEFAULT_NOTE)
            setBottomSheetExpanded(false)
        }
    }

    companion object {
        val DEFAULT_NOTE = ""
        val DEFAULT_EXPENSE = 0.0f
        val DEFAULT_CATEGORY = null
        val DEFAULT_DATE = LocalDate.now()
    }
    private var _isBottomSheetExpanded = MutableStateFlow(value = false)
    val isBottomSheetExpanded = _isBottomSheetExpanded.asStateFlow()

    private var _note = MutableStateFlow(DEFAULT_NOTE)
    val note = _note.asStateFlow()

    private var _inputExpense = MutableStateFlow<Float?>(DEFAULT_EXPENSE)
    val inputExpense = _inputExpense.asStateFlow()

    private var _categoryPicked = MutableStateFlow<ExpenseCategory?>(DEFAULT_CATEGORY)
    val categoryPicked = _categoryPicked.asStateFlow()

    private var _timePickerState = MutableStateFlow(false)
    val timePickerState = _timePickerState.asStateFlow()

    private var _datePicked = MutableStateFlow<LocalDate>(value = LocalDate.now())
    val datePicked = _datePicked.asStateFlow()

    private var _todayButtonActiveState = MutableStateFlow(false)
    val todayButtonActiveState = _todayButtonActiveState.asStateFlow()

    private var _yesterdayButtonActiveState = MutableStateFlow(false)
    val yesterdayButtonActiveState = _yesterdayButtonActiveState.asStateFlow()

    fun setBottomSheetExpanded(value: Boolean) {
        _isBottomSheetExpanded.update { value }
    }
    fun setNote(note: String) {
        _note.update { note }
    }
    fun setInputExpense(inputExpense: Float) {
        _inputExpense.update { inputExpense }
    }
    fun setCategoryPicked(category: ExpenseCategory?) {
        if (category != null) {
            _categoryPicked.update { category }
        } else _categoryPicked.update { null }
    }
    fun togglePickerState() {
        _timePickerState.update { !_timePickerState.value }
    }
    fun setDatePicked(localDate: LocalDate) {
        _datePicked.value = localDate
        if (localDate == LocalDate.now()) {
            setTodayButtonState(true)
            setYesterdayButtonState(false)
        } else if (localDate == LocalDate.now().minusDays(1)) {
            setYesterdayButtonState(true)
            setTodayButtonState(false)
        } else {
            setTodayButtonState(false)
            setYesterdayButtonState(false)
        }
    }
    private fun setTodayButtonState(boolean: Boolean) {
        _todayButtonActiveState.update { boolean }
    }
    private fun setYesterdayButtonState(boolean: Boolean) {
        _yesterdayButtonActiveState.update { boolean }
    }
    fun isDateInOtherSpan(localDate: LocalDate): Boolean {
        return (localDate != LocalDate.now() && localDate != LocalDate.now().minusDays(1))
    }
}