package com.savenko.track.shared.presentation.screens.screenComponents.mainScreenRelated.feed

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

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
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.shared.data.other.constants.CRYPTO_SCALE
import com.savenko.track.shared.data.other.constants.FIAT_SCALE
import com.savenko.track.shared.data.other.converters.dates.toLocalDate
import com.savenko.track.shared.data.other.constants.toFormattedCurrency
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.BudgetIdeaCardViewModel
import com.savenko.track.shared.domain.models.currency.CurrencyTypes
import com.savenko.track.shared.presentation.components.customComponents.CustomCircularProgressIndicator
import com.savenko.track.shared.presentation.other.getMonthResID
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock

/**
 * Main card of  [TrackScreenFeed](com.savenko.track.presentation.components.ideasCards.TrackScreenFeedKt.TrackScreenFeed)
 *
 * Displays user-predefined budget, summary of current month expenses
 */
@Composable
fun BudgetFeedCard() {
    val budgetIdeaCardViewModel = koinViewModel<BudgetIdeaCardViewModel>()
    val state = budgetIdeaCardViewModel.budgetCardState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        budgetIdeaCardViewModel.initializeStates()
    }
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val currentMonth = stringResource(getMonthResID(
                    localDate = Clock.System.now().toLocalDate(TimeZone.currentSystemDefault())
                )
            )
            Text(
                text = stringResource(Res.string.budget_main_feed_card, currentMonth),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W500),
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
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp
                                )
                            ) {
                                append(
                                    stringResource(Res.string.planned_main_track_screen)
                                )
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 18.sp, fontWeight = FontWeight.W500
                                )
                            ) {
                                append(
                                    " " + if (state.value.currency.type == CurrencyTypes.FIAT) {
                                        state.value.budget.toDouble().toFormattedCurrency(FIAT_SCALE)
                                    } else {
                                        state.value.budget.toDouble().toFormattedCurrency(CRYPTO_SCALE)
                                    }
                                )
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp
                                )
                            ) {
                                append(
                                    " " + state.value.currency.ticker
                                )
                            }
                        },
                        maxLines = 1
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp
                                )
                            ) {
                                append(
                                    stringResource(Res.string.spent_main_track_screen)
                                )
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 18.sp, fontWeight = FontWeight.W500
                                )
                            ) {
                                append(
                                    " " + if (state.value.currency.type == CurrencyTypes.FIAT) {
                                        state.value.currentExpensesSum.toDouble().toFormattedCurrency(FIAT_SCALE)
                                    } else {
                                        state.value.currentExpensesSum.toDouble().toFormattedCurrency(CRYPTO_SCALE)
                                    }
                                )
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp
                                )
                            ) {
                                append(
                                    " " + state.value.currency.ticker
                                )
                            }
                        },
                        maxLines = 1
                    )

                }
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val circleHeight = 80f
                    CustomCircularProgressIndicator(
                        modifier = Modifier.size(circleHeight.times(1.1f).dp),
                        initialValue = (state.value.budgetExpectancy * 100).toInt(),
                        primaryColor = MaterialTheme.colorScheme.primary,
                        secondaryColor = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
