package com.savenko.track.shared.presentation.components.dialogs.datePickerDialogs

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.shared.data.other.converters.dates.MILLISECONDS_IN_DAY
import kotlin.time.Clock
import kotlin.time.Instant


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    isDialogVisible: Boolean,
    futureDatePicker: Boolean,
    onDecline: () -> Unit,
    onAccept: (Instant, Instant) -> Unit,
) {
    val currentMoment = Clock.System.now()
    val dateRangePickerState =
        rememberDateRangePickerState(selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return (futureDatePicker && utcTimeMillis >= currentMoment.toEpochMilliseconds()) || (!futureDatePicker && utcTimeMillis <= currentMoment.toEpochMilliseconds())
            }

            override fun isSelectableYear(year: Int): Boolean {
                return true
            }
        })
    val startDate by remember {
        derivedStateOf {
            dateRangePickerState.selectedStartDateMillis?.let {
                dateRangePickerState.selectedStartDateMillis
            } ?: run {
                if (futureDatePicker) {
                    currentMoment.toEpochMilliseconds()
                } else {
                    currentMoment.toEpochMilliseconds() - MILLISECONDS_IN_DAY
                }
            }
        }
    }
    val endDate by remember {
        derivedStateOf {
            dateRangePickerState.selectedEndDateMillis?.let {
                dateRangePickerState.selectedEndDateMillis
            } ?: run {
                if (futureDatePicker) {
                    currentMoment.toEpochMilliseconds() + MILLISECONDS_IN_DAY
                } else {
                    currentMoment.toEpochMilliseconds()
                }
            }
        }
    }
    if (isDialogVisible) {
        DatePickerDialog(
            modifier = Modifier.padding(8.dp),
            onDismissRequest = {
                onAccept(
                    Instant.fromEpochMilliseconds(startDate),
                    Instant.fromEpochMilliseconds(endDate)
                )
            },
            dismissButton = {
                Button(
                    onClick = { onDecline() }
                ) {
                    Text(text = stringResource(Res.string.cancel))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onAccept(
                        Instant.fromEpochMilliseconds(startDate),
                        Instant.fromEpochMilliseconds(endDate)
                    )
                }) {
                    Text(text = stringResource(Res.string.confirm))
                }
            }) {
            DateRangePicker(state = dateRangePickerState, title = null, showModeToggle = false)
        }
    }
}

