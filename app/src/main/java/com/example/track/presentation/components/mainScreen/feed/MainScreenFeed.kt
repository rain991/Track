package com.example.track.presentation.components.mainScreen.feed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.track.data.constants.FEED_CARD_DELAY_FAST
import com.example.track.data.constants.FEED_CARD_DELAY_SLOW
import com.example.track.data.viewmodels.mainScreen.BudgetIdeaCardViewModel
import com.example.track.data.viewmodels.mainScreen.MainScreenFeedViewModel
import com.example.track.presentation.components.mainScreen.expenseAndIncomeLazyColumn.getMonthResID
import com.example.track.presentation.states.BudgetIdeaCardState
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenFeed() {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val pagerState = rememberPagerState(pageCount = { mainScreenFeedViewModel.maxPagerIndex.value })
    val ideaList = mainScreenFeedViewModel.ideaList
    val currentIndex = mainScreenFeedViewModel.cardIndex.collectAsState()
    val maxIndex = mainScreenFeedViewModel.maxPagerIndex.collectAsState()
    val budgetIdeaCardViewModel = koinViewModel<BudgetIdeaCardViewModel>()
    val budgetIdeaCardState = budgetIdeaCardViewModel.budgetCardState.collectAsState()
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
            0 -> Main_FeedCard(budgetIdeaCardState.value)
            1 -> NewIdea_FeedCard(mainScreenFeedViewModel = mainScreenFeedViewModel)
            else -> Idea_FeedCard()
        }
    }
}

@Composable
private fun Main_FeedCard(state : BudgetIdeaCardState) {
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
        Row(modifier = Modifier.padding(top=8.dp, bottom = 4.dp)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
                Text(stringResource(id = getMonthResID(localDate = LocalDate.now())), style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.weight(1f))
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)) {
                        append("Planned  ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(state.budget.toString())
                    }
                })
                Spacer(Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.Start) {
                Text("budget")
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)) {
                        append("Spent ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(state.currentExpensesSum.toString())
                    }
                })
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(state.budgetExpectancy.times(100).toString())
                    }
                    withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)) {
                        append("% expectancy rate ")
                    }
                })
            }
        }
        LinearProgressIndicator(
            progress = { state.budgetExpectancy },modifier =Modifier.fillMaxWidth().padding(bottom =8.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    val state = BudgetIdeaCardState(budget = 100f, budgetExpectancy = 0.35f, currentExpensesSum = 35f )
    Main_FeedCard(state = state)
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

