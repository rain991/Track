package com.savenko.track.presentation.components.mainScreen.feed.feedCards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CRYPTO_DECIMAL_FORMAT
import com.savenko.track.data.other.constants.FIAT_DECIMAL_FORMAT
import com.savenko.track.data.viewmodels.mainScreen.BudgetIdeaCardViewModel
import com.savenko.track.domain.models.currency.CurrencyTypes
import com.savenko.track.presentation.components.common.parser.getMonthResID
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

// Along code is always used to name as BudgetIdeaCard
@Composable
fun TrackMainFeedCard() {
    val budgetIdeaCardViewModel = koinViewModel<BudgetIdeaCardViewModel>()
    val state = budgetIdeaCardViewModel.budgetCardState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        budgetIdeaCardViewModel.initializeStates()
    }
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val currentMonth = stringResource(id = getMonthResID(localDate = LocalDate.now()))
            Text(
                text = stringResource(R.string.budget_main_feed_card, currentMonth),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.84f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Box(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(
                            R.string.planned_main_track_screen,
                            if (state.value.currency.type == CurrencyTypes.FIAT) {
                                FIAT_DECIMAL_FORMAT.format(state.value.budget)
                            } else {
                                CRYPTO_DECIMAL_FORMAT.format(state.value.budget)
                            },
                            state.value.currency.ticker
                        ),
                        maxLines = 1
                    )

                    Text(
                        text = stringResource(
                            R.string.spent_main_track_screen,
                            if (state.value.currency.type == CurrencyTypes.FIAT) {
                                FIAT_DECIMAL_FORMAT.format(state.value.currentExpensesSum)
                            } else {
                                CRYPTO_DECIMAL_FORMAT.format(state.value.currentExpensesSum)
                            },
                            state.value.currency.ticker
                        ),
                        maxLines = 1
                    )

                }
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomCircularProgressIndicator(
                        modifier = Modifier
                            .size(96.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        initialValue = (state.value.budgetExpectancy * 100).toInt(),
                        primaryColor = MaterialTheme.colorScheme.primary,
                        secondaryColor = MaterialTheme.colorScheme.secondary,
                        circleRadius = 90f
                    )
                }
            }
        }
    }
}