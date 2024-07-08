package com.savenko.track.presentation.components.dialogs.addToSavingIdeaDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.data.other.constants.MAX_IDEA_VALUE
import com.savenko.track.domain.models.currency.Currency

@Composable
fun AddToSavingDialogAmountInput(
    focusRequester: FocusRequester,
    controller: SoftwareKeyboardController?,
    currentCurrency: Currency,
    currentValue: Float,
    onValueChange: (Float) -> Unit,
    onCurrencyChange: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .width(IntrinsicSize.Min)
                .padding(start = 12.dp),
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontSize = 38.sp,
                letterSpacing = 1.3.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            value = currentValue.toString(),
            onValueChange = { newText: String ->
                val result = try {
                    val convertedValue = newText.toFloat()
                    if (convertedValue < MAX_IDEA_VALUE) {
                        convertedValue
                    } else {
                        MAX_IDEA_VALUE.toFloat()
                    }
                } catch (e: NumberFormatException) {
                    currentValue
                }
                onValueChange(result)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    controller?.hide()
                    focusManager.clearFocus()
                }
            ),
            maxLines = 1,
        )
        TextButton(onClick = { onCurrencyChange() }, modifier = Modifier.wrapContentWidth()) {
            Text(text = currentCurrency.ticker, style = MaterialTheme.typography.bodyMedium)
        }
    }
}