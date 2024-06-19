package com.example.track.presentation.components.mainScreen.dialogs.dialogComponents

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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel
import com.example.track.presentation.components.common.ui.CustomDatePicker
import com.example.track.presentation.states.componentRelated.NewIdeaDialogState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExpenseLimitsDialogInputs(newIdeaDialogState: NewIdeaDialogState) {
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.each_month_limit_new_idea_dialog))
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = newIdeaDialogState.eachMonth ?: true,
            onCheckedChange = { newIdeaDialogViewModel.setEachMonth(it) })
    }
    Spacer(modifier = Modifier.height(4.dp))
    if (newIdeaDialogState.eachMonth == false) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(R.string.limit_end_date_ideas))
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
        Spacer(modifier = Modifier.height(4.dp))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.related_to_all_categories_ideas))
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = newIdeaDialogState.relatedToAllCategories ?: true,
            onCheckedChange = { newIdeaDialogViewModel.setSelectedToAllCategories(it) })
    }
    if (newIdeaDialogState.relatedToAllCategories != true) {
        NewIdeaCategoriesGrid()
    }
}