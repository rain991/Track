package com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.accountPreferences.currenciesSettings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.domain.models.currency.Currency

// Used for non-preferable currency settings
@Composable
fun CurrenciesSettingsRow(currency: Currency?, currenciesList: List<Currency>, onMenuSelect: (Currency) -> Unit) {
    if (currency != null) {
        Spacer(modifier = Modifier.height(8.dp))
    }
    AnimatedVisibility(visible = currency != null) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.extra_currency_settings_screen),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 4.dp)
            )
            CurrenciesSettingsCurrencyCard(
                currencyList = currenciesList,
                selectedOption = currency ?: CURRENCY_DEFAULT,
                isElevated = false,
                onSelect = {
                    onMenuSelect(it)
                })
        }
    }
}