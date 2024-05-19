package com.example.track.presentation.components.mainScreen.feed.ideasCards

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track.data.other.constants.FEED_CARD_DELAY_FAST
import com.example.track.data.other.constants.FEED_CARD_DELAY_SLOW
import com.example.track.data.viewmodels.mainScreen.BudgetIdeaCardViewModel
import com.example.track.data.viewmodels.mainScreen.TrackScreenFeedViewModel
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.domain.models.idea.IncomePlans
import com.example.track.domain.models.idea.Savings
import com.example.track.presentation.components.mainScreen.feed.ExpenseLimitIdeaCard
import com.example.track.presentation.components.mainScreen.feed.feedCards.NewIdeaFeedCard
import com.example.track.presentation.components.mainScreen.feed.feedCards.TrackMainFeedCard
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


/*  It is main screen feed which contains automatically scrollable pager which always starting with card TrackMainFeedCard
    and ending with NewIdeaFeedCard. Also contains :   */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackScreenFeed() {
    val trackScreenFeedViewModel = koinViewModel<TrackScreenFeedViewModel>()
    val pagerState = rememberPagerState(pageCount = { trackScreenFeedViewModel.maxPagerIndex.value })
    val ideaList = trackScreenFeedViewModel.ideaList
    val currentIndex = trackScreenFeedViewModel.cardIndex.collectAsState()
    val maxIndex = trackScreenFeedViewModel.maxPagerIndex.collectAsState()
    val budgetIdeaCardViewModel = koinViewModel<BudgetIdeaCardViewModel>()
    val budgetIdeaCardState = budgetIdeaCardViewModel.budgetCardState.collectAsState()
    LaunchedEffect(true) {
        budgetIdeaCardViewModel.initializeStates()
        while (true) {
            delay(if (currentIndex.value == 0 || currentIndex.value == maxIndex.value) FEED_CARD_DELAY_SLOW else FEED_CARD_DELAY_FAST)
            trackScreenFeedViewModel.incrementCardIndex()
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
            0 -> TrackMainFeedCard(budgetIdeaCardState.value)
            maxIndex.value -> NewIdeaFeedCard(trackScreenFeedViewModel = trackScreenFeedViewModel)
            else -> when (ideaList[index - 1]) {
                is Savings -> SavingsIdeaCard(savings = ideaList[index - 1] as Savings) // WARNING AS CAST
                is IncomePlans -> IncomePlanIdeaCard(incomePlans = ideaList[index - 1] as IncomePlans)
                is ExpenseLimits -> ExpenseLimitIdeaCard(expenseLimit = ideaList[index - 1] as ExpenseLimits)
            }
        }
        Log.d("MyLog", "MainScreenFeed: $index")
    }
}