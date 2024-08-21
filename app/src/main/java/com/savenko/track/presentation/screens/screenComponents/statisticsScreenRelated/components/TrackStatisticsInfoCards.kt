package com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components

import android.util.Range
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
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
import com.savenko.track.data.viewmodels.statistics.StatisticInfoCardsViewModel
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.currency.CurrencyTypes
import com.savenko.track.presentation.components.customComponents.VerticalDashedDivider
import com.savenko.track.presentation.screens.states.core.statisticScreen.StatisticInfoCardsState
import java.util.Date

/**
Contains TrackStatisticsInfoCards and additional functions: SingleFinancialContent, BothFinancialContent
 */
@Composable
fun TrackStatisticsInfoCards(
    modifier: Modifier,
    statisticInfoCardsViewModel: StatisticInfoCardsViewModel,
    financialEntities: FinancialEntities,
    dateRange: Range<Date>
) {
    val state = statisticInfoCardsViewModel.infoCardsState.collectAsState()
    val singleFinancialModifier = Modifier.wrapContentHeight()
    val bothFinancialsModifier = Modifier.height(104.dp)
    LaunchedEffect(key1 = dateRange) {
        statisticInfoCardsViewModel.initializeValues(specifiedTimePeriod = dateRange)
    }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, focusedElevation = 8.dp),
        modifier = modifier
            .then(
                if (financialEntities is FinancialEntities.Both) {
                    bothFinancialsModifier
                } else {
                    singleFinancialModifier
                }
            )
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min)
        ) {
            when (financialEntities) {
                is FinancialEntities.ExpenseFinancialEntity -> {
                    SingleFinancialContent(
                        state = state.value,
                        financialTypes = FinancialTypes.Expense,
                        preferableCurrency = state.value.preferableCurrency
                    )
                }

                is FinancialEntities.IncomeFinancialEntity -> {
                    SingleFinancialContent(
                        state = state.value,
                        financialTypes = FinancialTypes.Income,
                        preferableCurrency = state.value.preferableCurrency
                    )
                }

                is FinancialEntities.Both -> {
                    BothFinancialsContent(state = state.value, preferableCurrency = state.value.preferableCurrency)
                }
            }
        }
    }
}

@Composable
private fun SingleFinancialContent(
    state: StatisticInfoCardsState,
    financialTypes: FinancialTypes,
    preferableCurrency: Currency
) {
    val overallQuantityText = provideAnnotatedString(
        baseText = stringResource(id = R.string.financials_quantity_stats_info_cards),
        mainValue = if (financialTypes == FinancialTypes.Income) {
            state.incomesPeriodQuantity.toString()
        } else {
            state.expensesPeriodQuantity.toString()
        }
    )

    val overallAverageText = provideAnnotatedString(
        baseText = stringResource(
            id = if (financialTypes == FinancialTypes.Income) {
                R.string.average_income_stats_info_cards
            } else {
                R.string.average_expenses_stats_info_cards
            }
        ),
        mainValue = " " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
            FIAT_DECIMAL_FORMAT.format(
                if (financialTypes == FinancialTypes.Income) state.averageIncomeValue else state.averageExpenseValue
            )
        } else {
            CRYPTO_DECIMAL_FORMAT.format(
                if (financialTypes == FinancialTypes.Income) state.averageIncomeValue else state.averageExpenseValue
            )
        },
        suffix = preferableCurrency.ticker
    )

    val overallSummaryText = provideAnnotatedString(
        baseText = stringResource(id = R.string.overall_month_summary),
        mainValue = " " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
            FIAT_DECIMAL_FORMAT.format(
                if (financialTypes == FinancialTypes.Income) state.incomesPeriodSummary else state.expensesPeriodSummary
            )
        } else {
            CRYPTO_DECIMAL_FORMAT.format(
                if (financialTypes == FinancialTypes.Income) state.incomesPeriodSummary else state.expensesPeriodSummary
            )
        },
        suffix = preferableCurrency.ticker
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight().scale(scaleX = 1.0f, scaleY = 0.9f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = stringResource(R.string.period_summary_statistics_info_cards))
        }
        Text(text = overallQuantityText, textAlign = TextAlign.Center)
        Text(text = overallAverageText, textAlign = TextAlign.Center)
        Text(text = overallSummaryText, textAlign = TextAlign.Center)
    }
}

@Composable
private fun BothFinancialsContent(
    state: StatisticInfoCardsState,
    preferableCurrency: Currency
) {
    val expensesQuantityAnnotatedString = provideAnnotatedString(
        baseText = stringResource(id = R.string.expenses),
        mainValue = state.expensesPeriodQuantity.toString(),
    )
    val incomesQuantityAnnotatedString = provideAnnotatedString(
        baseText = stringResource(id = R.string.incomes),
        mainValue = state.incomesPeriodQuantity.toString(),
    )


    val expensesSummaryAnnotatedString = provideAnnotatedString(
        baseText = stringResource(id = R.string.overall_month_summary),
        mainValue = if (preferableCurrency.type == CurrencyTypes.FIAT) {
            FIAT_DECIMAL_FORMAT.format(
                state.expensesPeriodSummary
            )
        } else {
            CRYPTO_DECIMAL_FORMAT.format(
                state.expensesPeriodSummary
            )
        },
        suffix = preferableCurrency.ticker
    )
    val incomesSummaryAnnotatedString = provideAnnotatedString(
        baseText = stringResource(id = R.string.overall_month_summary),
        mainValue = if (preferableCurrency.type == CurrencyTypes.FIAT) {
            FIAT_DECIMAL_FORMAT.format(
                state.incomesPeriodSummary
            )
        } else {
            CRYPTO_DECIMAL_FORMAT.format(
                state.incomesPeriodSummary
            )
        },
        suffix = preferableCurrency.ticker
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .weight(0.45f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = expensesQuantityAnnotatedString, textAlign = TextAlign.Center)
            Text(text = expensesSummaryAnnotatedString, textAlign = TextAlign.Center)
        }
        Column(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxHeight()
                .align(Alignment.CenterVertically)
        ) {
            VerticalDashedDivider(
                Modifier
                    .height(IntrinsicSize.Min)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(0.45f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = incomesQuantityAnnotatedString, textAlign = TextAlign.Center)
            Text(text = incomesSummaryAnnotatedString, textAlign = TextAlign.Center)
        }
    }
}


private fun provideAnnotatedString(
    baseText: String,
    mainValue: String,
    mainValueStyle: SpanStyle = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.W500),
    suffix: String = "",
    suffixStyle: SpanStyle = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.W400)
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 16.sp)) {
            append("$baseText ")
        }
        withStyle(style = mainValueStyle) {
            append("$mainValue ")
        }
        withStyle(style = suffixStyle) {
            append(suffix)
        }
    }
}