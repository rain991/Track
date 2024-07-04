package com.savenko.track.presentation.components.common.ui

import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    isDialogVisible: Boolean,
    futureDatePicker: Boolean,
    onDecline: () -> Unit,
    onAccept: (Date, Date) -> Unit,
) {
    val dateRangePickerState = rememberDateRangePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentTimeMillis = System.currentTimeMillis()
            return (futureDatePicker && utcTimeMillis >= currentTimeMillis) || (!futureDatePicker && utcTimeMillis <= currentTimeMillis)
        }

        override fun isSelectableYear(year: Int): Boolean {
            return true
        }
    })
    if (isDialogVisible) {
        DatePickerDialog(
            onDismissRequest = {
                onAccept(
                    Date(dateRangePickerState.selectedStartDateMillis ?: System.currentTimeMillis()),
                    Date(dateRangePickerState.selectedEndDateMillis ?: (System.currentTimeMillis() + 86400000))
                )
            },
            dismissButton = {
                Button(
                    onClick = { onDecline() }
                ) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onAccept(
                        Date(dateRangePickerState.selectedStartDateMillis ?: System.currentTimeMillis()),
                        Date(dateRangePickerState.selectedEndDateMillis ?: (System.currentTimeMillis() + 86400000))
                    )
                }) {
                    Text(text = "Confirm")
                }
            }) {
            DateRangePicker(state = dateRangePickerState, title = null, showModeToggle = false)
        }
    }
}

