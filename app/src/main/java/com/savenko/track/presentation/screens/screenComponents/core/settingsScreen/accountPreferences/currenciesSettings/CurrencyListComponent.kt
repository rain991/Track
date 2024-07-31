package com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.accountPreferences.currenciesSettings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CURRENCIES_FILTER_MAX_LENGTH
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.UiText.DatabaseStringResourcesProvider
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.CurrenciesSettingsScreenEvent
import org.koin.compose.koinInject

@Composable
fun CurrencyListComponent(
    filteredCurrenciesList: List<Currency>,
    onAction: (CurrenciesSettingsScreenEvent) -> Unit,
    onTextChange: (String) -> Unit
) {
    val listState = rememberLazyListState()
    var inputTextFilter by remember { mutableStateOf("") }
    val databaseStringResourcesProvider = koinInject<DatabaseStringResourcesProvider>()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.currencies),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
            )
            OutlinedTextField(
                value = inputTextFilter,
                onValueChange = {
                    if (it.length < CURRENCIES_FILTER_MAX_LENGTH) {
                        inputTextFilter = it
                        onTextChange(it)
                    }
                },
                suffix = { Icons.Filled.Search },
                placeholder = { Text(text = stringResource(R.string.filter_currencies)) },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .scale(0.8f)
                    .widthIn(1.dp, Dp.Infinity)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(state = listState, modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(items = filteredCurrenciesList, key = { _: Int, item: Currency ->
                item.name
            }) { _, item ->
                CurrencyFocusableRow(
                    currency = item,
                    databaseStringResourcesProvider = databaseStringResourcesProvider
                ) { currency: Currency, currenciesSettingsSelectionOption: CurrenciesSettingsSelectionOption ->
                    when (currenciesSettingsSelectionOption) {
                        CurrenciesSettingsSelectionOption.SetPreferableCurrency -> {
                            onAction(CurrenciesSettingsScreenEvent.SetPreferableCurrency(currency))
                        }

                        CurrenciesSettingsSelectionOption.SetFirstAdditionalCurrency -> {
                            onAction(CurrenciesSettingsScreenEvent.SetFirstAdditionalCurrency(currency))
                        }

                        CurrenciesSettingsSelectionOption.SetSecondAdditionalCurrency -> {
                            onAction(CurrenciesSettingsScreenEvent.SetSecondAdditionalCurrency(currency))
                        }

                        CurrenciesSettingsSelectionOption.SetThirdAdditionalCurrency -> {
                            onAction(CurrenciesSettingsScreenEvent.SetThirdAdditionalCurrency(currency))
                        }

                        CurrenciesSettingsSelectionOption.SetFourthAdditionalCurrency -> {
                            onAction(CurrenciesSettingsScreenEvent.SetFourthAdditionalCurrency(currency))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun CurrencyFocusableRow(
    currency: Currency,
    databaseStringResourcesProvider: DatabaseStringResourcesProvider,
    onOptionSelect: (Currency, CurrenciesSettingsSelectionOption) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val listOfOptions = listOf(
        CurrenciesSettingsSelectionOption.SetPreferableCurrency,
        CurrenciesSettingsSelectionOption.SetFirstAdditionalCurrency,
        CurrenciesSettingsSelectionOption.SetSecondAdditionalCurrency,
        CurrenciesSettingsSelectionOption.SetThirdAdditionalCurrency,
        CurrenciesSettingsSelectionOption.SetFourthAdditionalCurrency
    )
    val gradientColor1 = MaterialTheme.colorScheme.primary
    val gradientColor2 = MaterialTheme.colorScheme.tertiary
    val borderBrush = remember {
        Brush.linearGradient(
            listOf(
                gradientColor1,
                gradientColor2
            )
        )
    }
    val borderWidth: Float by animateFloatAsState(
        targetValue = if (isFocused) {
            2.0f
        } else {
            0.0f
        }, label = "currencyFocusableRow"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(
                if (borderWidth > 0) Modifier.border(
                    width = borderWidth.dp,
                    borderBrush,
                    RoundedCornerShape(8.dp)
                ) else Modifier
            )
            .clickable { isFocused = true }, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
                .focusRequester(focusRequester)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = stringResource(id = databaseStringResourcesProvider.getCurrencyStringResource(currency.ticker)))
                Text(text = currency.ticker)
            }
            DropdownMenu(
                expanded = isFocused,
                onDismissRequest = { isFocused = false },
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .wrapContentHeight()
            ) {
                listOfOptions.forEachIndexed { index, selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(id = selectionOption.messageResId))
                        },
                        onClick = {
                            onOptionSelect(currency, selectionOption)
                            isFocused = false
                        }
                    )
                }
            }
        }
    }
}

private sealed class CurrenciesSettingsSelectionOption(val messageResId: Int) {
    data object SetPreferableCurrency :
        CurrenciesSettingsSelectionOption(messageResId = R.string.set_as_preferable_currency_currencies_settings)

    data object SetFirstAdditionalCurrency :
        CurrenciesSettingsSelectionOption(messageResId = R.string.set_as_first_priority_additional)

    data object SetSecondAdditionalCurrency :
        CurrenciesSettingsSelectionOption(messageResId = R.string.set_as_second_priority_additional)

    data object SetThirdAdditionalCurrency :
        CurrenciesSettingsSelectionOption(messageResId = R.string.set_as_third_priority_additional)

    data object SetFourthAdditionalCurrency :
        CurrenciesSettingsSelectionOption(messageResId = R.string.set_as_fourth_priority_additional)
}