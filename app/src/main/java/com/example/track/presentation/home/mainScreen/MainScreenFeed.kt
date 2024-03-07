package com.example.track.presentation.home.mainScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.track.data.constants.FEED_CARD_DELAY_FAST
import com.example.track.data.constants.FEED_CARD_DELAY_SLOW
import com.example.track.data.viewmodels.mainScreen.MainScreenFeedViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenFeed() {  // should have VM as parameter, because public
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val ideaList = mainScreenFeedViewModel.ideaList
    val currentIndex = mainScreenFeedViewModel.cardIndex.collectAsState()
    val maxIndex  =mainScreenFeedViewModel.maxPagerIndex.collectAsState()
    val pagerState = rememberPagerState(pageCount = { mainScreenFeedViewModel.maxPagerIndex.value })

    LaunchedEffect(true) {
        while (true) {
            delay(if (currentIndex.value == 0 || currentIndex.value == maxIndex.value) FEED_CARD_DELAY_SLOW else FEED_CARD_DELAY_FAST)
            mainScreenFeedViewModel.incrementCardIndex()
        }
    }
    LaunchedEffect(currentIndex.value) {
        pagerState.animateScrollToPage(currentIndex.value)
    }
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        state = pagerState,
        beyondBoundsPageCount = 2,
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) { index ->
        when (index) {
            0 -> Main_FeedCard()
            1 -> NewIdea_FeedCard(mainScreenFeedViewModel = mainScreenFeedViewModel)
            else -> Idea_FeedCard()
        }
    }
}
@Composable
private fun Idea_FeedCard() {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp), colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = mainScreenFeedViewModel.cardIndex.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun Main_FeedCard() {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp), colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = mainScreenFeedViewModel.cardIndex.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun NewIdea_FeedCard(mainScreenFeedViewModel: MainScreenFeedViewModel) {

    val value = mainScreenFeedViewModel.cardIndex.collectAsState()
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp), colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = value.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}

