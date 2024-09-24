package com.savenko.track.presentation.screens.states.core.common

import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.other.composableTypes.errors.BottomSheetErrors
import java.time.LocalDate

/**
 * Bottom sheet view state
 *
 * @property isAddingExpense is BottomSheet in state of adding expense financials
 * @property isBottomSheetExpanded is BottomSheet visible
 * @property note current financial note
 * @property inputValue current financial value
 * @property currentSelectedCurrencyIndex index of selected financial currency in list of available (preferable) currencies
 * @property categoryPicked current financial category
 * @property timePickerState date and time picker visibility
 * @property datePicked current financial date picked
 * @property todayButtonActiveState is today button active
 * @property yesterdayButtonActiveState is yesterday button active
 * @property warningMessage current error message, uses [BottomSheetErrors]
 * @constructor Creates BottomSheet view state
 */
data class BottomSheetViewState(
    val isAddingExpense: Boolean,
    val isBottomSheetExpanded: Boolean,
    val note: String,
    val inputValue: Float?,
    val currentSelectedCurrencyIndex: Int,
    val categoryPicked: CategoryEntity?,
    val timePickerState: Boolean,
    val datePicked: LocalDate?,
    val todayButtonActiveState: Boolean,
    val yesterdayButtonActiveState: Boolean,
    val warningMessage: BottomSheetErrors? = null
)