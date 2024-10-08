package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.accountPreferences.currenciesSettings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.other.uiText.DatabaseStringResourcesProvider
import org.koin.compose.koinInject


@Composable
fun CurrenciesSettingsCurrencyCard(
    currencyList: List<Currency>,
    selectedOption: Currency,
    isElevated: Boolean,
    containsName: Boolean = false,
    onSelect: (Currency) -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val databaseStringResourcesProvider = koinInject<DatabaseStringResourcesProvider>()
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable {
                isFocused = true
            }, elevation = if (isElevated) {
            CardDefaults.cardElevation(
                defaultElevation = 16.dp,
                focusedElevation = 16.dp
            )
        } else {
            CardDefaults.cardElevation(
                defaultElevation = 0.dp,
                focusedElevation = 0.dp
            )
        }

    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .focusRequester(focusRequester)
        ) {
            val columnWithNameModifier = Modifier
                .padding(8.dp)
                .scale(0.8f)
            val columnWithoutNameModifier = Modifier.padding(12.dp)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = if (containsName) {
                    columnWithNameModifier
                } else {
                    columnWithoutNameModifier
                }
            ) {
                Text(
                    text = selectedOption.ticker,
                    style = MaterialTheme.typography.titleMedium
                )
                if (containsName) {
                    Text(
                        text = stringResource(
                            id = databaseStringResourcesProvider.provideCurrencyStringResource(
                                selectedOption.ticker
                            )
                        ), style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            DropdownMenu(
                expanded = isFocused,
                onDismissRequest = { isFocused = false },
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .wrapContentHeight()
            ) {
                currencyList.forEachIndexed { index, selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = selectionOption.name,
                                        color = uiColor
                                    )
                                    Text(
                                        text = selectionOption.ticker,
                                        color = uiColor
                                    )
                                }
                                if (index + 1 < currencyList.size && currencyList[index + 1].type != currencyList[index].type) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = currencyList[index + 1].type.name,
                                        color = uiColor
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        },
                        onClick = {
                            onSelect(selectionOption)
                            isFocused = false
                        }
                    )
                }
            }
        }
    }
}