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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R

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
                text = stringResource(R.string.income_plan),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 2.dp), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(
                    R.string.planned_income_plan_card, if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(incomePlans.goal)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(incomePlans.goal)
                    }, preferableCurrency.ticker
                )
            )
            Text(
                text = stringResource(
                    R.string.completed_for_income_plan_card, if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(completionValue)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(completionValue)
                    }, preferableCurrency.ticker
                )
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