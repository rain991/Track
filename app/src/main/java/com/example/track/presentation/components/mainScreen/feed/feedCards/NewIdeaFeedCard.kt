package com.example.track.presentation.components.mainScreen.feed.feedCards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.track.R
import com.example.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel

@Composable
fun NewIdeaFeedCard(newIdeaDialogViewModel: NewIdeaDialogViewModel) {
    Card(
        modifier = Modifier
            .clickable {
                newIdeaDialogViewModel.setIsNewIdeaDialogVisible(true)
            }
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(onClick = {}) {
                Text(
                    stringResource(R.string.new_idea_main_screen_feed),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(R.string.first_notion_new_idea_card),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(R.string.second_notion_new_idea_card),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(R.string.third_notion_new_idea_card),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}