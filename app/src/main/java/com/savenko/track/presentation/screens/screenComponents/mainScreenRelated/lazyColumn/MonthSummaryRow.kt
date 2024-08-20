package com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.lazyColumn


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.R
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.models.currency.Currency


@Composable
fun MonthSummaryRow(
    modifier: Modifier,
    summary: Float,
    quantity: Int,
    monthName: String,
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
                        stringResource(R.string.total_expenses_month_summary)
                    }

                    FinancialTypes.Income -> {
                        stringResource(R.string.total_incomes_month_summary)
                    }

                    else -> {
                        stringResource(R.string.total_operations_month_summary)
                    }
                }
            )
        }
        withStyle(
            style = SpanStyle(
                fontSize = 18.sp, fontWeight = FontWeight.W500
            )
        ) {
            append(quantity.toString())
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
                append(stringResource(R.string.overall_month_summary))
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.W500
                )
            ) {
                append(" $summary")
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
                .wrapContentHeight()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            DashedDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), dashWidth = 16.dp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = stringResource(R.string.month_summary_header, monthName),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W500)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = quantityText
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = summaryText)
            Spacer(modifier = Modifier.height(4.dp))
            DashedDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), dashWidth = 16.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun DashedDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    dashWidth: Dp = 8.dp,
    dashGap: Dp = 8.dp,
    thickness: Dp = 1.dp,
) {
    Row(
        modifier = modifier.then(Modifier.height(thickness))
    ) {
        val configuration = LocalConfiguration.current
        val dashCount = configuration.screenWidthDp.dp.value.div(dashWidth.value + dashGap.value).toInt()
        repeat(dashCount) {
            Box(
                modifier = Modifier
                    .width(dashWidth)
                    .fillMaxHeight()
                    .background(color)
            )
            Spacer(modifier = Modifier.width(dashGap))
        }
    }
}