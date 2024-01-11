package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BottomSheetViewModel : ViewModel() {
    private var _timePickerState = MutableStateFlow(false)
    val timePickerStateFlow = _timePickerState.asStateFlow()


    fun togglePickerState() {
        _timePickerState.value = !_timePickerState.value
    }
}