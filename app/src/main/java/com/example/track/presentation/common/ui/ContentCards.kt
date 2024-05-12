package com.example.track.presentation.common.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.track.R
import com.example.track.data.other.converters.extractAdditionalDateInformation
import com.example.track.data.other.converters.extractDayOfWeekFromDate
import com.example.track.domain.models.other.CategoryEntity
import com.example.track.domain.models.other.FinancialEntity
import com.example.track.data.viewmodels.mainScreen.ExpenseAndIncomeLazyColumnViewModel
import com.example.track.presentation.common.parser.parseColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue


@Composable
fun FinancialItemCardTypeSimple(
    financialEntity: FinancialEntity,
    expanded: Boolean,
    categoryEntity: CategoryEntity,
    expenseAndIncomeLazyColumnViewModel: ExpenseAndIncomeLazyColumnViewModel
) {
    val density = LocalDensity.current
    val categoryColor = parseColor(categoryEntity.colorId)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(if (expanded) 150.dp else 100.dp)
            .padding(vertical = 8.dp)
            .clickable { expenseAndIncomeLazyColumnViewModel.setExpandedExpenseCard(if (expanded) null else financialEntity) },
        shape = MaterialTheme.shapes.medium
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
                        if (financialEntity.note.isNotEmpty()) {
                            NoteCard(expenseItem = financialEntity)
                        } else {
                            CategoryCard(category = categoryEntity, containerColor = categoryColor)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.Bottom) {
                        val text = remember { mutableStateOf("") }
                        LaunchedEffect(key1 = Unit) {
                            withContext(Dispatchers.IO) {
                                text.value = expenseAndIncomeLazyColumnViewModel.requestSumExpensesInMonthNotion(
                                    financialEntity = financialEntity,
                                    financialCategory = categoryEntity
                                )
                            }
                        }
                        val resultedNotion = if (!expanded) {
                            extractDayOfWeekFromDate(date = financialEntity.date) + ", " + extractAdditionalDateInformation(date = financialEntity.date)
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
                                            append("${categoryEntity.note} this month: ")
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
                                            append(financialEntity.currencyTicker)
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
                        ), exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
                            val notion = remember { mutableStateOf("") }
                            LaunchedEffect(key1 = Unit) {
                                withContext(Dispatchers.IO) {
                                    notion.value = expenseAndIncomeLazyColumnViewModel.requestCountInMonthNotion(
                                        financialEntity = financialEntity,
                                        financialCategory = categoryEntity
                                    )
                                }
                            }
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)) {
                                        append("${categoryEntity.note} Expenses: ")
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
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                ExpenseValueCard(expenseItem = financialEntity, currentCurrencyName = financialEntity.currencyTicker, isExpanded = expanded)
            }
        }
    }
}

@Composable
private fun ExpenseValueCard(expenseItem: FinancialEntity, currentCurrencyName: String, isExpanded: Boolean) {
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
private fun CategoryCard(category: CategoryEntity, containerColor: Color) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = Modifier.wrapContentSize(),
        colors = CardColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = containerColor,
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
private fun NoteCard(expenseItem: FinancialEntity) {
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
