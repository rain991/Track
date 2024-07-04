package com.savenko.track.presentation.components.common.ui.datePickers

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleDatePickerDialog(
    isDialogVisible: Boolean,
    futureDatePicker: Boolean,
    onDecline: () -> Unit,
    onAccept: (Date) -> Unit,
) {
    val dateState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentTimeMillis = System.currentTimeMillis()
            return (futureDatePicker && utcTimeMillis >= currentTimeMillis) || (!futureDatePicker && utcTimeMillis <= currentTimeMillis)
        }

        override fun isSelectableYear(year: Int): Boolean {
            return true
        }
    })
    if (isDialogVisible) {
        DatePickerDialog(modifier = Modifier.padding(8.dp),
            onDismissRequest = { onDecline() },
            confirmButton = {
                Button(
                    onClick = {
                        onAccept(Date(dateState.selectedDateMillis ?: System.currentTimeMillis()))
                    }
                ) {
                    Text(text = stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDecline() }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(
                state = dateState,
                showModeToggle = true
            )
        }
    }
}