package com.example.track.presentation.components.mainScreen.TrackScreenInfoCards

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.track.data.viewmodels.mainScreen.ExpenseAndIncomeLazyColumnViewModel
import com.example.track.data.viewmodels.mainScreen.TrackScreenInfoCardsViewModel
import org.koin.androidx.compose.koinViewModel

/*  Contains 2 additional cards above lazy column in main screen. Those card show overall stats about expenses and income relatively.   */
@Composable
fun TrackScreenInfoCards() {
    val viewModel = koinViewModel<TrackScreenInfoCardsViewModel>()
    val screenState = viewModel.cardsState.collectAsState()
    val expenseAndIncomeLazyColumnViewModel = koinViewModel<ExpenseAndIncomeLazyColumnViewModel>()
    val isScrolledBelow = expenseAndIncomeLazyColumnViewModel.isScrolledBelow.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.initializeValues()
    }
    AnimatedVisibility(visible = !isScrolledBelow.value) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .height(120.dp)
                    .weight(0.5f)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "Expenses", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold))
                    }
                    if (screenState.value.currentMonthExpensesSum > 0) {
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(screenState.value.currentMonthExpensesSum.toString())
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
                                append(screenState.value.currentMonthExpensesCount.toString())
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(" operations")
                            }
                        })
                    } else {
                        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
                            Text("0 operations this month", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium), textAlign = TextAlign.Center)
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .height(120.dp)
                    .weight(0.5f)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "Incomes", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold))
                    }
                    if (screenState.value.currentMonthIncomesSum > 0) {
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(screenState.value.currentMonthIncomesSum.toString())
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
                                append(" operations")
                            }
                        })
                    } else {
                        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
                            Text("0 operations this month", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium), textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}