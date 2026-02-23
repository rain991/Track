package com.savenko.track.shared.presentation.components.dropDownMenus

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.TextStyle
import com.savenko.track.shared.domain.models.currency.Currency


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("UNUSED")
fun CurrencyDropDownMenu(currencyList: List<Currency>, selectedOption: Currency, onSelect: (Currency) -> Unit) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        }
    ) {
        TextField(
            value = selectedOption.ticker,
            readOnly = true,
            onValueChange = {},
            label = { Text(stringResource(Res.string.currency), style = TextStyle(color = uiColor)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            modifier = Modifier.width(IntrinsicSize.Min),
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            currencyList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption.name, color = uiColor) },
                    onClick = {
                        onSelect(selectionOption)
                        isExpanded = false
                    },
                    trailingIcon = {
                        Text(text = selectionOption.ticker, color = uiColor)
                    }
                )
            }
        }
    }
}
