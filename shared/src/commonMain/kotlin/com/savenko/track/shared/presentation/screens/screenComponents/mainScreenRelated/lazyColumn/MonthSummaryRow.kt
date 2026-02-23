package com.savenko.track.shared.presentation.screens.screenComponents.mainScreenRelated.lazyColumn

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.shared.data.other.constants.CRYPTO_SCALE
import com.savenko.track.shared.data.other.constants.FIAT_SCALE
import com.savenko.track.shared.data.other.constants.toFormattedCurrency
import com.savenko.track.shared.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.models.currency.CurrencyTypes
import com.savenko.track.shared.presentation.components.customComponents.HorizontalDashedDivider


/**
 * MonthSummaryRow creates summary of user month financials
 *
 * @param monthName month name of summary row
 * @param summary summary of predefined period
 * @param quantity quantity of financials in predefined period
 * @param preferableCurrency user preferable currency
 * @param financialTypes type of financials
 */
@Composable
fun MonthSummaryRow(
    monthName: String,
    modifier: Modifier,
    summary: Float,
    quantity: Int,
    preferableCurrency: Currency,
    financialTypes: FinancialTypes
) {
    var quantityText by remember { mutableStateOf(buildAnnotatedString { }) }
    var summaryText by remember { mutableStateOf(buildAnnotatedString { }) }
    quantityText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 16.sp
            )
        ) {
            append(
                when (financialTypes) {
                    FinancialTypes.Expense -> {
                        stringResource(Res.string.total_expenses_month_summary)
                    }

                    FinancialTypes.Income -> {
                        stringResource(Res.string.total_incomes_month_summary)
                    }
                }
            )
        }
        withStyle(
            style = SpanStyle(
                fontSize = 18.sp, fontWeight = FontWeight.W500
            )
        ) {
            append(" $quantity")
        }
    }

    summaryText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 16.sp
            )
        ) {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(stringResource(Res.string.overall_month_summary))
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.W500
                )
            ) {
              val formatedSummary = if (preferableCurrency.type == CurrencyTypes.FIAT) {
                    summary.toDouble().toFormattedCurrency(FIAT_SCALE)
                } else {
                    summary.toDouble().toFormattedCurrency(CRYPTO_SCALE)
                }
                append(" $formatedSummary")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.W500
                )
            ) {
                append(
                    " " + preferableCurrency.ticker
                )
            }
        }
    }
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight().padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDashedDivider(
                modifier = Modifier.fillMaxWidth(), dashWidth = 16.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = stringResource(Res.string.month_summary_header, monthName),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W500)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = quantityText
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = summaryText)
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDashedDivider(
                modifier = Modifier.fillMaxWidth(), dashWidth = 16.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

