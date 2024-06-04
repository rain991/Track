package com.example.track.presentation.components.statisticsScreen.charts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsFourthSlot(modifier: Modifier, isSplitted: Boolean, text1: String, text2: String?) {
    if (isSplitted && text2 != null) {
      Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
          Box(modifier = modifier) {
              Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly){
                  Card( elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
                      Text(text = text1, style = MaterialTheme.typography.bodyMedium)
                  }
                  Spacer(modifier = Modifier.height(8.dp))
                  Card( elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
                      Text(text = text2, style =MaterialTheme.typography.bodyMedium)
                  }
              }
          }
      }
    }else{
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center){
            Box(modifier = modifier) {
                Card( elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), modifier = Modifier.fillMaxWidth()) {
                    Text(text = text1, style =MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}