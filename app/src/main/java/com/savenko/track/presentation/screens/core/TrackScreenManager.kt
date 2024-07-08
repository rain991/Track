package com.savenko.track.presentation.screens.core

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.savenko.track.data.viewmodels.common.TrackScreenManagerViewModel
import org.koin.androidx.compose.koinViewModel

/*
Track screen manager is pager that handles navigation accross main Track screens : SettingsTrackScreen, MainTrackScreen, StatisticsTrackScreen
*/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackScreenManager(navHostController: NavHostController) {
    val viewModel = koinViewModel<TrackScreenManagerViewModel>()
    val pagerValue = viewModel.pagerStateValue.collectAsState()
    val pagerState = rememberPagerState(initialPage = pagerValue.value) { 3 }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> SettingsTrackScreen(navHostController)
                1 -> MainTrackScreen()
                2 -> StatisticsTrackScreen()
            }
        }
    }
}


