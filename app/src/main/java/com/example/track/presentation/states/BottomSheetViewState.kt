package com.example.track.presentation.states

import com.example.track.data.models.other.CategoryEntity
import java.time.LocalDate

data class BottomSheetViewState(
    val isAddingExpense : Boolean = true,
    val isBottomSheetExpanded: Boolean,
    val note: String,
    val inputExpense: Float?,
    val categoryPicked: CategoryEntity?,
    val timePickerState: Boolean,
    val datePicked: LocalDate = LocalDate.now(),
    val todayButtonActiveState: Boolean = true,
    val yesterdayButtonActiveState: Boolean = false
)