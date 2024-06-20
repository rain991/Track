package com.savenko.track.presentation.components.bottomSheet.composables

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
import androidx.compose.runtime.collectAsState
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
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.domain.models.currency.Currency
import org.koin.androidx.compose.koinViewModel

// Warning amountInput is currently depends on external viewModels
@Composable
fun AmountInput(
    focusRequester: FocusRequester,
    controller: SoftwareKeyboardController?,
    currentCurrency: Currency
) {
    val focusManager = LocalFocusManager.current
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val bottomSheetViewState = bottomSheetViewModel.expenseViewState.collectAsState()
    val currentInputValue = bottomSheetViewState.value.inputExpense
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
                fontSize = 54.sp,
                letterSpacing = 1.3.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            value = currentInputValue.toString(),
            onValueChange = { newText ->
                bottomSheetViewModel.setInputExpense(
                    try {
                        newText.toFloat()
                    } catch (e: NumberFormatException) {
                        currentInputValue ?: 0.0f
                    }
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    controller?.hide()
                    focusManager.clearFocus()
                }
            ),
            maxLines = 1,
        )
        TextButton(onClick = { bottomSheetViewModel.changeSelectedCurrency() }, modifier = Modifier.wrapContentWidth()) {
            Text(text = currentCurrency.ticker, style = MaterialTheme.typography.titleMedium)
        }
    }
}