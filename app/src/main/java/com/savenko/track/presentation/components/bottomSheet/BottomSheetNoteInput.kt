package com.savenko.track.presentation.components.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.FINANCIAL_NOTE_MAX_LENGTH

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetNoteInput(modifier: Modifier, note: String, onNoteChange: (String) -> Unit) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.your_note_adding_exp),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = note,
            onValueChange = { if (it.length < FINANCIAL_NOTE_MAX_LENGTH) onNoteChange(it) },
            placeholder = {
                Text(
                    text = stringResource(R.string.describe_your_financial_bottom_sheet),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
    }
}