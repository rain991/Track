package com.savenko.track.presentation.components.mainScreen.dialogs.dialogComponents

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.IDEA_NOTE_MAX_LENGTH
import com.savenko.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel
import com.savenko.track.presentation.components.common.ui.CustomDatePicker
import com.savenko.track.presentation.components.other.GradientInputTextField
import com.savenko.track.presentation.states.componentRelated.NewIdeaDialogState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavingsDialogInputs(
    newIdeaDialogState: NewIdeaDialogState
) {
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    val labelInputText = newIdeaDialogState.label
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), horizontalArrangement = Arrangement.Start
    ) {
        GradientInputTextField(value = labelInputText ?: "", label = stringResource(R.string.saving_for), maxLines = 2) {
            if(it.length < IDEA_NOTE_MAX_LENGTH){
                newIdeaDialogViewModel.setLabel(it)
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.plan_end_date_ideas))
            Text(text = stringResource(R.string.optional), style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Button(onClick = { newIdeaDialogViewModel.setIsDatePickerDialogVisible(true) }) {
            Text(
                text = if (newIdeaDialogState.endDate != null) newIdeaDialogState.endDate.toString() else stringResource(id = R.string.date),
                style = MaterialTheme.typography.bodySmall
            )
        }
        CustomDatePicker(
            isVisible = newIdeaDialogState.isDateDialogVisible,
            onNegativeClick = { newIdeaDialogViewModel.setIsDatePickerDialogVisible(false) },
            onPositiveClick = { date -> newIdeaDialogViewModel.setEndDate(date) }
        )
    }
}