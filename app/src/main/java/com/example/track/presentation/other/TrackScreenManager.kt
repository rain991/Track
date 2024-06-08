package com.example.track.presentation.other

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.track.data.viewmodels.common.TrackScreenManagerViewModel
import com.example.track.presentation.screens.Core.MainTrackScreen
import com.example.track.presentation.screens.Core.SettingsExpenseScreen
import com.example.track.presentation.screens.Core.StatisticScreenPlaceholder
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackScreenManager(navHostController: NavHostController) {
    val viewModel = koinViewModel<TrackScreenManagerViewModel>()
    val pagerValue = viewModel.pagerState.collectAsState()
    val pagerState = rememberPagerState(initialPage = pagerValue.value) { 3 }
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


