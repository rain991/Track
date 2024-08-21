package com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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

/**
Contains TrackStatisticsInfoCards and additional functions: SingleFinancialContent, BothFinancialContent
 */
@Composable
fun TrackStatisticsInfoCards(
    statisticInfoCardsViewModel: StatisticInfoCardsViewModel,
    financialEntities: FinancialEntities
) {
    val state = statisticInfoCardsViewModel.infoCardsState.collectAsState()
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, focusedElevation = 8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Box(modifier = Modifier.padding(8.dp).height(IntrinsicSize.Min)) {
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
            .height(IntrinsicSize.Min),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Period summary")
        }
        Text(text = overallQuantityText)
        Text(text = overallAverageText)
        Text(text = overallSummaryText)
    }
}

@Composable
private fun BothFinancialsContent(
    state: StatisticInfoCardsState,
    preferableCurrency: Currency
) {
    val expensesQuantityAnnotatedString = provideAnnotatedString(
        baseText = "Expenses",
        mainValue = state.expensesPeriodQuantity.toString(),
        suffix = preferableCurrency.ticker
    )
    val incomesQuantityAnnotatedString = provideAnnotatedString(
        baseText = "Incomes",
        mainValue = state.incomesPeriodQuantity.toString(),
        suffix = preferableCurrency.ticker
    )
    val expensesSummaryAnnotatedString = provideAnnotatedString(
        baseText = stringResource(id = R.string.overall_month_summary),
        mainValue = state.expensesPeriodSummary.toString(),
        suffix = preferableCurrency.ticker
    )
    val incomesSummaryAnnotatedString = provideAnnotatedString(
        baseText = stringResource(id = R.string.overall_month_summary),
        mainValue = state.incomesPeriodSummary.toString(),
        suffix = preferableCurrency.ticker
    )

    Row(
        modifier = Modifier
            .fillMaxWidth().height(IntrinsicSize.Max)
    ) {
        Column(modifier = Modifier.weight(0.45f)) {
            Text(text = expensesQuantityAnnotatedString)
            Text(text = expensesSummaryAnnotatedString)
        }
        Column(
            modifier = Modifier
                .weight(0.1f)
                .align(Alignment.CenterVertically)
        ) {
            VerticalDashedDivider(Modifier.height(IntrinsicSize.Min))
        }
        Column(modifier = Modifier.weight(0.45f)) {
            Text(text = incomesQuantityAnnotatedString)
            Text(text = incomesSummaryAnnotatedString)
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