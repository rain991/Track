package com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.mainScreenInfoCards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CRYPTO_DECIMAL_FORMAT
import com.savenko.track.data.other.constants.FIAT_DECIMAL_FORMAT
import com.savenko.track.data.viewmodels.mainScreen.feedCards.TrackScreenInfoCardsViewModel
import com.savenko.track.domain.models.currency.CurrencyTypes
import org.koin.androidx.compose.koinViewModel

/**  Contains 2 cards that showing overall financial stats.
 *   @param isScrolledBelow regulates TrackScreenInfoCards visibility
 *   @param isExpenseCardSelected selected card has additional gradient border
 *   @param onExpenseCardClick on expense card click callback
 *   @param onIncomeCardClick  on income card click callback
 */
@Composable
fun TrackScreenInfoCards(
    isScrolledBelow: Boolean,
    isExpenseCardSelected: Boolean = true,
    onExpenseCardClick: () -> Unit,
    onIncomeCardClick: () -> Unit
) {
    val viewModel = koinViewModel<TrackScreenInfoCardsViewModel>()
    val screenState = viewModel.cardsState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.initializeValues()
    }
    AnimatedVisibility(visible = !isScrolledBelow) {
        val gradientColor1 = MaterialTheme.colorScheme.primary
        val gradientColor2 = MaterialTheme.colorScheme.tertiary
        val borderBrush = remember {
            Brush.linearGradient(
                listOf(
                    gradientColor1,
                    gradientColor2
                )
            )
        }
        val expenseCardBorderWidth: Float by animateFloatAsState(
            targetValue = if (isExpenseCardSelected) {
                2.0f
            } else {
                0.0f
            }, label = "expenseCardBorderWidth"
        )
        val incomeCardBorderWidth: Float by animateFloatAsState(
            targetValue = if (!isExpenseCardSelected) {
                2.0f
            } else {
                0.0f
            }, label = "incomeCardBorderWidth"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .height(120.dp)
                    .weight(0.5f)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                    .then(
                        if (expenseCardBorderWidth > 0) Modifier.border(
                            width = expenseCardBorderWidth.dp,
                            borderBrush,
                            RoundedCornerShape(16.dp)
                        ) else Modifier
                    )
                    .clickable {
                        onExpenseCardClick()
                    },
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = stringResource(id = R.string.expenses_lazy_column),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                    if (screenState.value.currentMonthExpensesSum > 0) {
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(
                                    if (screenState.value.preferableCurrency.type == CurrencyTypes.FIAT) {
                                        FIAT_DECIMAL_FORMAT.format(screenState.value.currentMonthExpensesSum)
                                    } else {
                                        CRYPTO_DECIMAL_FORMAT.format(screenState.value.currentMonthExpensesSum)
                                    }
                                )
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(" " + screenState.value.preferableCurrency.ticker)
                            }
                        })

                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(screenState.value.currentMonthExpensesCount.toString() + " ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(
                                    pluralStringResource(
                                        R.plurals.operations_screen_info_cards,
                                        screenState.value.currentMonthExpensesCount,
                                        screenState.value.currentMonthExpensesCount
                                    )
                                )
                            }
                        })
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                stringResource(R.string.warning_message_info_cards),
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .height(120.dp)
                    .weight(0.5f)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                    .then(
                        if (incomeCardBorderWidth > 0.0f) Modifier.border(
                            width = incomeCardBorderWidth.dp,
                            borderBrush,
                            RoundedCornerShape(16.dp)
                        ) else Modifier
                    )
                    .clickable {
                        onIncomeCardClick()
                    },
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = stringResource(R.string.incomes),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                    if (screenState.value.currentMonthIncomesSum > 0) {
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(
                                    if (screenState.value.preferableCurrency.type == CurrencyTypes.FIAT) {
                                        FIAT_DECIMAL_FORMAT.format(screenState.value.currentMonthIncomesSum)
                                    } else {
                                        CRYPTO_DECIMAL_FORMAT.format(screenState.value.currentMonthIncomesSum)
                                    }
                                )
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(" " + screenState.value.preferableCurrency.ticker)
                            }
                        })

                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(screenState.value.currentMonthIncomesCount.toString())
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(
                                    " " +
                                            pluralStringResource(
                                                R.plurals.operations_screen_info_cards,
                                                screenState.value.currentMonthIncomesCount,
                                                screenState.value.currentMonthIncomesCount
                                            )
                                )
                            }
                        })
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                stringResource(R.string.warning_message_info_cards),
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}