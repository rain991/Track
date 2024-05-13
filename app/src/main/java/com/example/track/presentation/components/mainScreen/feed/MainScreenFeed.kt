package com.example.track.presentation.components.mainScreen.feed

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.track.R
import com.example.track.data.other.constants.FEED_CARD_DELAY_FAST
import com.example.track.data.other.constants.FEED_CARD_DELAY_SLOW
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.domain.models.idea.IncomePlans
import com.example.track.domain.models.idea.Savings
import com.example.track.data.viewmodels.mainScreen.BudgetIdeaCardViewModel
import com.example.track.data.viewmodels.mainScreen.MainScreenFeedViewModel
import com.example.track.presentation.components.mainScreen.expenseAndIncomeLazyColumn.getMonthResID
import com.example.track.presentation.states.componentRelated.BudgetIdeaCardState
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenFeed() {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val pagerState = rememberPagerState(pageCount = { mainScreenFeedViewModel.maxPagerIndex.value+1 })
    val ideaList = mainScreenFeedViewModel.ideaList
    val currentIndex = mainScreenFeedViewModel.cardIndex.collectAsState()
    val maxIndex = mainScreenFeedViewModel.maxPagerIndex.collectAsState()
    val budgetIdeaCardViewModel = koinViewModel<BudgetIdeaCardViewModel>()
    val budgetIdeaCardState = budgetIdeaCardViewModel.budgetCardState.collectAsState()
    Log.d("MyLog", "ideaList size : ${ideaList.size} ")
    Log.d("MyLog", "ideaList size : ${maxIndex} ")
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
            maxIndex.value -> NewIdea_FeedCard(mainScreenFeedViewModel = mainScreenFeedViewModel)
            else -> when(ideaList[index-1]){
                is Savings -> SavingsIdeaCard(savings = ideaList[index-1] as Savings)
                is IncomePlans -> IncomeIdeaCard(incomePlans = ideaList[index-1] as IncomePlans)
                is ExpenseLimits -> ExpenseLimitIdeaCard(expenseLimit = ideaList[index-1] as ExpenseLimits)
            }
        }
        Log.d("MyLog", "MainScreenFeed: $index")
    }
}

@Composable
private fun Main_FeedCard(state: BudgetIdeaCardState) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp), colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier//.weight(1f)
                .padding(top = 8.dp)
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.9f)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.92f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(id = getMonthResID(localDate = LocalDate.now())),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("Planned  ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(state.budget.toString())
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append(" ")
                        append(state.currencyTicker)
                    }

                })
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.92f)

            ) {
                Text(
                    "budget", style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Start
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("Spent ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(state.currentExpensesSum.toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append(" ")
                            append(state.currencyTicker)
                        }
                    }, textAlign = TextAlign.Center)
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(state.budgetExpectancy.times(100).toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("% expectancy rate ")
                        }
                    })
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            LinearProgressIndicator(
                progress = { state.budgetExpectancy },
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.6f),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
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
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = mainScreenFeedViewModel.cardIndex.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun NewIdea_FeedCard(mainScreenFeedViewModel: MainScreenFeedViewModel) {
    Card(
        modifier = Modifier
            .clickable {
                mainScreenFeedViewModel.setIsNewIdeaDialogVisible(true)
            }
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp), colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(onClick = {}) {
                Text(
                    stringResource(R.string.new_idea_main_screen_feed),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(R.string.first_notion_new_idea_card),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(R.string.second_notion_new_idea_card),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(R.string.third_notion_new_idea_card),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

