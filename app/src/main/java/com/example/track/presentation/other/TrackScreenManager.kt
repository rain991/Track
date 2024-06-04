package com.example.track.presentation.other

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.track.presentation.screens.Core.MainTrackScreen
import com.example.track.presentation.screens.Core.SettingsExpenseScreen
import com.example.track.presentation.screens.Core.StatisticScreenPlaceholder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackScreenManager(navHostController: NavHostController) {
    val pagerState = rememberPagerState(initialPage = 1) { 3 }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> SettingsExpenseScreen(navHostController)
                1 -> MainTrackScreen()
                2 -> StatisticScreenPlaceholder()
            }
        }
    }
}


