package com.savenko.track.shared.presentation.components.dialogs.newIdeaDialog.components.dialogInputs

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.savenko.track.shared.presentation.other.formatDateWithYear
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.NewIdeaDialogViewModel
import com.savenko.track.shared.presentation.components.dialogs.datePickerDialogs.SingleDatePickerDialog
import com.savenko.track.shared.presentation.screens.states.core.common.NewIdeaDialogState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun IncomePlanDialogInputs(newIdeaDialogState: NewIdeaDialogState) {
    val currentTimeZone = TimeZone.currentSystemDefault()
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(Res.string.plan_end_date_ideas))
            Text(
                text = stringResource(Res.string.optional),
                style = MaterialTheme.typography.labelSmall
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Button(onClick = { newIdeaDialogViewModel.setIsDatePickerDialogVisible(true) }) {
            Text(
                text = if (newIdeaDialogState.endDate != null) formatDateWithYear(newIdeaDialogState.endDate.toLocalDateTime(currentTimeZone)) else stringResource(Res.string.date
                ),
                style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center
            )
        }
        SingleDatePickerDialog(
            isDialogVisible = newIdeaDialogState.isDateDialogVisible,
            futureDatePicker = true,
            onDecline = { newIdeaDialogViewModel.setIsDatePickerDialogVisible(false) }) { date ->
            newIdeaDialogViewModel.setEndDate(date)
            newIdeaDialogViewModel.setIsDatePickerDialogVisible(false)
        }
    }
}
