package com.example.track.presentation.components.mainScreen.feed.ideasCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.track.data.viewmodels.mainScreen.TrackScreenFeedViewModel
import com.example.track.domain.models.idea.IncomePlans
import org.koin.androidx.compose.koinViewModel
/*  Contains Card used in expense screen feed to show income plan entity  */
@Composable
fun IncomePlanIdeaCard(incomePlans: IncomePlans) {
    val trackScreenFeedViewModel = koinViewModel<TrackScreenFeedViewModel>()
    var completedAbsoluteValue by remember { mutableFloatStateOf(0.0f) }
    var complitionRate by remember { mutableFloatStateOf(0.0f) }
    LaunchedEffect(key1 = trackScreenFeedViewModel.ideaList) {
        completedAbsoluteValue = trackScreenFeedViewModel.getCompletionValue(incomePlans).value
        trackScreenFeedViewModel.getCompletionValue(incomePlans).collect {
            if (it != 0.0f) {
                complitionRate = incomePlans.goal.div(it)
            } else 0.0f
        }
    }
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp), colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "income plan",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Start) {
                Text(text = "Planned")
                Spacer(Modifier.width(2.dp))
                Text(text = "${incomePlans.goal}")
            }
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                Text(text = "Completed for")
                Spacer(Modifier.width(2.dp))
                Text(text = completedAbsoluteValue.toString())
            }
        }
        Spacer(Modifier.height(2.dp))
        if (incomePlans.endDate != null) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Preferable period:")
                Spacer(Modifier.width(2.dp))
                Text(text = "${incomePlans.startDate} + ${incomePlans.endDate}")
            }
        }
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { complitionRate },
            modifier = Modifier
                .fillMaxWidth(fraction = 0.6f)
        )
    }
}