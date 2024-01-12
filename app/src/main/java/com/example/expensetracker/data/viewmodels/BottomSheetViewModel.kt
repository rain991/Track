package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class BottomSheetViewModel : ViewModel() {
    private var _timePickerState = MutableStateFlow(false) // timePicker Dialog state
    val timePickerState = _timePickerState.asStateFlow()

    private var _datePicked = MutableStateFlow<LocalDate>(LocalDate.now())
    val datePicked = _datePicked.asStateFlow()

    private var _todayButtonState = MutableStateFlow(false)
    val todayButtonState = _todayButtonState.asStateFlow()

    private var _yesterdayButtonState = MutableStateFlow(false)
    val yesterdayButtonState = _yesterdayButtonState.asStateFlow()
    fun togglePickerState() {
        _timePickerState.value = !_timePickerState.value
    }

    fun setDatePicked(localDate: LocalDate) {
        _datePicked.value = localDate
        if(localDate==LocalDate.now()){
            setTodayButtonState(true)
            setYesterdayButtonState(false)
        }else if (localDate == LocalDate.now().minusDays(1)) {
            setTodayButtonState(false)
            setYesterdayButtonState(true)
        }
        else{
            setTodayButtonState(false)
            setYesterdayButtonState(false)
        }
    }

    private fun setTodayButtonState(boolean: Boolean) {
        _todayButtonState.value = boolean
    }
    private fun setYesterdayButtonState(boolean: Boolean) {
        _yesterdayButtonState.value = boolean
    }

}