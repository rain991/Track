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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.data.viewmodels.mainScreen.TrackScreenFeedViewModel
import com.example.track.presentation.components.common.ui.CustomDatePicker
import com.example.track.presentation.states.componentRelated.NewIdeaDialogState
import org.koin.androidx.compose.koinViewModel

@Composable
fun IncomePlanDialogInputs(newIdeaDialogState: NewIdeaDialogState) {
    val trackScreenFeedViewModel = koinViewModel<TrackScreenFeedViewModel>()
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(R.string.plan_end_date_ideas))
            Text(text = "optional", style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Button(onClick = { trackScreenFeedViewModel.setIsDatePickerDialogVisible(true) }) {
            Text(
                text = if (newIdeaDialogState.endDate != null) newIdeaDialogState.endDate.toString() else "Date",
                style = MaterialTheme.typography.bodySmall
            )
        }
        CustomDatePicker(
            isVisible = newIdeaDialogState.isDateDialogVisible,
            onNegativeClick = { trackScreenFeedViewModel.setIsDatePickerDialogVisible(false) },
            onPositiveClick = { date -> trackScreenFeedViewModel.setEndDate(date) }
        )
    }
}