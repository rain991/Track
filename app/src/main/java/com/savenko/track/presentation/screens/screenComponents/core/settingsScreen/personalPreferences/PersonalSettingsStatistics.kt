package com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.personalPreferences

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.viewmodels.settingsScreen.personal.PersonalStatsViewModel

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
                Text(text = stringResource(R.string.not_added_any_expenses_yet_personal_settings_screen))
            } else {
                Text(
                    text = stringResource(
                        R.string.you_have_already_added_expenses_worth_of,
                        statsState.value.allTimeExpensesCount,
                        statsState.value.allTimeExpensesSum,
                        statsState.value.preferableCurrency.ticker
                    )
                )
            }
            if (statsState.value.allTimeIncomesCount == 0) {
                Text(text = stringResource(R.string.not_added_any_incomes_yet_personal_settings_screen))
            } else {
                Text(
                    text = stringResource(
                        R.string.you_have_already_added_incomes_worth_of,
                        statsState.value.allTimeIncomesCount,
                        statsState.value.allTimeIncomesSum,
                        statsState.value.preferableCurrency.ticker
                    )
                )
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(0.9f))
            Text(
                text = stringResource(
                    R.string.login_message_personal_settings_screen,
                    statsState.value.loginCount
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}