package com.example.expensetracker.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.expensetracker.data.viewmodels.mainScreen.MainScreenFeedViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenFeed() {  // should have VM as parameter, because public
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val list = mainScreenFeedViewModel.ideaList
    val currentIndex = mainScreenFeedViewModel.cardIndex.collectAsState()
    val pagerState = rememberPagerState { mainScreenFeedViewModel.maxPagerIndex.value }
    HorizontalPager(modifier = Modifier.fillMaxWidth().height(140.dp),state = pagerState ,contentPadding = PaddingValues(horizontal = 8.dp)) { page ->
    when (page) {
        0 -> Main_FeedCard(mainScreenFeedViewModel = mainScreenFeedViewModel)
        1 -> NewIdea_FeedCard(mainScreenFeedViewModel = mainScreenFeedViewModel)
    }
    }
}

@Composable
private fun Main_FeedCard(mainScreenFeedViewModel: MainScreenFeedViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = mainScreenFeedViewModel.cardIndex.toString())
    }
}

@Composable
private fun NewIdea_FeedCard(mainScreenFeedViewModel: MainScreenFeedViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = mainScreenFeedViewModel.cardIndex.toString())
    }
}


