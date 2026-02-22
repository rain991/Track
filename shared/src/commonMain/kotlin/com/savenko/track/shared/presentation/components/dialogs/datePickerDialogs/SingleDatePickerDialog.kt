package com.savenko.track.shared.presentation.components.dialogs.datePickerDialogs

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

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
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.dp
import kotlin.time.Clock
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleDatePickerDialog(
    isDialogVisible: Boolean,
    futureDatePicker: Boolean,
    onDecline: () -> Unit,
    onAccept: (Instant) -> Unit,
) {
    val dateState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
            return (futureDatePicker && utcTimeMillis >= currentTimeMillis) || (!futureDatePicker && utcTimeMillis <= currentTimeMillis)
        }

        override fun isSelectableYear(year: Int): Boolean {
            return true
        }
    })
    if (isDialogVisible) {
        DatePickerDialog(
            modifier = Modifier.padding(8.dp),
            onDismissRequest = { onDecline() },
            confirmButton = {
                Button(
                    onClick = {
                        onAccept(
                            Instant.fromEpochMilliseconds(
                                dateState.selectedDateMillis ?: Clock.System.now().toEpochMilliseconds()
                            )
                        )
                    }
                ) {
                    Text(text = stringResource(Res.string.confirm))
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDecline() }
                ) {
                    Text(text = stringResource(Res.string.cancel))
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
