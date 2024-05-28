package com.example.track.presentation.components.mainScreen.feed.ideasCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.track.data.viewmodels.mainScreen.AddToSavingIdeaDialogViewModel
import com.example.track.domain.models.idea.Savings

@Composable
fun SavingsIdeaCard(savings: Savings, preferableCurrencyTicker: String, addToSavingIdeaDialogViewModel: AddToSavingIdeaDialogViewModel) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = savings.label, style = MaterialTheme.typography.titleSmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Saving", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(0.5f), verticalArrangement = Arrangement.SpaceEvenly) {
                    Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Start) {
                        Text(text = "Planned ${savings.goal} " + preferableCurrencyTicker)
                    }
                    Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Start) {
                        Text(text = "Completed for" + " ${savings.value} " + preferableCurrencyTicker)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material3.Button(onClick = { addToSavingIdeaDialogViewModel.setCurrentSaving(savings) }) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}