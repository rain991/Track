package com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.accountPreferences.currenciesSettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CURRENCIES_FILTER_MAX_LENGTH
import com.savenko.track.domain.models.currency.Currency

@Composable
fun CurrencyListComponent(filteredCurrenciesList: List<Currency>, onTextChange: (String) -> Unit) {
    val listState = rememberLazyListState()
    var inputTextFilter by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.currencies), style = MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = inputTextFilter,
                onValueChange = {
                    if (it.length < CURRENCIES_FILTER_MAX_LENGTH) {
                        inputTextFilter = it
                        onTextChange(it)
                    }
                },
                suffix = { Icons.Filled.Search },
                placeholder = { Text(text = "Filter currencies") },
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
            }) { index, item ->
                Card(modifier = Modifier, shape = RoundedCornerShape(8.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item.name)
                        Text(text = item.ticker)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}