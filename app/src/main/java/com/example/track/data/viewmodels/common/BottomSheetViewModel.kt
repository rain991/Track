package com.example.track.data.viewmodels.common

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.constants.CURRENCY_DEFAULT
import com.example.track.data.converters.convertLocalDateToDate
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomesCategoriesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.domain.usecases.expenseusecases.AddExpensesItemUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

// Could be simplified by using dataClass for state
class BottomSheetViewModel(
    private val addExpensesItemUseCase: AddExpensesItemUseCase,
    private val categoryListRepositoryImpl: ExpensesCategoriesListRepositoryImpl,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : ViewModel() {
    private val _expenseCategoryList = mutableStateListOf<ExpenseCategory>()
    val expenseCategoryList: List<ExpenseCategory> = _expenseCategoryList
    private val _incomeCategoryList = mutableStateListOf<IncomeCategory>()
    val incomeCategoryList = _incomeCategoryList

    val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = CURRENCY_DEFAULT)
    val firstAdditionalCurrency = currenciesPreferenceRepositoryImpl.getFirstAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = null)
    val secondAdditionalCurrency = currenciesPreferenceRepositoryImpl.getSecondAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = null)
    val thirdAdditionalCurrency = currenciesPreferenceRepositoryImpl.getThirdAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = null)
    val fourthAdditionalCurrency = currenciesPreferenceRepositoryImpl.getFourthAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = null)
    private val _selectedCurrency = MutableStateFlow(value = preferableCurrency.value)
    val selectedCurrency = _selectedCurrency.asStateFlow()
    private val _expenseViewState = MutableStateFlow(
        BottomSheetViewState(
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
                currencyTicker = preferableCurrency.value!!.ticker
            )
            Log.d("MyLog", "addExpense: ${preferableCurrency.value!!.ticker}")
            addExpensesItemUseCase.addExpensesItem(currentExpenseItem)
            setCategoryPicked(DEFAULT_CATEGORY)
            setInputExpense(DEFAULT_EXPENSE)
            setDatePicked(DEFAULT_DATE)
            setNote(DEFAULT_NOTE)
            setBottomSheetExpanded(false)
        }
    }


//    private var _isBottomSheetExpanded = MutableStateFlow(value = false)
//    val isBottomSheetExpanded = _isBottomSheetExpanded.asStateFlow()
//
//    private var _note = MutableStateFlow(DEFAULT_NOTE)
//    val note = _note.asStateFlow()
//
//    private var _inputExpense = MutableStateFlow<Float?>(DEFAULT_EXPENSE)
//    val inputExpense = _inputExpense.asStateFlow()
//
//    private var _categoryPicked = MutableStateFlow<ExpenseCategory?>(DEFAULT_CATEGORY)
//    val categoryPicked = _categoryPicked.asStateFlow()
//
//    private var _timePickerState = MutableStateFlow(false)
//    val timePickerState = _timePickerState.asStateFlow()
//
//    private var _datePicked = MutableStateFlow<LocalDate>(value = LocalDate.now())
//    val datePicked = _datePicked.asStateFlow()
//
//    private var _todayButtonActiveState = MutableStateFlow(false)
//    val todayButtonActiveState = _todayButtonActiveState.asStateFlow()
//
//    private var _yesterdayButtonActiveState = MutableStateFlow(false)
//    val yesterdayButtonActiveState = _yesterdayButtonActiveState.asStateFlow()
    fun setBottomSheetExpanded(value: Boolean) {
        // _isBottomSheetExpanded.update { value }
        _expenseViewState.value = _expenseViewState.value.copy(isBottomSheetExpanded = value)
    }

    fun setNote(note: String) {
        //  _note.update { note }
        _expenseViewState.value = _expenseViewState.value.copy(note = note)
    }

    fun setInputExpense(inputExpense: Float) {
        //  _inputExpense.update { inputExpense }
        _expenseViewState.value = _expenseViewState.value.copy(inputExpense = inputExpense)
    }

    fun setCategoryPicked(category: ExpenseCategory?) {
//        if (category != null) {
//            _categoryPicked.update { category }
//        } else _categoryPicked.update { null }
        _expenseViewState.value = expenseViewState.value.copy(categoryPicked = category)
    }

    fun togglePickerState() {
        // _timePickerState.update { !_timePickerState.value }
        _expenseViewState.value = expenseViewState.value.copy(timePickerState = !_expenseViewState.value.timePickerState)
    }

    fun setDatePicked(neededDate: LocalDate) {
        // _datePicked.value = localDate
        _expenseViewState.update{expenseViewState.value.copy(
            datePicked = neededDate,
            todayButtonActiveState = (neededDate == LocalDate.now()),
            yesterdayButtonActiveState = (neededDate == (LocalDate.now().minusDays(1)))
        )}
//        when (localDate) {
//            LocalDate.now() -> {
//                setTodayButtonState(true)
//                setYesterdayButtonState(false)
//            }
//            LocalDate.now().minusDays(1) -> {
//                setYesterdayButtonState(true)
//                setTodayButtonState(false)
//            }
//            else -> {
//                setTodayButtonState(false)
//                setYesterdayButtonState(false)
//            }
//        }
        Log.d("MyLog", "setDatePicked:${_expenseViewState.value} ")
    }

    fun isDateInOtherSpan(localDate: LocalDate): Boolean {
        return (localDate != LocalDate.now() && localDate != LocalDate.now().minusDays(1))
    }

    private fun setTodayButtonState(boolean: Boolean) {
        // _todayButtonActiveState.update { boolean }
        _expenseViewState.value = expenseViewState.value.copy(todayButtonActiveState = boolean)
    }

    private fun setYesterdayButtonState(boolean: Boolean) {
        _expenseViewState.value = expenseViewState.value.copy(yesterdayButtonActiveState = boolean)
    }
}

data class BottomSheetViewState(
    val isBottomSheetExpanded: Boolean,
    val note: String,
    val inputExpense: Float?,
    val categoryPicked: ExpenseCategory?,
    val timePickerState: Boolean,
    val datePicked: LocalDate = LocalDate.now(),
    val todayButtonActiveState: Boolean = true,
    val yesterdayButtonActiveState: Boolean = false
)