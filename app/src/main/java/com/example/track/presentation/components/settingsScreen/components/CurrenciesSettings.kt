package com.example.track.presentation.components.settingsScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.track.R
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.domain.models.currency.Currency
import com.example.track.data.viewmodels.settingsScreen.SettingsViewModel

import com.example.track.presentation.components.login.CurrencyDropDownMenu
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CurrenciesSettings(
    modifier: Modifier,
    preferableCurrency: Currency,
    firstAdditionalCurrency: Currency?,
    secondAdditionalCurrency: Currency?,
    thirdAdditionalCurrency: Currency?,
    fourthAdditionalCurrency: Currency?
) {
    val coroutineScope = rememberCoroutineScope()
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    Box(modifier) {
        Column(Modifier.wrapContentSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.currencies_settings_screen),
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
                AnimatedVisibility(visible = firstAdditionalCurrency != null || secondAdditionalCurrency != null || thirdAdditionalCurrency != null || fourthAdditionalCurrency != null) {
                    CurrenciesMinusTextButton {
                        coroutineScope.launch {
                            settingsViewModel.setLatestCurrencyAsNull()
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Text(
                    text = "You can switch currencies by touching their ticker in cards",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.preferable_currency_settings_screen),
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier.padding(start = 4.dp)
                )
                Box(Modifier.width(140.dp)) {
                    CurrencyDropDownMenu(
                        currencyList = settingsViewModel.currencyList,
                        selectedOption = preferableCurrency,
                        onSelect = {
                            coroutineScope.launch {
                                settingsViewModel.setPreferableCurrency(it)
                            }
                        })
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = firstAdditionalCurrency != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.extra_currency_settings_screen),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Box(Modifier.width(140.dp)) {
                        CurrencyDropDownMenu(
                            currencyList = settingsViewModel.currencyList,
                            selectedOption = firstAdditionalCurrency ?: CURRENCY_DEFAULT,
                            onSelect = {
                                coroutineScope.launch {
                                    settingsViewModel.setFirstAdditionalCurrency(it)
                                }
                            })
                    }
                }
            }
            if (firstAdditionalCurrency != null) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            AnimatedVisibility(visible = secondAdditionalCurrency != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.extra_currency_settings_screen),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Box(Modifier.width(140.dp)) {
                        CurrencyDropDownMenu(
                            currencyList = settingsViewModel.currencyList,
                            selectedOption = secondAdditionalCurrency ?: CURRENCY_DEFAULT,
                            onSelect = {
                                coroutineScope.launch {
                                    settingsViewModel.setSecondAdditionalCurrency(it)
                                }
                            })
                    }
                }
            }
            if (secondAdditionalCurrency != null) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            AnimatedVisibility(visible = thirdAdditionalCurrency != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.extra_currency_settings_screen),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Box(Modifier.width(140.dp)) {
                        CurrencyDropDownMenu(
                            currencyList = settingsViewModel.currencyList,
                            selectedOption = thirdAdditionalCurrency ?: CURRENCY_DEFAULT,
                            onSelect = {
                                coroutineScope.launch {
                                    settingsViewModel.setThirdAdditionalCurrency(it)
                                }
                            })
                    }
                }
            }
            if (thirdAdditionalCurrency != null) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            AnimatedVisibility(visible = fourthAdditionalCurrency != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.extra_currency_settings_screen),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Box(Modifier.width(140.dp)) {
                        CurrencyDropDownMenu(
                            currencyList = settingsViewModel.currencyList,
                            selectedOption = fourthAdditionalCurrency ?: CURRENCY_DEFAULT,
                            onSelect = {
                                coroutineScope.launch {
                                    settingsViewModel.setFourthAdditionalCurrency(it)
                                }
                            })
                    }
                }
            }
            if (fourthAdditionalCurrency != null) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            AnimatedVisibility(visible = (firstAdditionalCurrency == null || secondAdditionalCurrency == null || thirdAdditionalCurrency == null || fourthAdditionalCurrency == null)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CurrenciesPlusTextButton {
                        if (firstAdditionalCurrency == null) {
                            coroutineScope.launch {
                                settingsViewModel.setFirstAdditionalCurrency(settingsViewModel.getRandomNotUsedCurrency())
                            }
                        } else if (secondAdditionalCurrency == null) {
                            coroutineScope.launch {
                                settingsViewModel.setSecondAdditionalCurrency(settingsViewModel.getRandomNotUsedCurrency())
                            }
                        } else if (thirdAdditionalCurrency == null) {
                            coroutineScope.launch {
                                settingsViewModel.setThirdAdditionalCurrency(settingsViewModel.getRandomNotUsedCurrency())
                            }
                        } else if (fourthAdditionalCurrency == null) {
                            coroutineScope.launch {
                                settingsViewModel.setFourthAdditionalCurrency(settingsViewModel.getRandomNotUsedCurrency())
                            }
                        }
                    }
                }
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