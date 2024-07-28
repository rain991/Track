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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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
                .padding(horizontal = 8.dp), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp
                        )
                    ) {
                        append(
                            stringResource(id = R.string.planned_income_plan_card)
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
            )
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp
                    )
                ) {
                    append(
                        stringResource(id = R.string.completed_for_income_plan_card)
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
            )
            if (incomePlans.endDate != null) {
                Text(
                    text = stringResource(
                        R.string.preferable_period_income_plan_card,
                        formatDateWithoutYear(incomePlans.startDate),
                        formatDateWithoutYear(incomePlans.endDate)
                    )
                )
            }
        }
    }
}