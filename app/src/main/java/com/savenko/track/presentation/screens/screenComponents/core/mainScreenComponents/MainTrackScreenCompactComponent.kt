package com.savenko.track.presentation.screens.screenComponents.core.mainScreenComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.savenko.track.presentation.components.ideasCards.TrackScreenFeed
import com.savenko.track.presentation.other.windowInfo.WindowInfo
import com.savenko.track.presentation.other.windowInfo.rememberWindowInfo
import com.savenko.track.presentation.screens.screenComponents.core.mainScreenComponents.lazyColumn.MainScreenLazyColumn

@Composable
fun MainTrackScreenCompactComponent(paddingValues: PaddingValues, isPageNameVisible: Boolean) {
    val windowInfo = rememberWindowInfo()
    Column(
        modifier = Modifier
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        if (!isPageNameVisible) Spacer(modifier = Modifier.height(12.dp))
        TrackScreenFeed()
        MainScreenLazyColumn(containsInfoCards = windowInfo.screenHeightInfo !is WindowInfo.WindowType.Compact)
    }
}

@Composable
fun MainTrackScreenExpandedComponent(paddingValues: PaddingValues) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
    )
    {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            TrackScreenFeed()
            Spacer(modifier = Modifier.height(8.dp))
            //TrackScreenInfoCards()
        }

        Column(modifier = Modifier.weight(1f)) {
            MainScreenLazyColumn(containsInfoCards = true)
        }
    }
}