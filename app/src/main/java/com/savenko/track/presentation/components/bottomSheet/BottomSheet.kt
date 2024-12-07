package com.savenko.track.presentation.components.bottomSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.formatDateWithoutYear
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.presentation.components.customComponents.CategoryChip
import com.savenko.track.presentation.components.customComponents.ErrorText
import com.savenko.track.presentation.other.composableTypes.errors.BottomSheetErrors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(bottomSheetViewModel: BottomSheetViewModel) {
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
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            )
            {
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
                        Spacer(modifier = Modifier.height(16.dp))
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
                        Spacer(modifier = Modifier.height(16.dp))
                        AnimatedVisibility(
                            visible = bottomSheetViewState.value.datePicked != null,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            bottomSheetViewState.value.datePicked?.let {
                                bottomSheetViewState.value.datePicked?.let { localDate ->
                                    BottomSheetDateButton(
                                        modifier = Modifier.height(IntrinsicSize.Min),
                                        text = if (bottomSheetViewState.value.todayButtonActiveState) {
                                            stringResource(id = R.string.today)
                                        } else if (bottomSheetViewState.value.yesterdayButtonActiveState) {
                                            stringResource(id = R.string.yesterday)
                                        } else {
                                            formatDateWithoutYear(convertLocalDateToDate(localDate))
                                        },
                                        isSelected = true,
                                        date = localDate,
                                        needsAdditionalDateNotation = (bottomSheetViewState.value.todayButtonActiveState || bottomSheetViewState.value.yesterdayButtonActiveState)
                                    ) {
                                        bottomSheetViewModel.setDatePicked(null)
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.align(Alignment.End)) {
                    val note = bottomSheetViewState.value.note
                    BottomSheetNoteInput(modifier = Modifier.padding(start = 8.dp), note = note) {
                        bottomSheetViewModel.setNote(it)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedVisibility(
                        visible = bottomSheetViewState.value.datePicked == null,
                        modifier = Modifier.wrapContentHeight()
                    ) {
                        BottomSheetDateSelection(
                            bottomSheetViewState = bottomSheetViewState.value,
                            isDayOutOfPredefinedSpan = { localDate ->
                                bottomSheetViewModel.isDateInOtherSpan(
                                    localDate
                                )
                            },
                            onSetDatePicked = { localDate -> bottomSheetViewModel.setDatePicked(localDate) },
                            onTogglePickerState = { bottomSheetViewModel.togglePickerState() })
                    }
                    Spacer(modifier = Modifier.height(8.dp))

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
                            ErrorText(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = stringResource(id = text)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    AnimatedVisibility(
                        visible = bottomSheetViewState.value.categoryPicked == null
                    ) {
                        BottomSheetCategorySelectionGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            categoryList = categoryList
                        ) {
                            bottomSheetViewModel.setCategoryPicked(it)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    BottomSheetAcceptButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
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