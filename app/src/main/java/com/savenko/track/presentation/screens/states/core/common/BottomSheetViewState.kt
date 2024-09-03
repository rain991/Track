package com.savenko.track.presentation.screens.states.core.common

import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.other.composableTypes.errors.BottomSheetErrors
import java.time.LocalDate

data class BottomSheetViewState(
    val isAddingExpense : Boolean,
    val isBottomSheetExpanded: Boolean,
    val note: String,
    val inputValue: Float?,
    val currentSelectedCurrencyIndex : Int,
    val categoryPicked: CategoryEntity?,
    val timePickerState: Boolean,
    val datePicked: LocalDate?,
    val todayButtonActiveState: Boolean,
    val yesterdayButtonActiveState: Boolean,
    val warningMessage : BottomSheetErrors? = null
)