package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.personalPreferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CRYPTO_DECIMAL_FORMAT
import com.savenko.track.data.other.constants.FIAT_DECIMAL_FORMAT
import com.savenko.track.data.viewmodels.settingsScreen.personal.PersonalStatsViewModel
import com.savenko.track.domain.models.currency.CurrencyTypes

/**
 * Statistics in [personal settings screen](com.savenko.track.presentation.screens.screenComponents.additional.PersonalSettingsScreenComponent)
 */
@Composable
fun PersonalSettingsStatistics(viewModel: PersonalStatsViewModel) {
    val statsState = viewModel.personalStatsState.collectAsState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.your_stats_personal_settings_screen),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (statsState.value.allTimeExpensesCount == 0) {
                Text(
                    text = stringResource(R.string.not_added_any_expenses_yet_personal_settings_screen),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Text(
                    text = stringResource(
                        R.string.you_have_already_added_expenses_worth_of,
                        statsState.value.allTimeExpensesCount,
                        if (statsState.value.preferableCurrency.type == CurrencyTypes.FIAT) {
                            FIAT_DECIMAL_FORMAT.format(statsState.value.allTimeExpensesSum)
                        } else {
                            CRYPTO_DECIMAL_FORMAT.format(statsState.value.allTimeExpensesSum)
                        },
                        statsState.value.preferableCurrency.ticker
                    ), textAlign = TextAlign.Center
                )
            }
            if (statsState.value.allTimeIncomesCount == 0) {
                Text(
                    text = stringResource(R.string.not_added_any_incomes_yet_personal_settings_screen),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Text(
                    text = stringResource(
                        R.string.you_have_already_added_incomes_worth_of,
                        statsState.value.allTimeIncomesCount,
                        if (statsState.value.preferableCurrency.type == CurrencyTypes.FIAT) {
                            FIAT_DECIMAL_FORMAT.format(statsState.value.allTimeIncomesSum)
                        } else {
                            CRYPTO_DECIMAL_FORMAT.format(statsState.value.allTimeIncomesSum)
                        },
                        statsState.value.preferableCurrency.ticker
                    ), textAlign = TextAlign.Center
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(
                    R.string.login_message_personal_settings_screen,
                    statsState.value.loginCount
                ), modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}