package com.savenko.track.presentation.components.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeConfig
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import com.savenko.track.data.other.constants.MIN_SUPPORTED_YEAR
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import java.time.LocalDate
import java.util.Date

@Composable
fun CustomDatePicker(
    isVisible: Boolean,
    isFutureTimeSelectable: Boolean = false,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Date) -> Unit,
    selectedDate: LocalDate = LocalDate.now()
) {
    val datePickerState = UseCaseState(visible = isVisible)
    DateTimeDialog(
        state = datePickerState,
        selection = DateTimeSelection.Date(selectedDate = selectedDate,
            onNegativeClick = { onNegativeClick() },
            onPositiveClick = { localDate ->
                val date = convertLocalDateToDate(localDate)
                if (isFutureTimeSelectable && date.time >= System.currentTimeMillis()) {
                    onPositiveClick(date)
                } else {
                    if (date.time <= System.currentTimeMillis()) {
                        onPositiveClick(date)
                    }
                }
            }),
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        config = DateTimeConfig(
            minYear = MIN_SUPPORTED_YEAR,
            maxYear = LocalDate.now().year - 1
        ) // Date time config adds additional year to options
    )
}
