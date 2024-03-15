package com.example.track.presentation.components.mainScreen.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.track.data.constants.CURRENCY_DEFAULT
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.models.currency.Currency
import com.example.track.data.viewmodels.mainScreen.MainScreenFeedViewModel
import com.example.track.presentation.states.IdeaSelectorTypes
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewIdeaDialog() {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val newIdeaDialogState = mainScreenFeedViewModel.newIdeaDialogState.collectAsState()
    val currenciesPreferenceRepositoryImpl = koinInject<CurrenciesPreferenceRepositoryImpl>()
    val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().collectAsState(initial = CURRENCY_DEFAULT)
    Dialog(
        onDismissRequest = { mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Add idea",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                val options = listOf(IdeaSelectorTypes.Savings, IdeaSelectorTypes.ExpenseLimit, IdeaSelectorTypes.IncomePlans)
                Row {
                    SingleChoiceSegmentedButtonRow() {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                modifier = Modifier.safeContentPadding(),
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                                onClick = { mainScreenFeedViewModel.setTypeSelected(label) },
                                selected = newIdeaDialogState.value.typeSelected == label
                            ) {
                                if (label == IdeaSelectorTypes.ExpenseLimit) {
                                    Text(
                                        text = label.name,
                                        maxLines = 1,
                                        style = MaterialTheme.typography.labelSmall,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                } else {
                                    Text(
                                        modifier = Modifier.wrapContentWidth(),
                                        text = label.name,
                                        maxLines = 1,
                                        style = MaterialTheme.typography.labelMedium,
                                        textAlign = TextAlign.Start
                                    )
                                }

                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (newIdeaDialogState.value.typeSelected) {
                            IdeaSelectorTypes.ExpenseLimit -> "Limit planned"
                            IdeaSelectorTypes.IncomePlans -> "Income planned"
                            IdeaSelectorTypes.Savings -> "Savings planned"
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IdeaInputField(preferableCurrency = preferableCurrency.value)
                }

//specific card inputs


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp), horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        modifier = Modifier.scale(0.8f),
                        onClick = { mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false) }) {
                        Text("Decline")
                    }
                    FilledTonalButton(modifier = Modifier.scale(0.9f), onClick = {
                        mainScreenFeedViewModel.addNewIdea()
                        mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false)
                    }) {
                        Text("Add")
                    }
                }

            }
        }
    }
}

//@Composable
//private fun SavingsDialogInputs(newIdeaDialogState : NewIdeaDialogState) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(0.9f)
//            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = "Included in budget")
//        Spacer(modifier = Modifier.width(8.dp))
//        Switch(
//            checked = newIdeaDialogState. ?: true,
//            onCheckedChange = { mainScreenFeedViewModel.setSelectedToAllCategories(it) })
//    }
//    Spacer(modifier = Modifier.height(4.dp))
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(0.9f)
//            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(
//                text = if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit) {
//                    stringResource(R.string.limit_end_date_ideas)
//                } else {
//                    stringResource(R.string.plan_end_date_ideas)
//                }
//            )
//            Text(text = "optional", style = MaterialTheme.typography.labelSmall)
//        }
//        Spacer(modifier = Modifier.width(12.dp))
//        Button(onClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(true) }) {
//            Text(
//                text = if (newIdeaDialogState.value.endDate != null) newIdeaDialogState.value.endDate.toString() else "Date",
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//        CustomDatePicker(
//            isVisible = newIdeaDialogState.value.isDateDialogVisible,
//            onNegativeClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(false) },
//            onPositiveClick = { date -> mainScreenFeedViewModel.setEndDate(date) }
//        )
//    }
//
//    if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit) {
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = stringResource(R.string.related_to_all_categories_ideas))
//            Spacer(modifier = Modifier.width(8.dp))
//            Switch(
//                checked = newIdeaDialogState.value.relatedToAllCategories ?: true,
//                onCheckedChange = { mainScreenFeedViewModel.setSelectedToAllCategories(it) })
//        }
//    }
//    Spacer(Modifier.height(12.dp))
//
//}
//
//@Composable
//private fun ExpenseLimitsDialogInputs(newIdeaDialogState : NewIdeaDialogState) {
//    if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit) {
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Each month limit")
//            Spacer(modifier = Modifier.width(8.dp))
//            Switch(
//                checked = newIdeaDialogState.value.eachMonth ?: true,
//                onCheckedChange = { mainScreenFeedViewModel.setSelectedToAllCategories(it) })
//        }
//    }
//    if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit || newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.IncomePlans) {
//        Spacer(modifier = Modifier.height(4.dp))
//    }
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(0.9f)
//            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(
//                text = if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit) {
//                    stringResource(R.string.limit_end_date_ideas)
//                } else {
//                    stringResource(R.string.plan_end_date_ideas)
//                }
//            )
//            Text(text = "optional", style = MaterialTheme.typography.labelSmall)
//        }
//        Spacer(modifier = Modifier.width(12.dp))
//        Button(onClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(true) }) {
//            Text(
//                text = if (newIdeaDialogState.value.endDate != null) newIdeaDialogState.value.endDate.toString() else "Date",
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//        CustomDatePicker(
//            isVisible = newIdeaDialogState.value.isDateDialogVisible,
//            onNegativeClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(false) },
//            onPositiveClick = { date -> mainScreenFeedViewModel.setEndDate(date) }
//        )
//    }
//
//    if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit) {
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = stringResource(R.string.related_to_all_categories_ideas))
//            Spacer(modifier = Modifier.width(8.dp))
//            Switch(
//                checked = newIdeaDialogState.value.relatedToAllCategories ?: true,
//                onCheckedChange = { mainScreenFeedViewModel.setSelectedToAllCategories(it) })
//        }
//    }
//    Spacer(Modifier.height(12.dp)) //accept and decline button
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(end = 8.dp), horizontalArrangement = Arrangement.End
//    ) {
//        OutlinedButton(
//            modifier = Modifier.scale(0.8f),
//            onClick = { mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false) }) {
//            Text("Decline")
//        }
//        FilledTonalButton(modifier = Modifier.scale(0.9f), onClick = {
//            mainScreenFeedViewModel.addNewIdea()
//            mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false)
//        }) {
//            Text("Add")
//        }
//    }
//}
//
//@Composable
//private fun IncomePlanDialogInputs(newIdeaDialogState : NewIdeaDialogState) {
//    if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit) {
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Each month limit")
//            Spacer(modifier = Modifier.width(8.dp))
//            Switch(
//                checked = newIdeaDialogState.value.eachMonth ?: true,
//                onCheckedChange = { mainScreenFeedViewModel.setSelectedToAllCategories(it) })
//        }
//    }
//    if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit || newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.IncomePlans) {
//        Spacer(modifier = Modifier.height(4.dp))
//    }
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(0.9f)
//            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(
//                text = if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit) {
//                    stringResource(R.string.limit_end_date_ideas)
//                } else {
//                    stringResource(R.string.plan_end_date_ideas)
//                }
//            )
//            Text(text = "optional", style = MaterialTheme.typography.labelSmall)
//        }
//        Spacer(modifier = Modifier.width(12.dp))
//        Button(onClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(true) }) {
//            Text(
//                text = if (newIdeaDialogState.value.endDate != null) newIdeaDialogState.value.endDate.toString() else "Date",
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//        CustomDatePicker(
//            isVisible = newIdeaDialogState.value.isDateDialogVisible,
//            onNegativeClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(false) },
//            onPositiveClick = { date -> mainScreenFeedViewModel.setEndDate(date) }
//        )
//    }
//
//    if (newIdeaDialogState.value.typeSelected == IdeaSelectorTypes.ExpenseLimit) {
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = stringResource(R.string.related_to_all_categories_ideas))
//            Spacer(modifier = Modifier.width(8.dp))
//            Switch(
//                checked = newIdeaDialogState.value.relatedToAllCategories ?: true,
//                onCheckedChange = { mainScreenFeedViewModel.setSelectedToAllCategories(it) })
//        }
//    }
//    Spacer(Modifier.height(12.dp)) //accept and decline button
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(end = 8.dp), horizontalArrangement = Arrangement.End
//    ) {
//        OutlinedButton(
//            modifier = Modifier.scale(0.8f),
//            onClick = { mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false) }) {
//            Text("Decline")
//        }
//        FilledTonalButton(modifier = Modifier.scale(0.9f), onClick = {
//            mainScreenFeedViewModel.addNewIdea()
//            mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false)
//        }) {
//            Text("Add")
//        }
//    }
//}

@Composable
private fun IdeaInputField(preferableCurrency: Currency) {
    val focusManager = LocalFocusManager.current
    val controller = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val newIdeaDialogState = mainScreenFeedViewModel.newIdeaDialogState.collectAsState()
    val currentInputValue = newIdeaDialogState.value.goal
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.wrapContentHeight()
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .width(IntrinsicSize.Min)
                .padding(start = 12.dp),
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontSize = 44.sp,
                letterSpacing = 1.2.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            value = currentInputValue.toString(),
            onValueChange = { newText ->
                mainScreenFeedViewModel.setGoal(newText.toFloat())
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
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = preferableCurrency.ticker, style = MaterialTheme.typography.bodyMedium)
    }
}