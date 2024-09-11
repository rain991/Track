package com.savenko.track.presentation.components.ideasCards

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.constants.FEED_CARD_DELAY_ADDITIONAL
import com.savenko.track.data.other.constants.FEED_CARD_DELAY_FAST
import com.savenko.track.data.other.constants.FEED_CARD_DELAY_SLOW
import com.savenko.track.data.viewmodels.mainScreen.feed.AddToSavingIdeaDialogViewModel
import com.savenko.track.data.viewmodels.mainScreen.feed.NewIdeaDialogViewModel
import com.savenko.track.data.viewmodels.mainScreen.feed.TrackScreenFeedViewModel
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.presentation.components.dialogs.addToSavingIdeaDialog.AddToSavingDialog
import com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.feed.BudgetFeedCard
import com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.feed.NewIdeaFeedCard
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


/**
 * It is main screen feed which contains automatically scrollable pager which always starts
 * with card TrackMainFeedCard and ending with NewIdeaFeedCard
 * Scroll and state is handled by [TrackScreenFeedViewModel]
 */
@Composable
fun TrackScreenFeed() {
    val trackScreenFeedViewModel = koinViewModel<TrackScreenFeedViewModel>()
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    val addToSavingIdeaDialogViewModel = koinViewModel<AddToSavingIdeaDialogViewModel>()
    val currenciesPreferenceRepositoryImpl = koinInject<CurrenciesPreferenceRepository>()
    val currentIndex = trackScreenFeedViewModel.cardIndex.collectAsState()
    val maxIndex = trackScreenFeedViewModel.maxPagerIndex.collectAsState()
    val preferableCurrencyState =
        currenciesPreferenceRepositoryImpl.getPreferableCurrency().collectAsState(initial = CURRENCY_DEFAULT)
    val ideaList = trackScreenFeedViewModel.ideaList
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { maxIndex.value + 1 })
    val currentSavingAddingDialogState = addToSavingIdeaDialogViewModel.currentSavings.collectAsState()
    var needsAdditionalDelay by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        while (true) {
            var delayTime = if (currentIndex.value == 0 || currentIndex.value == maxIndex.value) {
                FEED_CARD_DELAY_SLOW
            } else {
                FEED_CARD_DELAY_FAST
            }
            if (currentIndex.value == 0) {
                delayTime += FEED_CARD_DELAY_ADDITIONAL
                needsAdditionalDelay = false
            }
            delay(delayTime)
            trackScreenFeedViewModel.incrementCardIndex()
        }
    }
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage <= ideaList.size + 1 && pagerState.targetPage != 0) {
            trackScreenFeedViewModel.setCardIndex(pagerState.currentPage)
        } else if (pagerState.currentPage == ideaList.size + 2) {
            trackScreenFeedViewModel.setCardIndex(0)
        }
    }
    LaunchedEffect(currentIndex.value) {
        pagerState.animateScrollToPage(currentIndex.value)
    }
    LaunchedEffect(key1 = Unit) {
        trackScreenFeedViewModel.checkListOfIdeasCompletionState()
    }
    if (currentSavingAddingDialogState.value != null) {
        AddToSavingDialog(addToSavingIdeaDialogViewModel)
    }
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        state = pagerState,
        beyondViewportPageCount = 1,
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) { index ->
        when (index) {
            0 -> BudgetFeedCard()
            maxIndex.value -> NewIdeaFeedCard(newIdeaDialogViewModel = newIdeaDialogViewModel)
            else -> when (ideaList[index - 1]) {
                is Savings -> SavingsIdeaCard(
                    savings = ideaList[index - 1] as Savings,
                    preferableCurrency = preferableCurrencyState.value,
                    addToSavingIdeaDialogViewModel = addToSavingIdeaDialogViewModel
                )

                is IncomePlans -> {
                    var completedValue by remember { mutableFloatStateOf(0.0f) }
                    LaunchedEffect(key1 = Unit) {
                        trackScreenFeedViewModel.getCompletionValue(ideaList[index - 1]).collect {
                            completedValue = it
                        }
                    }
                    IncomePlanIdeaCard(
                        incomePlans = ideaList[index - 1] as IncomePlans,
                        completionValue = completedValue,
                        preferableCurrency = preferableCurrencyState.value
                    )
                }

                is ExpenseLimits -> {
                    var completedValue by remember { mutableFloatStateOf(0.0f) }
                    LaunchedEffect(key1 = Unit) {
                        trackScreenFeedViewModel.getCompletionValue(ideaList[index - 1]).collect {
                            completedValue = it
                        }
                    }
                    ExpenseLimitIdeaCard(
                        expenseLimit = ideaList[index - 1] as ExpenseLimits,
                        preferableCurrency = preferableCurrencyState.value,
                        completedValue = completedValue
                    )
                }
            }
        }
    }
}