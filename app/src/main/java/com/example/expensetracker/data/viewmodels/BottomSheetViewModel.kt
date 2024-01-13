package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.models.ExpenseCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class BottomSheetViewModel : ViewModel() {
    private var _note = MutableStateFlow("")
    val note = _note.asStateFlow()

    private var _inputExpense = MutableStateFlow(0.0f)
    val inputExpense = _inputExpense.asStateFlow()

    private var _isAcceptButtonAvailable = MutableStateFlow(false)
    val isAcceptButtonAvailable = _isAcceptButtonAvailable.asStateFlow()

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

    fun setNote(note: String) {
        _note.value = note
    }

    fun setInputExpense(inputExpense: Float) {
        _inputExpense.value = inputExpense
        checkAcceptButtonAvailable()
    }

    private fun setIsAcceptButton(value : Boolean){
        _isAcceptButtonAvailable.value=value
    }

    private fun checkAcceptButtonAvailable(){
        if(_inputExpense.value>0.5f && _categoryPicked.value!=null){
            setIsAcceptButton(true)
        }else setIsAcceptButton(false)
    }

    fun togglePickerState() {
        _timePickerState.value = !_timePickerState.value
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
        _todayButtonActiveState.value = boolean
    }

    private fun setYesterdayButtonState(boolean: Boolean) {
        _yesterdayButtonActiveState.value = boolean
    }

    fun isDateInOther(localDate: LocalDate): Boolean {
        return (localDate != LocalDate.now() && localDate != LocalDate.now().minusDays(1))
    }

}