package com.example.expensetracker.presentation.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.data.constants.CURRENCY_DEFAULT
import com.example.expensetracker.data.converters.extractAdditionalDateInformation
import com.example.expensetracker.data.converters.extractDayOfWeekFromDate
import com.example.expensetracker.data.implementations.CurrenciesPreferenceRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import com.example.expensetracker.data.viewmodels.mainScreen.ExpensesLazyColumnViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject
import kotlin.math.absoluteValue


@Composable
fun ExpensesCardTypeSimple(
    expenseItem: ExpenseItem,
    expenseCategory: ExpenseCategory,
    expensesLazyColumnViewModel: ExpensesLazyColumnViewModel
) {
    val currenciesPreferenceRepositoryImpl = koinInject<CurrenciesPreferenceRepositoryImpl>()
    var expanded by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val currentCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().collectAsState(initial = CURRENCY_DEFAULT)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(if (expanded) 150.dp else 100.dp)
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded }, shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 4.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center, modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                    ) {
                        if (expenseItem.note.isNotEmpty()) {
                            noteCard(expenseItem = expenseItem)
                        } else {
                            categoryCard(category = expenseCategory)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.Bottom) {
                        val text = remember { mutableStateOf("") }
                        LaunchedEffect(key1 = Unit) {
                            withContext(Dispatchers.IO) {
                                text.value = expensesLazyColumnViewModel.requestSumExpensesInMonthNotion(
                                    expenseItem = expenseItem,
                                    expenseCategory = expenseCategory
                                )
                            }
                        }
                        val resultedNotion = if (!expanded) {
                            extractDayOfWeekFromDate(date = expenseItem.date) + ", " + extractAdditionalDateInformation(date = expenseItem.date)
                        } else {
                            text.value
                        }
                        AnimatedContent(targetState = resultedNotion, label = "horizontalTextChange", transitionSpec = {
                            slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
                        }) {
                            Text(
                                text = if (!expanded) {
                                    buildAnnotatedString {
                                        append(it)
                                    }
                                } else {
                                    buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)) {
                                            append("${expenseCategory.note} this month: ")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                                            )
                                        ) {
                                            append(it)
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                                            )
                                        ) {
                                            append(" ")
                                            append(currentCurrency.value!!.ticker)
                                        }

                                    }
                                }, textAlign = TextAlign.Center
                            )
                        }
                    }
                    if (expanded) {
                        Spacer(modifier = Modifier.weight(0.7f))
                    }
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 6.dp)
                    ) {
                        AnimatedVisibility(visible = expanded, enter = slideInHorizontally {
                            with(density) { -60.dp.roundToPx() }
                        } + expandHorizontally(
                            expandFrom = Alignment.Start
                        ) + fadeIn(
                            initialAlpha = 0.3f
                        ), exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()) {
                            val notion = remember { mutableStateOf("") }
                            LaunchedEffect(key1 = Unit) {
                                withContext(Dispatchers.IO) {
                                    notion.value = expensesLazyColumnViewModel.requestCountInMonthNotion(
                                        expenseItem = expenseItem,
                                        expenseCategory = expenseCategory
                                    )
                                }
                            }
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)) {
                                        append("${expenseCategory.note} expenses: ")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    ) {
                                        append(notion.value)
                                    }
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(2.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    if (expanded) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                ExpenseValueCard(expenseItem = expenseItem, currentCurrencyName = currentCurrency.value!!.ticker, isExpanded = expanded)
            }
        }
    }
}

@Composable
private fun ExpenseValueCard(expenseItem: ExpenseItem, currentCurrencyName: String, isExpanded: Boolean) {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 22.dp, focusedElevation = 14.dp), modifier = Modifier.padding(4.dp)) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .size(if (isExpanded) 110.dp else 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (expenseItem.value.absoluteValue % 1.0 >= 0.001) expenseItem.value.toString() else expenseItem.value.toInt()
                    .toString(), style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 24.sp
                )
            )
            Text(text = currentCurrencyName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun categoryCard(category: ExpenseCategory) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = Modifier.wrapContentSize(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ), shape = MaterialTheme.shapes.small
    ) {
        Box(modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            Text(
                text = category.note,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
private fun noteCard(expenseItem: ExpenseItem) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp), modifier = Modifier.wrapContentSize(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ), shape = MaterialTheme.shapes.small
    ) {
        Box(modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            Text(
                text = if (expenseItem.note.length < 8) stringResource(R.string.note_exp_list, expenseItem.note) else expenseItem.note,
                style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
            )
        }
    }
}
