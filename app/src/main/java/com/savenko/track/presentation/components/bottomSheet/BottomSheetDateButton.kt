package com.savenko.track.presentation.components.bottomSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.converters.dates.convertDateToLocalDate
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.formatDateAsNumeric
import com.savenko.track.data.other.converters.dates.formatDateWithoutYear
import com.savenko.track.presentation.components.dialogs.datePickerDialogs.SingleDatePickerDialog
import com.savenko.track.presentation.screens.states.core.common.BottomSheetViewState
import java.time.LocalDate

@Composable
fun BottomSheetDatePicker(
    bottomSheetViewState: BottomSheetViewState,
    isDayOutOfPredefinedSpan: (LocalDate) -> Boolean,
    onSetDatePicked: (LocalDate) -> Unit,
    onTogglePickerState: () -> Unit
) {
    var text by remember { mutableStateOf(formatDateWithoutYear(convertLocalDateToDate(bottomSheetViewState.datePicked))) }
    text = if (!isDayOutOfPredefinedSpan(bottomSheetViewState.datePicked)) {
        stringResource(R.string.date)
    } else {
        formatDateWithoutYear(convertLocalDateToDate(bottomSheetViewState.datePicked))
    }
    val todayDate = remember {
        derivedStateOf { LocalDate.now() }
    }
    val yesterdayDate = remember {
        derivedStateOf { LocalDate.now().minusDays(1) }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        BottomSheetDateButton(
            modifier = Modifier.height(IntrinsicSize.Min),
            text = stringResource(R.string.today),
            isSelected = (bottomSheetViewState.todayButtonActiveState), date = todayDate.value
        ) { onSetDatePicked(LocalDate.now()) }
        BottomSheetDateButton(
            modifier = Modifier.height(IntrinsicSize.Min),
            text = stringResource(R.string.yesterday),
            isSelected = (bottomSheetViewState.yesterdayButtonActiveState),
            date = yesterdayDate.value
        ) { onSetDatePicked(LocalDate.now().minusDays(1)) }
        Button(onClick = { onTogglePickerState() }) {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
        SingleDatePickerDialog(
            isDialogVisible = bottomSheetViewState.timePickerState,
            futureDatePicker = false,
            onDecline = { onTogglePickerState() }) { date ->
            onSetDatePicked(convertDateToLocalDate(date))
            onTogglePickerState()
        }
    }
}


@Composable
fun BottomSheetDateButton(
    modifier: Modifier,
    text: String,
    isSelected: Boolean,
    date: LocalDate,
    onSelect: () -> Unit
) {
    Button(
        onClick = onSelect,
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(), verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(visible = isSelected, exit = fadeOut()) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = stringResource(R.string.selected_date_add_exp),
                        modifier = Modifier.height(22.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            if (isSelected) Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
                )
                AnimatedVisibility(visible = isSelected) {
                    Text(
                        text = formatDateAsNumeric(convertLocalDateToDate(date)),
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}