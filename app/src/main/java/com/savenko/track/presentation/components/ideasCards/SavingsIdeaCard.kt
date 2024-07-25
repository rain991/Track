package com.savenko.track.presentation.components.ideasCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CRYPTO_DECIMAL_FORMAT
import com.savenko.track.data.other.constants.FIAT_DECIMAL_FORMAT
import com.savenko.track.data.viewmodels.mainScreen.feed.AddToSavingIdeaDialogViewModel
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.currency.CurrencyTypes
import com.savenko.track.domain.models.idea.Savings

@Composable
fun SavingsIdeaCard(savings: Savings, preferableCurrency: Currency, addToSavingIdeaDialogViewModel: AddToSavingIdeaDialogViewModel) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = savings.label, style = MaterialTheme.typography.headlineSmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(R.string.saving), style = MaterialTheme.typography.titleMedium)
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
                            text = stringResource(
                                R.string.planned_savings_idea_card, if (preferableCurrency.type == CurrencyTypes.FIAT) {
                                    FIAT_DECIMAL_FORMAT.format(savings.goal)
                                } else {
                                    CRYPTO_DECIMAL_FORMAT.format(savings.goal)
                                }, preferableCurrency.ticker
                            ),
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
                            text = stringResource(R.string.completed_for_savings_idea_card) + " ${
                                if (preferableCurrency.type == CurrencyTypes.FIAT) {
                                    FIAT_DECIMAL_FORMAT.format(savings.value)
                                } else {
                                    CRYPTO_DECIMAL_FORMAT.format(savings.value)
                                }
                            } ${preferableCurrency.ticker}", textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.36f)
                        .fillMaxHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { addToSavingIdeaDialogViewModel.setCurrentSaving(savings) }) {
                        Text(text = stringResource(id = R.string.add))
                    }
                }
            }
        }
    }
}