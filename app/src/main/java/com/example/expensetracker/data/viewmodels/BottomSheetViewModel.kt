package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.models.ExpenseCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import java.time.LocalDate
// private val categoryList: GetCategoryListUseCase, private val addExpensesItemUseCase: AddExpensesItemUseCase
class BottomSheetViewModel(private val screenViewModel: ScreenViewModel) :
    ViewModel() {


    private var _note = MutableStateFlow("")
    val note = _note.asStateFlow()

    private var _inputExpense = MutableStateFlow<Float?>(0.0f)
    val inputExpense = _inputExpense.asStateFlow()

    private var _categoryPicked = MutableStateFlow<ExpenseCategory?>(null)
    val categoryPicked = _categoryPicked.asStateFlow()

    private var _timePickerState = MutableStateFlow(false) // timePicker Dialog state
    val timePickerState = _timePickerState.asStateFlow()

    private var _datePicked = MutableStateFlow<LocalDate>(value = LocalDate.now())
    val datePicked = _datePicked.asStateFlow()

    private var _todayButtonActiveState = MutableStateFlow(false)
    val todayButtonActiveState = _todayButtonActiveState.asStateFlow()

    private var _yesterdayButtonActiveState = MutableStateFlow(false)
    val yesterdayButtonActiveState = _yesterdayButtonActiveState.asStateFlow()

    val isAcceptButtonAvailable = combine(_inputExpense, _datePicked, _categoryPicked) { _inputExpense, _datePicked, _categoryPicked ->
        _inputExpense != null && _inputExpense > 0.5  && _datePicked.isBefore(LocalDate.now().plusDays(1)) && _categoryPicked!=null
    }

    fun setNote(note: String) {
        _note.update {note}
    }

    fun setInputExpense(inputExpense: Float) {
        _inputExpense.update{ inputExpense}
    }

    fun setCategoryPicked(category: ExpenseCategory?) {
        if (category != null) {
            _categoryPicked.update { category }
        } else _categoryPicked.update { null }
    }

    fun togglePickerState() {
        _timePickerState.update{!_timePickerState.value }
    }

    fun setDatePicked(localDate: LocalDate) {
        _datePicked.value = localDate
        if (localDate == LocalDate.now()) {
            setTodayButtonState(true)
            setYesterdayButtonState(false)
        } else if (localDate == LocalDate.now().minusDays(1)) {
            setTodayButtonState(false)
            setYesterdayButtonState(true)
        } else {
            setTodayButtonState(false)
            setYesterdayButtonState(false)
        }
    }

    private fun setTodayButtonState(boolean: Boolean) {
        _todayButtonActiveState.update{boolean}
    }

    private fun setYesterdayButtonState(boolean: Boolean) {
        _yesterdayButtonActiveState.update{boolean}
    }

    fun isDateInOther(localDate: LocalDate): Boolean {
        return (localDate != LocalDate.now() && localDate != LocalDate.now().minusDays(1))
    }

}