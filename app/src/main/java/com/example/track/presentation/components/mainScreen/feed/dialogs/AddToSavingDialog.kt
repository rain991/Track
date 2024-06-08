package com.example.track.presentation.components.mainScreen.feed.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.track.data.other.constants.CRYPTO_DECIMAL_FORMAT
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.other.constants.FIAT_DECIMAL_FORMAT
import com.example.track.data.other.constants.PERCENTAGE_DECIMAL_FORMAT
import com.example.track.data.viewmodels.mainScreen.AddToSavingIdeaDialogViewModel
import com.example.track.domain.models.currency.CurrencyTypes
import kotlinx.coroutines.launch

@Composable
fun AddToSavingDialog(
    addToSavingIdeaDialogViewModel: AddToSavingIdeaDialogViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = FocusRequester.Default
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val currentSaving = addToSavingIdeaDialogViewModel.currentSavings.collectAsState()
    val preferableCurrency = addToSavingIdeaDialogViewModel.preferableCurrency.collectAsState()
    val selectedCurrency = addToSavingIdeaDialogViewModel.selectedCurrency.collectAsState(initial = CURRENCY_DEFAULT)
    var inputValue by remember { mutableFloatStateOf(0.0f) }
    Dialog(
        onDismissRequest = { addToSavingIdeaDialogViewModel.setCurrentSaving(null) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Add to " + currentSaving.value?.label, style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(8.dp))
                AddToSavingDialogAmountInput(
                    focusRequester = focusRequester,
                    controller = softwareKeyboardController,
                    currentCurrency = selectedCurrency.value ?: CURRENCY_DEFAULT,
                    currentValue = inputValue,
                    onCurrencyChange = { addToSavingIdeaDialogViewModel.changeSelectedCurrency() },
                    onValueChange = { inputValue = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Planned ${
                        if (preferableCurrency.value.type == CurrencyTypes.FIAT) {
                            FIAT_DECIMAL_FORMAT.format(currentSaving.value?.goal)
                        } else {
                            CRYPTO_DECIMAL_FORMAT.format(currentSaving.value?.goal)
                        }
                    } " + preferableCurrency.value.ticker,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Completed for" + " ${
                        if (preferableCurrency.value.type == CurrencyTypes.FIAT) {
                            FIAT_DECIMAL_FORMAT.format(currentSaving.value?.value)
                        } else {
                            CRYPTO_DECIMAL_FORMAT.format(currentSaving.value?.value)
                        }
                    } " + preferableCurrency.value.ticker + " (${
                        PERCENTAGE_DECIMAL_FORMAT.format(
                            currentSaving.value?.value?.div(
                                currentSaving.value?.goal ?: 1.0f
                            )?.times(100)
                        )
                    }%)", style = MaterialTheme.typography.bodySmall
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    OutlinedButton(onClick = { addToSavingIdeaDialogViewModel.setCurrentSaving(null) }) {
                        Text(text = "Decline")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        coroutineScope.launch {
                            addToSavingIdeaDialogViewModel.addToSaving(inputValue)
                        }
                    }) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}