package com.example.track.presentation.components.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import com.example.track.data.other.constants.MIN_SUPPORTED_YEAR
import com.example.track.data.other.converters.convertLocalDateToDate
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeConfig
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import java.time.LocalDate
import java.util.Date

@Composable
fun CustomDatePicker(
    isVisible: Boolean,
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
                onPositiveClick(date)
            }),
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        config = DateTimeConfig(minYear = MIN_SUPPORTED_YEAR, maxYear = LocalDate.now().year)
    )
}
