package com.example.expensetracker.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.data.SettingsData
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.domain.usecases.expenseusecases.AddExpensesItemUseCase
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SimplifiedBottomSheet(isVisible: Boolean, expensesListRepositoryImpl: ExpensesListRepositoryImpl, settingsData: SettingsData) {
    val addExpensesItemUseCase = koinInject<AddExpensesItemUseCase>()
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = {
            true
        })

    var currentExpenseAdded by remember { mutableFloatStateOf(0.0F) } // Expense adding value
    val scope = rememberCoroutineScope()
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = {//isVisible = false
            },
            sheetState = sheetState
        ) {
            val controller = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }
            Column(   // All content
                modifier = Modifier.
                    fillMaxHeight(0.7f)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.add_expenses), fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(40.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            modifier = Modifier.focusRequester(focusRequester)
                                .width(IntrinsicSize.Min)
                                .padding(horizontal = 12.dp),
                            textStyle = MaterialTheme.typography.titleLarge.copy(fontSize = 48.sp, letterSpacing = 1.3.sp),
                            value = currentExpenseAdded.toString(),
                            onValueChange = { newText ->
                                val filteredText = newText.filter { it.isDigit() || it in setOf('.', '+', '-') }
                                if (filteredText.isNotBlank()) { currentExpenseAdded = filteredText.toFloatOrNull() ?: 0f
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = { controller?.hide()
                                    focusRequester.freeFocus()}
                            ),
                            maxLines = 1,

                            )
                        Text(text = settingsData.getCurrency(), style = MaterialTheme.typography.titleSmall)
                    }

                }
            }
        }
    }
}


//                    TextField( modifier = Modifier.width(IntrinsicSize.Min),
//                        // modifier = Modifier.widthIn(1.dp, Dp.Infinity),
//                        value = currentExpenseAdded.toString(),
//                        onValueChange = {
//                            if (!it.matches(pattern)) {
//                                currentExpenseAdded = it.toFloat()
//                            } else {
//
//                            }
//                        },
//                        suffix = { Text(text = settingsData.getCurrency(), style = MaterialTheme.typography.titleSmall) },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                        maxLines = 1,
//                        textStyle = MaterialTheme.typography.titleMedium
//                    )