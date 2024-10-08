package com.savenko.track.presentation.screens.core

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.savenko.track.data.viewmodels.common.TrackScreenManagerViewModel

/**
 * Track screen manager is pager that handles navigation across main Track screens : SettingsTrackScreen, MainTrackScreen, StatisticsTrackScreen
 */
@Composable
fun TrackScreenManager(navHostController: NavHostController, viewModel: TrackScreenManagerViewModel) {
    val pagerValue = viewModel.pagerStateValue.collectAsState()
    val pagerState = rememberPagerState(initialPage = pagerValue.value) { 3 }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setPagerState(pagerState.currentPage)
    }
    HorizontalPager(
        state = pagerState
    ) { page ->
        when (page) {
            0 -> SettingsTrackScreen(navHostController)
            1 -> MainTrackScreen()
            2 -> StatisticsTrackScreen()
        }
    }
}