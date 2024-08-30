package com.savenko.track.presentation.components.bottomSheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.data.other.constants.MAX_FINANCIAL_VALUE
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.other.composableTypes.errors.BottomSheetErrors

@Composable
fun BottomSheetAmountInput(
    currentCurrency: Currency,
    listOfAvailableCurrencies: List<Currency>,
    currentInputValue: Float,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    hasErrors: Boolean = false,
    onInputValueChange: (Float) -> Unit,
    onCurrencyChange: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
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
                    color = MaterialTheme.colorScheme.primary
                ),
                value = currentInputValue.toString(),
                onValueChange = { newText ->
                    onInputValueChange(
                        try {
                            val newValue = newText.toFloat()
                            if (newValue < MAX_FINANCIAL_VALUE) {
                                newValue
                            } else {
                                0.0f
                            }
                        } catch (e: NumberFormatException) {
                            currentInputValue ?: 0.0f
                        }
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                maxLines = 1)
            TextButton(
                onClick = { onCurrencyChange() },
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(text = currentCurrency.ticker, style = MaterialTheme.typography.titleMedium)
            }
            if (listOfAvailableCurrencies.size > 1) {
                Column(modifier = Modifier.fillMaxHeight()) {
                    AnimatedContent(
                        targetState = currentCurrency,
                        label = ""
                    ) { selectedCurrency ->
                        val selectedIndex = listOfAvailableCurrencies.indexOfFirst { it == selectedCurrency }
                        when (selectedIndex) {
                            0 -> {
                                Column(
                                    Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = 12.dp)
                                        .offset((-12).dp, 0.dp),
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        modifier = Modifier.scale(0.7f),
                                        contentDescription = null
                                    )
                                }
                            }

                            listOfAvailableCurrencies.size - 1 -> {
                                Column(
                                    Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = 12.dp)
                                        .offset((-12).dp, 0.dp),
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowUp,
                                        modifier = Modifier.scale(0.7f),
                                        contentDescription = null
                                    )
                                }
                            }

                            else -> {
                                Column(
                                    Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = 12.dp)
                                        .offset((-12).dp, 0.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowUp,
                                        modifier = Modifier.scale(0.7f),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        modifier = Modifier.scale(0.7f),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
        if (hasErrors) {
            Text(
                text = stringResource(id = BottomSheetErrors.IncorrectInputValue.error),
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}