package com.savenko.track.shared.presentation.components.ideasCards

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.Fill
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.shared.data.other.constants.CRYPTO_SCALE
import com.savenko.track.shared.data.other.constants.FIAT_SCALE
import com.savenko.track.shared.data.other.constants.incomePlanSpecificColor
import com.savenko.track.shared.data.other.constants.toFormattedCurrency
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.models.currency.CurrencyTypes
import com.savenko.track.shared.domain.models.idea.IncomePlans
import com.savenko.track.shared.presentation.other.formatDateWithYear
import com.savenko.track.shared.presentation.other.formatDateWithoutYear
import com.savenko.track.shared.presentation.themes.purpleGreyTheme.purpleGreyNew_DarkColorScheme
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

/*  Contains Card used in expense screen feed to show income plan entity  */
@Composable
fun IncomePlanIdeaCard(
    incomePlans: IncomePlans,
    completionValue: Float,
    preferableCurrency: Currency
) {
    val plannedLabel = stringResource(Res.string.planned)
    val completedLabel = stringResource(Res.string.completed_for)
    var plannedText by remember { mutableStateOf(buildAnnotatedString { }) }
    var completedText by remember { mutableStateOf(buildAnnotatedString { }) }
    LaunchedEffect(preferableCurrency, incomePlans.goal, completionValue, plannedLabel, completedLabel) {
        plannedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(
                    plannedLabel
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                )
            ) {
                append(
                    " " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        incomePlans.goal.toDouble().toFormattedCurrency(FIAT_SCALE)
                    } else {
                        incomePlans.goal.toDouble().toFormattedCurrency(CRYPTO_SCALE)
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
                    completedLabel
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                )
            ) {
                append(
                    " " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        completionValue.toDouble().toFormattedCurrency(FIAT_SCALE)
                    } else {
                        completionValue.toDouble().toFormattedCurrency(CRYPTO_SCALE)
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
            .padding(horizontal = 8.dp),
        border = BorderStroke((1.2).dp, incomePlanSpecificColor), shape = RoundedCornerShape(16.dp)
    ) {
        Canvas(modifier = Modifier.wrapContentSize()) {
            val arcSize = 70.dp.toPx()
            drawArc(
                color = incomePlanSpecificColor,
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = true,
                topLeft = androidx.compose.ui.geometry.Offset(-(arcSize / 2), -(arcSize / 2)),
                size = androidx.compose.ui.geometry.Size(arcSize, arcSize),
                style = Fill
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp), horizontalArrangement = Arrangement.Center
        ) {
            Card(
                colors = CardColors(
                    containerColor = incomePlanSpecificColor,
                    contentColor = purpleGreyNew_DarkColorScheme.onSurfaceVariant,
                    disabledContainerColor = incomePlanSpecificColor,
                    disabledContentColor = purpleGreyNew_DarkColorScheme.onSurfaceVariant
                ), modifier = Modifier.scale(0.8f)
            ) {
                Text(
                    text = stringResource(Res.string.income_plan),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W500),
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp), verticalArrangement = Arrangement.SpaceEvenly

        ) {
            if (incomePlans.endDate != null) {
                SpecifiedEndDateContent(
                    incomePlans = incomePlans,
                    plannedText = plannedText,
                    completedText = completedText
                )
            } else {
                NonSpecifiedEndDateContent(
                    incomePlans = incomePlans,
                    plannedText = plannedText,
                    completedText = completedText
                )
            }
        }
    }
}

@Composable
private fun SpecifiedEndDateContent(
    incomePlans: IncomePlans,
    plannedText: AnnotatedString,
    completedText: AnnotatedString
) {
    val currentTimeZone = TimeZone.currentSystemDefault()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = plannedText
        )
        Text(
            text = completedText
        )
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = stringResource(
                Res.string.preferable_period_income_plan_card,
                formatDateWithoutYear(
                    date = Instant.fromEpochMilliseconds(incomePlans.startDate)
                        .toLocalDateTime(currentTimeZone)
                ),
                formatDateWithoutYear(
                    Instant.fromEpochMilliseconds(incomePlans.endDate!!)
                        .toLocalDateTime(currentTimeZone)
                ),
            ), textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun NonSpecifiedEndDateContent(
    incomePlans: IncomePlans,
    plannedText: AnnotatedString,
    completedText: AnnotatedString
) {
    val currentTimeZone = TimeZone.currentSystemDefault()
    Text(
        text = plannedText
    )
    Text(
        text = completedText
    )
    Text(
        text = stringResource(
            Res.string.plan_started_income_plan_card,
            formatDateWithYear(
                Instant.fromEpochMilliseconds(incomePlans.startDate)
                    .toLocalDateTime(currentTimeZone)
            ),
        ), textAlign = TextAlign.Center
    )
}
