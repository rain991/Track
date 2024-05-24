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
import com.example.track.data.other.constants.IDEA_NOTE_MAX_LENGTH
import com.example.track.data.viewmodels.mainScreen.TrackScreenFeedViewModel
import com.example.track.presentation.components.common.ui.CustomDatePicker
import com.example.track.presentation.components.other.GradientInputTextField
import com.example.track.presentation.states.componentRelated.NewIdeaDialogState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavingsDialogInputs(
    newIdeaDialogState: NewIdeaDialogState
) {
    val trackScreenFeedViewModel = koinViewModel<TrackScreenFeedViewModel>()
    val labelInputText = newIdeaDialogState.label
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), horizontalArrangement = Arrangement.Start
    ) {
        
//        TextField(
//            value = labelInputText ?: "",
//            onValueChange = { trackScreenFeedViewModel.setLabel(it) },
//            label = { "Saving for" },
//            maxLines = 1,
//            modifier = Modifier
//                .padding(horizontal = 8.dp)
//                .border(
//                    width = 2.dp,
//                    brush = Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)),
//                    shape = RoundedCornerShape(4.dp)
//                ),
//            colors = TextFieldDefaults.colors().copy(unfocusedContainerColor = MaterialTheme.colorScheme.background)
//        )
       // Spacer(modifier = Modifier.width(8.dp))
        GradientInputTextField(value = labelInputText ?: "", label = "Saving for", maxLines = 2) {
            if(it.length < IDEA_NOTE_MAX_LENGTH){
                trackScreenFeedViewModel.setLabel(it)
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Included in budget")
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = newIdeaDialogState.includedInBudget ?: true,
            onCheckedChange = { it -> trackScreenFeedViewModel.setIncludedInBudget(it) })
    }
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.plan_end_date_ideas))
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