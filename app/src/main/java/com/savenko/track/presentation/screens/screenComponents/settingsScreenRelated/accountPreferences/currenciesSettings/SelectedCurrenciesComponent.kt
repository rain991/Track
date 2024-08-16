package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.accountPreferences.currenciesSettings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.domain.models.abstractLayer.CurrenciesOptions
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.other.composableTypes.currencies.CurrenciesPreferenceUI
import com.savenko.track.presentation.other.composableTypes.errors.CurrenciesSettingsScreenErrors
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.CurrenciesSettingsScreenEvent
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.SelectedCurrenciesSettingsState
import kotlinx.coroutines.launch

@Composable
fun SelectedCurrenciesComponent(
    paddingValues: PaddingValues,
    state: SelectedCurrenciesSettingsState,
    onAction: (CurrenciesSettingsScreenEvent) -> Unit,
    currenciesPreferenceUI: CurrenciesPreferenceUI,
    additionalCurrenciesPreferenceList: List<Currency?>
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = stringResource(R.string.message_currencies_settings_screen),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.preferable_currency_settings_screen),
                    style = MaterialTheme.typography.bodyMedium
                )
                CurrenciesSettingsCurrencyCard(
                    currencyList = state.allCurrenciesList,
                    selectedOption = currenciesPreferenceUI.preferableCurrency,
                    isElevated = true,
                    containsName = true
                ) {
                    coroutineScope.launch {
                        onAction(CurrenciesSettingsScreenEvent.SetPreferableCurrency(it))
                    }
                }
            }
        }
        AnimatedVisibility(state.isAdditionalCurrenciesVisible) {
            Column {
                for (currency in additionalCurrenciesPreferenceList) {
                    CurrenciesSettingsRow(
                        currency = currency,
                        currenciesList = state.allCurrenciesList
                    ) { newCurrency ->
                        when (currency) {
                            currenciesPreferenceUI.firstAdditionalCurrency -> {
                                coroutineScope.launch {
                                    onAction(CurrenciesSettingsScreenEvent.SetFirstAdditionalCurrency(newCurrency))
                                }
                            }

                            currenciesPreferenceUI.secondAdditionalCurrency -> {
                                coroutineScope.launch {
                                    onAction(CurrenciesSettingsScreenEvent.SetSecondAdditionalCurrency(newCurrency))
                                }
                            }

                            currenciesPreferenceUI.thirdAdditionalCurrency -> {
                                coroutineScope.launch {
                                    onAction(CurrenciesSettingsScreenEvent.SetThirdAdditionalCurrency(newCurrency))
                                }
                            }

                            currenciesPreferenceUI.fourthAdditionalCurrency -> {
                                coroutineScope.launch {
                                    onAction(CurrenciesSettingsScreenEvent.SetFourthAdditionalCurrency(newCurrency))
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            if (currenciesPreferenceUI.fourthAdditionalCurrency != null) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = { onAction(CurrenciesSettingsScreenEvent.SwitchAdditionalCurrenciesVisibility) }) {
                Text(
                    text = if (state.isAdditionalCurrenciesVisible) {
                        stringResource(R.string.hide_additional_currencies)
                    } else {
                        stringResource(R.string.show_additional_currencies)
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(visible = additionalCurrenciesPreferenceList.any { it == null }) {
                CurrenciesPlusTextButton {
                    when {
                        currenciesPreferenceUI.firstAdditionalCurrency == null -> {
                            onAction(CurrenciesSettingsScreenEvent.SetCurrencyAsRandomNotUsed(CurrenciesOptions.FIRST_ADDITIONAL))
                        }

                        currenciesPreferenceUI.secondAdditionalCurrency == null -> {
                            onAction(CurrenciesSettingsScreenEvent.SetCurrencyAsRandomNotUsed(CurrenciesOptions.SECOND_ADDITIONAL))
                        }

                        currenciesPreferenceUI.thirdAdditionalCurrency == null -> {
                            onAction(CurrenciesSettingsScreenEvent.SetCurrencyAsRandomNotUsed(CurrenciesOptions.THIRD_ADDITIONAL))
                        }

                        currenciesPreferenceUI.fourthAdditionalCurrency == null -> {
                            onAction(CurrenciesSettingsScreenEvent.SetCurrencyAsRandomNotUsed(CurrenciesOptions.FOURTH_ADDITIONAL))
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = additionalCurrenciesPreferenceList.any { it != null }) {
                    CurrenciesMinusTextButton {
                        coroutineScope.launch {
                            onAction(CurrenciesSettingsScreenEvent.SetLatestCurrencyAsNull)
                        }
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            when (state.error) {
                is CurrenciesSettingsScreenErrors.CurrencyIsAlreadyInUse -> {
                    val errorTicker = state.error.relatedCurrencyTicker
                    Text(
                        text = stringResource(
                            id = CurrenciesSettingsScreenErrors.CurrencyIsAlreadyInUse(errorTicker).error,
                            errorTicker
                        ),
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error)
                    )
                }

                is CurrenciesSettingsScreenErrors.IncorrectCurrencyConversion -> {
                    Text(
                        text = stringResource(id = CurrenciesSettingsScreenErrors.IncorrectCurrencyConversion.error),
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error)
                    )
                }

                null -> {}
            }
        }
    }
}

@Composable
private fun CurrenciesMinusTextButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.outline_remove_24),
            contentDescription = stringResource(R.string.delete_latest_currency_settings_screen),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun CurrenciesPlusTextButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = stringResource(R.string.delete_latest_currency_settings_screen),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}