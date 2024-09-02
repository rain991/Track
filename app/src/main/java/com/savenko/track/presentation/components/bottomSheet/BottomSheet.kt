package com.savenko.track.presentation.components.bottomSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.constants.FINANCIAL_NOTE_MAX_LENGTH
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.presentation.components.customComponents.CategoryChip
import com.savenko.track.presentation.components.customComponents.GradientInputTextField
import com.savenko.track.presentation.other.composableTypes.errors.BottomSheetErrors
import com.savenko.track.presentation.other.windowInfo.WindowInfo
import com.savenko.track.presentation.other.windowInfo.rememberWindowInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(bottomSheetViewModel: BottomSheetViewModel) {
    val windowInfo = rememberWindowInfo()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetViewState = bottomSheetViewModel.bottomSheetViewState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true })
    val isAddingExpense = bottomSheetViewState.value.isAddingExpense
    val categoryList = if (isAddingExpense) {
        bottomSheetViewModel.expenseCategoryList
    } else {
        bottomSheetViewModel.incomeCategoryList
    }
    val bottomSheetTitle = if (isAddingExpense) {
        stringResource(R.string.expense)
    } else {
        stringResource(R.string.income)
    }
    if (bottomSheetViewState.value.isBottomSheetExpanded) {
        ModalBottomSheet(
            onDismissRequest = {
                bottomSheetViewModel.setBottomSheetExpanded(false)
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(
                        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
                            0.75f
                        } else if (windowInfo.screenHeightInfo is WindowInfo.WindowType.Compact) {
                            1f
                        } else {
                            0.65f
                        }
                    )
                    .fillMaxWidth()
            )
            {
                Box(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        val listOfAvailableCurrencies = bottomSheetViewModel.listOfPreferableCurrencies
                        val selectedCurrencyIndex = bottomSheetViewState.value.currentSelectedCurrencyIndex
                        val currentCurrency =
                            listOfAvailableCurrencies.getOrNull(selectedCurrencyIndex) ?: CURRENCY_DEFAULT
                        val focusRequester = remember { FocusRequester() }
                        val controller = LocalSoftwareKeyboardController.current
                        LaunchedEffect(key1 = Unit) {
                            focusRequester.requestFocus()
                            controller?.show()
                        }

                        Column {
                            BottomSheetTransactionHeader(
                                bottomSheetTitle = bottomSheetTitle,
                                bottomSheetViewState = bottomSheetViewState
                            ) {
                                bottomSheetViewModel.toggleIsAddingExpense()
                            }
                            BottomSheetAmountInput(
                                currentCurrency = currentCurrency,
                                listOfAvailableCurrencies = listOfAvailableCurrencies,
                                hasErrors = bottomSheetViewState.value.warningMessage is BottomSheetErrors.IncorrectInputValue,
                                currentInputValue = bottomSheetViewState.value.inputValue ?: 0.0f,
                                focusRequester = focusRequester,
                                onInputValueChange = {
                                    bottomSheetViewModel.setInputValue(it)
                                },
                                keyboardController = controller,
                                onCurrencyChange = {
                                    bottomSheetViewModel.changeSelectedCurrency()
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            AnimatedVisibility(
                                visible = bottomSheetViewState.value.categoryPicked != null,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                bottomSheetViewState.value.categoryPicked?.let { category ->
                                    CategoryChip(
                                        modifier = Modifier.requiredHeightIn(min = 32.dp, max = 40.dp),
                                        category = category,
                                        isSelected = true,
                                        borderColor = MaterialTheme.colorScheme.primary,
                                        onSelect = { bottomSheetViewModel.setCategoryPicked(null) }
                                    )
                                }
                            }
                        }


                        // Note, Date and category input below
                        Spacer(Modifier.weight(1f))
                        val note = bottomSheetViewState.value.note
                        Box(modifier = Modifier.padding(start = 8.dp)) {
                            GradientInputTextField(
                                value = note,
                                label = stringResource(R.string.your_note_adding_exp)
                            ) {
                                if (it.length < FINANCIAL_NOTE_MAX_LENGTH) bottomSheetViewModel.setNote(it)
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        BottomSheetDatePicker(
                            bottomSheetViewState = bottomSheetViewState.value,
                            isDayOutOfPredefinedSpan = { localDate -> bottomSheetViewModel.isDateInOtherSpan(localDate) },
                            onSetDatePicked = { localDate -> bottomSheetViewModel.setDatePicked(localDate) },
                            onTogglePickerState = { bottomSheetViewModel.togglePickerState() })
                        Spacer(Modifier.height(16.dp))
                        if (bottomSheetViewState.value.warningMessage is BottomSheetErrors.CategoryNotSelected || bottomSheetViewState.value.warningMessage is BottomSheetErrors.ExpenseGroupingCategoryIsNotSelected || bottomSheetViewState.value.warningMessage is BottomSheetErrors.IncomeGroupingCategoryIsNotSelected) {
                            Box(modifier = Modifier.height(24.dp)) {
                                val text = when (bottomSheetViewState.value.warningMessage) {
                                    is BottomSheetErrors.CategoryNotSelected -> {
                                        BottomSheetErrors.CategoryNotSelected.error
                                    }

                                    is BottomSheetErrors.ExpenseGroupingCategoryIsNotSelected -> {
                                        BottomSheetErrors.ExpenseGroupingCategoryIsNotSelected.error
                                    }

                                    is BottomSheetErrors.IncomeGroupingCategoryIsNotSelected -> {
                                        BottomSheetErrors.IncomeGroupingCategoryIsNotSelected.error
                                    }

                                    else -> {
                                        R.string.error
                                    }
                                }
                                Text(
                                    text = stringResource(id = text),
                                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = bottomSheetViewState.value.categoryPicked == null,
                            modifier = Modifier.wrapContentHeight()
                        ) {
                            BottomSheetCategorySelectionGrid(categoryList = categoryList) {
                                bottomSheetViewModel.setCategoryPicked(it)
                            }
                        }
                        BottomSheetAcceptButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeightIn(40.dp, Dp.Infinity),
                        ) {
                            coroutineScope.launch {
                                bottomSheetViewModel.addFinancialItem()
                            }
                        }
                    }
                }
            }
        }
    }
}