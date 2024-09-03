package com.savenko.track.presentation.components.customComponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun ErrorText(
    modifier: Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error)
) {
    Text(
        text = text,
        style = style,
        modifier = modifier
    )
}

