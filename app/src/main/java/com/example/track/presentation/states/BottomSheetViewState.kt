package com.example.track.presentation.states

import com.example.track.data.models.Expenses.ExpenseCategory
import java.time.LocalDate

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