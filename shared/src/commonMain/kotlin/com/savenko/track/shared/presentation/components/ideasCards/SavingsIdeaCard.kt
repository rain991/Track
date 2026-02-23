package com.savenko.track.shared.presentation.components.ideasCards

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.Fill
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.shared.data.other.constants.CRYPTO_SCALE
import com.savenko.track.shared.data.other.constants.FIAT_SCALE
import com.savenko.track.shared.data.other.constants.savingsSpecificColor
import com.savenko.track.shared.data.other.constants.toFormattedCurrency
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.AddToSavingIdeaDialogViewModel
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.models.currency.CurrencyTypes
import com.savenko.track.shared.domain.models.idea.Savings
import com.savenko.track.shared.presentation.themes.purpleGreyTheme.purpleGreyNew_DarkColorScheme

@Composable
fun SavingsIdeaCard(
    savings: Savings,
    preferableCurrency: Currency,
    addToSavingIdeaDialogViewModel: AddToSavingIdeaDialogViewModel
) {
    val plannedLabel = stringResource(Res.string.planned)
    val completedLabel = stringResource(Res.string.completed_for)
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp)  ,border = BorderStroke((1.2).dp, savingsSpecificColor
    ), shape = RoundedCornerShape(16.dp)
    ) {
        Canvas(modifier = Modifier.wrapContentSize()) {
            val arcSize = 70.dp.toPx()
            drawArc(
                color = savingsSpecificColor,
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = true,
                topLeft = androidx.compose.ui.geometry.Offset(-(arcSize / 2), -(arcSize / 2)),
                size = androidx.compose.ui.geometry.Size(arcSize, arcSize),
                style = Fill
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = savings.label,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W500),
                    maxLines = 1
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .scale(0.8f), horizontalArrangement = Arrangement.Center) {
                Card(
                    colors = CardColors(
                        containerColor = savingsSpecificColor,
                        contentColor = purpleGreyNew_DarkColorScheme.onSurfaceVariant,
                        disabledContainerColor = savingsSpecificColor,
                        disabledContentColor = purpleGreyNew_DarkColorScheme.onSurfaceVariant
                    ), modifier = Modifier
                ) {
                    Text(
                        text = stringResource(Res.string.saving), style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(0.64f)
                        .wrapContentWidth()
                        .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = buildAnnotatedString {
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
                                            savings.goal.toDouble().toFormattedCurrency(FIAT_SCALE)
                                        } else {
                                            savings.goal.toDouble().toFormattedCurrency(CRYPTO_SCALE)
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
                            },
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = buildAnnotatedString {
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
                                            savings.value.toDouble().toFormattedCurrency(FIAT_SCALE)
                                        } else {
                                            savings.value.toDouble().toFormattedCurrency(CRYPTO_SCALE)
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
                            }, textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.36f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { addToSavingIdeaDialogViewModel.setCurrentSaving(savings) }) {
                        Text(text = stringResource(Res.string.add))
                    }
                }
            }
        }
    }
}
