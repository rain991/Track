package com.example.track.presentation.components.mainScreen.feed.ideasCards

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.track.data.other.constants.CRYPTO_DECIMAL_FORMAT
import com.example.track.data.other.constants.FIAT_DECIMAL_FORMAT
import com.example.track.data.other.converters.formatDateWithoutYear
import com.example.track.domain.models.currency.Currency
import com.example.track.domain.models.currency.CurrencyTypes
import com.example.track.domain.models.idea.IncomePlans

/*  Contains Card used in expense screen feed to show income plan entity  */
@Composable
fun IncomePlanIdeaCard(incomePlans: IncomePlans, completionValue: Float, preferableCurrency: Currency) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Income plan",
                style = MaterialTheme.typography.titleSmall
            )
        }
        Spacer(Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 2.dp), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Planned ${
                    if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(incomePlans.goal)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(incomePlans.goal)
                    }
                } ${preferableCurrency.ticker}"
            )
            Text(
                text = "Completed for ${
                    if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(completionValue)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(completionValue)
                    }
                } ${preferableCurrency.ticker}"
            )
            if (incomePlans.endDate != null) {
                Text(
                    text = "Preferable period: ${formatDateWithoutYear(incomePlans.startDate)} - ${formatDateWithoutYear(incomePlans.endDate)}"
                )
            }
        }
    }
}