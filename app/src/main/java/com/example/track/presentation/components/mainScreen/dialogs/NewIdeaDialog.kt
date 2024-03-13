package com.example.track.presentation.components.mainScreen.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.track.data.viewmodels.mainScreen.MainScreenFeedViewModel
import com.example.track.presentation.states.IdeaSelectorTypes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewIdeaDialog() {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val newIdeaDialogState = mainScreenFeedViewModel.newIdeaDialogState.collectAsState()
    Dialog(onDismissRequest = { mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false) }) {
        Surface(
            shape = RoundedCornerShape(8.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Add idea",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                val options = listOf(IdeaSelectorTypes.Savings, IdeaSelectorTypes.ExpenseLimit, IdeaSelectorTypes.IncomePlans)
                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                            onClick = { mainScreenFeedViewModel.setTypeSelected(label) },
                            selected = newIdeaDialogState.value.typeSelected == label
                        ) {
                            Text(text = label.name, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = text,
                    onValueChange = { bottomSheetViewModel.setNote(it) },
                    label = { Text(label) },
                    maxLines = 1,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)),
                            shape = RoundedCornerShape(4.dp)
                        ),
                    colors = TextFieldDefaults.colors().copy(unfocusedContainerColor = MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}