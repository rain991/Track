package com.savenko.track.presentation.components.screenComponents.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.savenko.track.presentation.components.mainScreen.expenseAndIncomeLazyColumn.MainScreenLazyColumn
import com.savenko.track.presentation.components.mainScreen.feed.ideasCards.TrackScreenFeed

@Composable
fun MainTrackScreenComponent(paddingValues: PaddingValues, isPageNameVisible : Boolean){
    Column(
        modifier = Modifier
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        if (!isPageNameVisible) Spacer(modifier = Modifier.height(12.dp))
        TrackScreenFeed()
        MainScreenLazyColumn()
    }
}