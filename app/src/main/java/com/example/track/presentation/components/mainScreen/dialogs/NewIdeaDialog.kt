package com.example.track.presentation.components.mainScreen.dialogs


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.track.R
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesCategoriesListRepositoryImpl
import com.example.track.domain.models.currency.Currency
import com.example.track.data.viewmodels.mainScreen.MainScreenFeedViewModel
import com.example.track.presentation.common.ui.CategoryChip
import com.example.track.presentation.common.ui.CustomDatePicker
import com.example.track.presentation.states.common.IdeaSelectorTypes
import com.example.track.presentation.states.common.NewIdeaDialogState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewIdeaDialog() {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val newIdeaDialogState = mainScreenFeedViewModel.newIdeaDialogState.collectAsState()
    val currenciesPreferenceRepositoryImpl = koinInject<CurrenciesPreferenceRepositoryImpl>()
     val coroutineScope = rememberCoroutineScope()
    Log.d("MyLog", "NewIdeaDialog: ${newIdeaDialogState.value}")
    val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().collectAsState(initial = CURRENCY_DEFAULT)
    Dialog(
        onDismissRequest = { mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
        ) {
            if (newIdeaDialogState.value.warningMessage != "") {
                Toast.makeText(LocalContext.current, newIdeaDialogState.value.warningMessage, Toast.LENGTH_SHORT).show()
                mainScreenFeedViewModel.setWarningMessage("")
            }
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
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 12.dp)
                    ) {
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
                        .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = when (newIdeaDialogState.value.typeSelected) {
                            IdeaSelectorTypes.ExpenseLimit -> "Limit planned"
                            IdeaSelectorTypes.IncomePlans -> "Income planned"
                            IdeaSelectorTypes.Savings -> "Savings planned"
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IdeaInputField(preferableCurrency = preferableCurrency.value)
                    Spacer(modifier = Modifier.weight(1f))
                }
                when (newIdeaDialogState.value.typeSelected) {
                    IdeaSelectorTypes.ExpenseLimit -> ExpenseLimitsDialogInputs(newIdeaDialogState = newIdeaDialogState.value)
                    IdeaSelectorTypes.IncomePlans -> IncomePlanDialogInputs(newIdeaDialogState = newIdeaDialogState.value)
                    IdeaSelectorTypes.Savings -> SavingsDialogInputs(newIdeaDialogState = newIdeaDialogState.value)
                }
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
                        coroutineScope.launch{
                            mainScreenFeedViewModel.addNewIdea()
                        }
                      //  mainScreenFeedViewModel.setIsNewIdeaDialogVisible(false)
                    }) {
                        Text("Add")
                    }
                }
            }
        }
    }
}


@Composable
private fun SavingsDialogInputs(
    newIdeaDialogState: NewIdeaDialogState
) {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val labelInputText = newIdeaDialogState.label
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = labelInputText ?: "",
            onValueChange = { mainScreenFeedViewModel.setLabel(it) },
            label = { "Saving for" },
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)),
                    shape = RoundedCornerShape(4.dp)
                ),
            colors = TextFieldDefaults.colors().copy(unfocusedContainerColor = MaterialTheme.colorScheme.background)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Included in budget")
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = newIdeaDialogState.includedInBudget ?: true,
            onCheckedChange = { it -> mainScreenFeedViewModel.setIncludedInBudget(it) })
    }
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.plan_end_date_ideas))
            Text(text = "optional", style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Button(onClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(true) }) {
            Text(
                text = if (newIdeaDialogState.endDate != null) newIdeaDialogState.endDate.toString() else "Date",
                style = MaterialTheme.typography.bodySmall
            )
        }
        CustomDatePicker(
            isVisible = newIdeaDialogState.isDateDialogVisible,
            onNegativeClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(false) },
            onPositiveClick = { date -> mainScreenFeedViewModel.setEndDate(date) }
        )
    }
}

@Composable
private fun ExpenseLimitsDialogInputs(newIdeaDialogState: NewIdeaDialogState) {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Each month limit")
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = newIdeaDialogState.eachMonth ?: true,
            onCheckedChange = { mainScreenFeedViewModel.setEachMonth(it) })
    }
    Spacer(modifier = Modifier.height(4.dp))
    if (newIdeaDialogState.eachMonth == false) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(R.string.limit_end_date_ideas))
                Text(text = "optional", style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(true) }) {
                Text(
                    text = if (newIdeaDialogState.endDate != null) newIdeaDialogState.endDate.toString() else "Date",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            CustomDatePicker(
                isVisible = newIdeaDialogState.isDateDialogVisible,
                onNegativeClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(false) },
                onPositiveClick = { date -> mainScreenFeedViewModel.setEndDate(date) }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.related_to_all_categories_ideas))
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = newIdeaDialogState.relatedToAllCategories ?: true,
            onCheckedChange = { mainScreenFeedViewModel.setSelectedToAllCategories(it) })
    }
    if (newIdeaDialogState.relatedToAllCategories != true) {
        NewIdeaCategoriesGrid()
    }
}

@Composable
private fun IncomePlanDialogInputs(newIdeaDialogState: NewIdeaDialogState) {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(R.string.plan_end_date_ideas))
            Text(text = "optional", style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Button(onClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(true) }) {
            Text(
                text = if (newIdeaDialogState.endDate != null) newIdeaDialogState.endDate.toString() else "Date",
                style = MaterialTheme.typography.bodySmall
            )
        }
        CustomDatePicker(
            isVisible = newIdeaDialogState.isDateDialogVisible,
            onNegativeClick = { mainScreenFeedViewModel.setIsDatePickerDialogVisible(false) },
            onPositiveClick = { date -> mainScreenFeedViewModel.setEndDate(date) }
        )
    }
}

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

@Composable
private fun NewIdeaCategoriesGrid() {
    val lazyHorizontalState = rememberLazyStaggeredGridState()
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val expenseCategoriesListRepositoryImpl = koinInject<ExpensesCategoriesListRepositoryImpl>()
    val expenseCategoriesList = expenseCategoriesListRepositoryImpl.getCategoriesList().collectAsState(initial = listOf())
    val bottomSheetViewState = mainScreenFeedViewModel.newIdeaDialogState.collectAsState()
    val firstSelectedCategory = bottomSheetViewState.value.selectedCategory1
    val secondSelectedCategory = bottomSheetViewState.value.selectedCategory2
    val thirdSelectedCategory = bottomSheetViewState.value.selectedCategory3
    LazyHorizontalStaggeredGrid(
        modifier = Modifier.height(84.dp),
        rows = StaggeredGridCells.Fixed(2),
        state = lazyHorizontalState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalItemSpacing = 2.dp, contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        items(count = expenseCategoriesList.value.size) { index ->
            val item = expenseCategoriesList.value[index]
            CategoryChip(
                category = item,
                isSelected = (item == firstSelectedCategory || item == secondSelectedCategory || item == thirdSelectedCategory),
                chipScale = 0.92f,
                onSelect = { mainScreenFeedViewModel.setSelectedCategory(item) })
        }
    }
}