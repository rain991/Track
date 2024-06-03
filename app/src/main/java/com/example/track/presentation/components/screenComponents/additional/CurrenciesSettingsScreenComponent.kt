package com.example.track.presentation.components.screenComponents.additional

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.data.other.constants.CURRENCY_FIAT
import com.example.track.data.viewmodels.settingsScreen.CurrenciesSettingsViewModel
import com.example.track.presentation.components.common.ui.CurrencyDropDownMenu
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/*  Contains component of currencies settings screen(except header).
    Also contains CurrenciesMinusTextButton and Plus buttons to remove and add new currencies relatively    */
@Composable
fun CurrenciesSettingsScreenComponent(paddingValues: PaddingValues) {
    val currenciesSettingsViewModel = koinViewModel<CurrenciesSettingsViewModel>()
    val preferableCurrency = currenciesSettingsViewModel.preferableCurrencyStateFlow.collectAsState(initial = CURRENCY_FIAT)
    val firstAdditionalCurrency = currenciesSettingsViewModel.firstAdditionalCurrencyStateFlow.collectAsState(initial = null)
    val secondAdditionalCurrency = currenciesSettingsViewModel.secondAdditionalCurrencyStateFlow.collectAsState(initial = null)
    val thirdAdditionalCurrency = currenciesSettingsViewModel.thirdAdditionalCurrencyStateFlow.collectAsState(initial = null)
    val fourthAdditionalCurrency = currenciesSettingsViewModel.fourthAdditionalCurrencyStateFlow.collectAsState(initial = null)
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
                text = "You can switch currencies by touching their ticker in cards. Your preferable currency is used as basic in most parts of the Track. ",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall
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
                    currencyList = currenciesSettingsViewModel.currencyList,
                    selectedOption = preferableCurrency.value,
                    onSelect = {
                        coroutineScope.launch {
                            currenciesSettingsViewModel.setPreferableCurrency(it)
                        }
                    })
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AnimatedVisibility(visible = firstAdditionalCurrency.value != null) {
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
                        currencyList = currenciesSettingsViewModel.currencyList,
                        selectedOption = firstAdditionalCurrency.value ?: CURRENCY_FIAT,
                        onSelect = {
                            coroutineScope.launch {
                                currenciesSettingsViewModel.setFirstAdditionalCurrency(it)
                            }
                        })
                }
            }
        }
        if (firstAdditionalCurrency.value != null) {
            Spacer(modifier = Modifier.height(8.dp))
        }
        AnimatedVisibility(visible = secondAdditionalCurrency.value != null) {
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
                        currencyList = currenciesSettingsViewModel.currencyList,
                        selectedOption = secondAdditionalCurrency.value ?: CURRENCY_FIAT,
                        onSelect = {
                            coroutineScope.launch {
                                currenciesSettingsViewModel.setSecondAdditionalCurrency(it)
                            }
                        })
                }
            }
        }
        if (secondAdditionalCurrency.value != null) {
            Spacer(modifier = Modifier.height(8.dp))
        }
        AnimatedVisibility(visible = thirdAdditionalCurrency.value != null) {
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
                        currencyList = currenciesSettingsViewModel.currencyList,
                        selectedOption = thirdAdditionalCurrency.value ?: CURRENCY_FIAT,
                        onSelect = {
                            coroutineScope.launch {
                                currenciesSettingsViewModel.setThirdAdditionalCurrency(it)
                            }
                        })
                }
            }
        }
        if (thirdAdditionalCurrency.value != null) {
            Spacer(modifier = Modifier.height(8.dp))
        }
        AnimatedVisibility(visible = fourthAdditionalCurrency.value != null) {
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
                        currencyList = currenciesSettingsViewModel.currencyList,
                        selectedOption = fourthAdditionalCurrency.value ?: CURRENCY_FIAT,
                        onSelect = {
                            coroutineScope.launch {
                                currenciesSettingsViewModel.setFourthAdditionalCurrency(it)
                            }
                        })
                }
            }
        }
        if (fourthAdditionalCurrency.value != null) {
            Spacer(modifier = Modifier.height(8.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(visible = (firstAdditionalCurrency.value == null || secondAdditionalCurrency.value == null || thirdAdditionalCurrency.value == null || fourthAdditionalCurrency.value == null)) {
                CurrenciesPlusTextButton {
                    if (firstAdditionalCurrency.value == null) {
                        coroutineScope.launch {
                            currenciesSettingsViewModel.setFirstAdditionalCurrency(currenciesSettingsViewModel.getRandomNotUsedCurrency())
                        }
                    } else if (secondAdditionalCurrency.value == null) {
                        coroutineScope.launch {
                            currenciesSettingsViewModel.setSecondAdditionalCurrency(currenciesSettingsViewModel.getRandomNotUsedCurrency())
                        }
                    } else if (thirdAdditionalCurrency.value == null) {
                        coroutineScope.launch {
                            currenciesSettingsViewModel.setThirdAdditionalCurrency(currenciesSettingsViewModel.getRandomNotUsedCurrency())
                        }
                    } else if (fourthAdditionalCurrency.value == null) {
                        coroutineScope.launch {
                            currenciesSettingsViewModel.setFourthAdditionalCurrency(currenciesSettingsViewModel.getRandomNotUsedCurrency())
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(visible = firstAdditionalCurrency.value != null || secondAdditionalCurrency.value != null || thirdAdditionalCurrency.value != null || fourthAdditionalCurrency.value != null) {
                CurrenciesMinusTextButton {
                    coroutineScope.launch {
                        currenciesSettingsViewModel.setLatestCurrencyAsNull()
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