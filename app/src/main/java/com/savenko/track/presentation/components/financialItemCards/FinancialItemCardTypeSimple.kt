package com.savenko.track.presentation.components.financialItemCards

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.savenko.track.R
import com.savenko.track.data.implementations.currencies.CurrencyListRepositoryImpl
import com.savenko.track.data.other.constants.CRYPTO_DECIMAL_FORMAT
import com.savenko.track.data.other.constants.FIAT_DECIMAL_FORMAT
import com.savenko.track.data.other.converters.dates.formatDateWithoutYear
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.currency.CurrencyTypes
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.presentation.UiText.DatabaseStringResourcesProvider
import com.savenko.track.presentation.other.colors.parseColor
import kotlinx.coroutines.flow.first
import org.koin.compose.koinInject
import java.util.Calendar
import java.util.Locale

/*  FinancialItemCardTypeSimple used in expenses screen for single expense or income entity in lazy column
    Also contains ExpenseValueCard(rectangle on right with values and currency), CategoryCard(colored category name), NoteCard(contains notion)  */
@Composable
fun FinancialItemCardTypeSimple(
    financialEntity: FinancialEntity,
    categoryEntity: CategoryEntity,
    expanded: Boolean,
    financialEntityMonthSummary: Float,
    countOfFinancialEntities: Int,
    preferableCurrency: Currency,
    onClick: () -> Unit
) {
    val locale = Locale.getDefault()
    val density = LocalDensity.current
    val categoryColor = parseColor(categoryEntity.colorId)
    val databaseStringResourcesProvider = DatabaseStringResourcesProvider()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(if (expanded) 150.dp else 100.dp)
            .padding(vertical = 8.dp)
            .clickable { onClick()   /*expenseAndIncomeLazyColumnViewModel.setExpandedExpenseCard(if (expanded) null else financialEntity) */ },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = if (expanded) {
                            Arrangement.Center
                        } else {
                            Arrangement.Start
                        }
                    ) {
                        val resultedNotion = if (!expanded) {
                            formatDateWithoutYear(financialEntity.date)
                        } else {
                            if (preferableCurrency.type == CurrencyTypes.FIAT) {
                                FIAT_DECIMAL_FORMAT.format(financialEntityMonthSummary)
                            } else {
                                CRYPTO_DECIMAL_FORMAT.format(financialEntityMonthSummary)
                            }
                        }
                        AnimatedContent(
                            targetState = resultedNotion,
                            label = "horizontalTextChange",
                            transitionSpec = {
                                slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
                            }) { resultedNotion ->
                            val calendar = Calendar.getInstance().apply {
                                time = financialEntity.date
                            }
                            val monthName =
                                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
                            Text(
                                text = if (!expanded) {
                                    buildAnnotatedString {
                                        append(resultedNotion.toString())
                                    }
                                } else {
                                    buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        ) {
                                            val categoryName =
                                                stringResource(
                                                    id = databaseStringResourcesProvider.provideStringResource(
                                                        categoryEntity
                                                    )
                                                )
                                            append(
                                                stringResource(
                                                    R.string.specified_category_fin_item_card,
                                                    categoryName,
                                                    monthName
                                                )
                                            )
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                                            )
                                        ) {
                                            append(resultedNotion.toString())
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        ) {
                                            append(" ")
                                            append(preferableCurrency.ticker)
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
                            .fillMaxWidth()
                            .padding(end = 16.dp), horizontalArrangement = Arrangement.Center
                    ) {
                        AnimatedVisibility(visible = expanded, enter = slideInHorizontally {
                            with(density) { -60.dp.roundToPx() }
                        } + expandHorizontally(
                            expandFrom = Alignment.Start
                        ) + fadeIn(
                            initialAlpha = 0.3f
                        ), exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    ) {
                                        val categoryName = if (categoryEntity.isDefault()) {
                                            stringResource(
                                                id = databaseStringResourcesProvider.provideStringResource(
                                                    categoryEntity
                                                )
                                            )
                                        } else {
                                            categoryEntity.note
                                        }

                                        append(
                                            if (categoryEntity is ExpenseCategory) {
                                                stringResource(
                                                    R.string.expenses_fin_item_card,
                                                    categoryName
                                                )
                                            } else {
                                                stringResource(
                                                    R.string.incomes_fin_item_card,
                                                    categoryName
                                                )
                                            }

                                        )
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    ) {
                                        append(countOfFinancialEntities.toString())
                                    }
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                ExpenseValueCard(
                    financialEntity = financialEntity,
                    currentCurrencyName = financialEntity.currencyTicker,
                    isExpanded = expanded
                )
            }
        }
    }
}

@Composable
private fun ExpenseValueCard(
    financialEntity: FinancialEntity,
    currentCurrencyName: String,
    isExpanded: Boolean
) {
    val currencyListRepositoryImpl = koinInject<CurrencyListRepositoryImpl>()
    var listOfCurrencies = listOf<Currency>()
    LaunchedEffect(key1 = Unit) {
        listOfCurrencies = currencyListRepositoryImpl.getCurrencyList().first()
    }
    val currentCurrency =
        listOfCurrencies.firstOrNull { it.ticker == financialEntity.currencyTicker }
    val currencyType = currentCurrency?.type



    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 22.dp, focusedElevation = 14.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .size(if (isExpanded) 110.dp else 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (currencyType == CurrencyTypes.FIAT) {
                    FIAT_DECIMAL_FORMAT.format(financialEntity.value)
                } else {
                    CRYPTO_DECIMAL_FORMAT.format(financialEntity.value)
                }, style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 24.sp
                )
            )
            Text(text = currentCurrencyName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun CategoryCard(category: CategoryEntity, containerColor: Color) {
    val databaseStringResourcesProvider = DatabaseStringResourcesProvider()
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
                text = if (category.isDefault()) {
                    stringResource(
                        id = databaseStringResourcesProvider.provideStringResource(
                            category
                        )
                    )
                } else {
                    category.note
                },
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun NoteCard(expenseItem: FinancialEntity) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = Modifier.wrapContentSize(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Box(modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            Text(
                text = if (expenseItem.note.length < 8) stringResource(
                    R.string.note_exp_list,
                    expenseItem.note
                ) else expenseItem.note,
                style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
            )
        }
    }
}