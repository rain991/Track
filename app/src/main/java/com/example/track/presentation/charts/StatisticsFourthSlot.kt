package com.example.track.presentation.charts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun StatisticsFourthSlot(text: String, modifier: Modifier, textStyle: TextStyle) {
    Card(modifier = modifier) {
        Text(text = text, style = textStyle, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}