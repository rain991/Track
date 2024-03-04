package com.example.expensetracker.presentation.home.settingsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.constants.CURRENCY_DEFAULT
import com.example.expensetracker.data.constants.NAME_DEFAULT
import com.example.expensetracker.data.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.expensetracker.data.constants.USE_SYSTEM_THEME_DEFAULT
import com.example.expensetracker.data.models.currency.Currency
import com.example.expensetracker.data.viewmodels.settingsScreen.SettingsViewModel
import com.example.expensetracker.presentation.login.CurrencyDropDownMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsHeader(modifier: Modifier, dataStoreManager: DataStoreManager) {
    val currentUserName = dataStoreManager.nameFlow.collectAsState(initial = NAME_DEFAULT)
    Text(
        text = stringResource(R.string.greetings_settings_screen, currentUserName.value),
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 40.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun CurrenciesSettings(
    modifier: Modifier,
    preferableCurrency: Currency,
    firstAdditionalCurrency: Currency?,
    secondAdditionalCurrency: Currency?
) {
    val coroutineScope = rememberCoroutineScope()
    val settingsViewModel = koinViewModel<SettingsViewModel>()

    Box(modifier = modifier) {
        Column(Modifier.wrapContentSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.currencies_settings_screen),
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 26.sp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    //modifier = Modifier.padding(start = 4.dp),
                    textAlign = TextAlign.Center
                )
                AnimatedVisibility(visible = firstAdditionalCurrency != null || secondAdditionalCurrency != null) {
                    CurrenciesMinusTextButton {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                settingsViewModel.setLatestCurrencyAsNull()
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
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
                            settingsViewModel.setPreferableCurrency(it)
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
                            selectedOption = firstAdditionalCurrency!!, // ALERT
                            onSelect = {
                                settingsViewModel.setFirstAdditionalCurrency(it)
                            })
                    }

                }
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
                            selectedOption = secondAdditionalCurrency!!, // ALERT
                            onSelect = {
                                settingsViewModel.setSecondAdditionalCurrency(it)
                            })
                    }
                }
            }
            AnimatedVisibility(visible = (firstAdditionalCurrency == null || secondAdditionalCurrency == null)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CurrenciesPlusTextButton {
                        if (firstAdditionalCurrency != null) {
                            settingsViewModel.setSecondAdditionalCurrency(CURRENCY_DEFAULT)
                        } else if (secondAdditionalCurrency != null) {
                            settingsViewModel.setFirstAdditionalCurrency(CURRENCY_DEFAULT)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ThemePreferences(modifier: Modifier, dataStoreManager: DataStoreManager) {
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val showPageNameChecked = settingsViewModel.showPagesNameFlow.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val useDeviceTheme = settingsViewModel.useSystemTheme.collectAsState(initial = USE_SYSTEM_THEME_DEFAULT)
    Box(modifier = modifier) {
        Column(Modifier.wrapContentSize()) {
            Text(
                text = stringResource(R.string.theme_settings_screen),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(R.string.use_device_theme_settings_screen),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                        modifier = Modifier.padding(start = 4.dp), textAlign = TextAlign.Start
                    )
                    SettingsSwitch(checked = useDeviceTheme.value, onCheckedChange = {
                        coroutineScope.launch { settingsViewModel.setUseSystemTheme(it) }
                    })
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(visible = !useDeviceTheme.value, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(R.string.preferable_theme_settings_screen),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(start = 4.dp), textAlign = TextAlign.Start
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.show_page_name_setttings_screen),
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier.padding(start = 4.dp)
                )
                SettingsSwitch(checked = showPageNameChecked.value, onCheckedChange = {
                    coroutineScope.launch {
                        settingsViewModel.setShowPagesNameFlow(it)
                    }
                })
            }
        }
    }
}

@Composable
private fun SettingsSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(checked = checked, onCheckedChange = onCheckedChange)
}

@Composable
private fun OptionalSettingsSwitch(
    enabledFlow: Flow<Boolean>,
    checkedFlow: Flow<Boolean>,
    initial: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val enabled = enabledFlow.collectAsState(initial = false)
    val checked = checkedFlow.collectAsState(initial = initial)
    Switch(checked = checked.value, onCheckedChange = onCheckedChange, enabled = enabled.value)
}

@Composable
private fun CurrenciesMinusTextButton(onClick: () -> Unit) {
    TextButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Outlined.Clear,
            contentDescription = stringResource(R.string.delete_latest_currency_settings_screen),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun CurrenciesPlusTextButton(onClick: () -> Unit) {
    TextButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = stringResource(R.string.delete_latest_currency_settings_screen),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}