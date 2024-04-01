package com.example.track.presentation.charts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsSecondSlot(modifier: Modifier, isSplited: Boolean, text1: String, text2: String?, textStyle: TextStyle) {
    if (isSplited && text2 != null) {
        Row{
            Box(modifier = modifier) {
                Card(modifier = Modifier.fillMaxHeight(0.5f)) {
                    Text(text = text1, style =textStyle)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Card(modifier = Modifier.fillMaxHeight(0.5f)) {
                    Text(text = text2, style =textStyle)
                }
            }
        }
    }else{
        Row{
            Box(modifier = modifier) {
                Card(modifier = Modifier.fillMaxHeight()) {
                    Text(text = text1, style =textStyle)
                }
            }
        }
    }
}