package com.savenko.track.presentation.components.ideasCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.savenko.track.data.other.converters.dates.formatDateWithoutYear
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.currency.CurrencyTypes
import com.savenko.track.domain.models.idea.IncomePlans

/*  Contains Card used in expense screen feed to show income plan entity  */
@Composable
fun IncomePlanIdeaCard(incomePlans: IncomePlans, completionValue: Float, preferableCurrency: Currency) {
    val localContext = LocalContext.current
    var plannedText by remember { mutableStateOf(buildAnnotatedString { }) }
    var completedText by remember { mutableStateOf(buildAnnotatedString { }) }
    LaunchedEffect(key1 = Unit) {
        plannedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(
                    localContext.getString(R.string.planned)
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                )
            ) {
                append(
                    " " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(incomePlans.goal)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(incomePlans.goal)
                    }
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(
                    " " + preferableCurrency.ticker
                )
            }
        }
        completedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(
                    localContext.getString(R.string.completed_for)
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                )
            ) {
                append(
                    " " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(completionValue)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(completionValue)
                    }
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(
                    " " + preferableCurrency.ticker
                )
            }
        }
    }
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.income_plan),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Spacer(Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp), verticalArrangement =
            Arrangement.SpaceEvenly

        ) {
            if (incomePlans.endDate != null) {
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = plannedText
                    )
                    Text(
                        text = completedText
                    )
                }
            } else {
                Text(
                    text = plannedText
                )
                Text(
                    text = completedText
                )
            }
            if (incomePlans.endDate != null) {
                Text(
                    text = stringResource(
                        R.string.preferable_period_income_plan_card,
                        formatDateWithoutYear(incomePlans.startDate),
                        formatDateWithoutYear(incomePlans.endDate)
                    ), textAlign = TextAlign.Center
                )
            }
        }
    }
}