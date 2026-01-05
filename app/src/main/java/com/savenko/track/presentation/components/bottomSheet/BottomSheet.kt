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
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.components.customComponents.CategoryChip
import com.savenko.track.presentation.components.customComponents.ErrorText
import com.savenko.track.presentation.other.composableTypes.errors.BottomSheetErrors
import com.savenko.track.presentation.screens.states.core.common.BottomSheetViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(bottomSheetViewModel: BottomSheetViewModel) {
    val bottomSheetViewState = bottomSheetViewModel.bottomSheetViewState.collectAsState()
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true })
    val isAddingExpense = bottomSheetViewState.value.isAddingExpense
    val categoryList =
        if (isAddingExpense) bottomSheetViewModel.expenseCategoryList else bottomSheetViewModel.incomeCategoryList
    val bottomSheetTitle =
        stringResource(if (isAddingExpense) R.string.expense else R.string.income)

    if (bottomSheetViewState.value.isBottomSheetExpanded) {
        ModalBottomSheet(
            onDismissRequest = { bottomSheetViewModel.setBottomSheetExpanded(false) },
            sheetState = sheetState,
        ) {
            BottomSheetContent(
                viewModel = bottomSheetViewModel,
                bottomSheetTitle = bottomSheetTitle,
                categoryList = categoryList
            )
        }
    }
}

@Composable
private fun BottomSheetContent(
    viewModel: BottomSheetViewModel,
    bottomSheetTitle: String,
    categoryList: List<CategoryEntity>
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        TopSection(viewModel, bottomSheetTitle)
        Spacer(modifier = Modifier.height(16.dp))
        BottomSection(viewModel, categoryList)
    }
}

@Composable
private fun TopSection(
    viewModel: BottomSheetViewModel,
    bottomSheetTitle: String
) {
    val viewState = viewModel.bottomSheetViewState.collectAsState()
    val currencies = viewModel.listOfPreferableCurrencies
    val focusRequester = remember { FocusRequester() }
    val controller = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        controller?.show()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        val selectedCurrencyIndex = viewState.value.currentSelectedCurrencyIndex
        val currentCurrency = currencies.getOrNull(selectedCurrencyIndex) ?: CURRENCY_DEFAULT

        BottomSheetTransactionHeader(bottomSheetTitle, viewState) {
            viewModel.toggleIsAddingExpense()
        }

        BottomSheetAmountInput(
            currentCurrency = currentCurrency,
            listOfAvailableCurrencies = currencies,
            currentInputValue = viewState.value.inputValue ?: 0.0f,
            focusRequester = focusRequester,
            onInputValueChange = { viewModel.setInputValue(it) },
            keyboardController = controller,
            onCurrencyChange = { viewModel.changeSelectedCurrency() },
            hasErrors = viewState.value.warningMessage is BottomSheetErrors.IncorrectInputValue
        )
        Spacer(modifier = Modifier.height(16.dp))
        SelectedCategoryChip(viewState.value, onRemove = { viewModel.setCategoryPicked(null) })
        Spacer(modifier = Modifier.height(16.dp))
        SelectedDateChip(viewState.value, onRemove = { viewModel.setDatePicked(null) })
    }
}

@Composable
private fun BottomSection(
    viewModel: BottomSheetViewModel,
    categoryList: List<CategoryEntity>
) {
    val coroutineScope = rememberCoroutineScope()
    val viewState = viewModel.bottomSheetViewState.collectAsState()
    Column {
        BottomSheetNoteInput(modifier = Modifier.padding(start = 8.dp), note = viewState.value.note) {
            viewModel.setNote(it)
        }
        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(
            visible = viewState.value.datePicked == null
        ) {
            BottomSheetDateSelection(
                bottomSheetViewState = viewState.value,
                isDayOutOfPredefinedSpan = { viewModel.isDateInOtherSpan(it) },
                onSetDatePicked = { viewModel.setDatePicked(it) },
                onTogglePickerState = { viewModel.togglePickerState() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        ErrorDisplay(viewState.value)

        Spacer(modifier = Modifier.height(8.dp))
        AnimatedVisibility(
            visible = viewState.value.categoryPicked == null
        ) {
            BottomSheetCategorySelectionGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                categoryList = categoryList
            ) {
                viewModel.setCategoryPicked(it)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        BottomSheetAcceptButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            coroutineScope.launch { viewModel.addFinancialItem() }
        }
    }
}


@Composable
private fun SelectedCategoryChip(viewState: BottomSheetViewState, onRemove: () -> Unit) {
    AnimatedVisibility(
        visible = viewState.categoryPicked != null,
        //modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        viewState.categoryPicked?.let { category ->
            CategoryChip(
                modifier = Modifier.requiredHeightIn(min = 32.dp, max = 40.dp),
                category = category,
                isSelected = true,
                borderColor = MaterialTheme.colorScheme.primary,
                onSelect = { onRemove() }
            )
        }
    }
}

@Composable
private fun SelectedDateChip(viewState: BottomSheetViewState, onRemove: () -> Unit) {
    AnimatedVisibility(
        visible = viewState.datePicked != null,
        // modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        viewState.datePicked?.let { localDate ->
            BottomSheetDateButton(
                modifier = Modifier.height(IntrinsicSize.Min),
                text = when {
                    viewState.todayButtonActiveState -> stringResource(R.string.today)
                    viewState.yesterdayButtonActiveState -> stringResource(R.string.yesterday)
                    else -> formatDateWithoutYear(convertLocalDateToDate(localDate))
                },
                isSelected = true,
                date = localDate,
                needsAdditionalDateNotation = (viewState.todayButtonActiveState || viewState.yesterdayButtonActiveState)
            ) {
                onRemove()
            }
        }
    }
}

@Composable
private fun ErrorDisplay(viewState: BottomSheetViewState) {
    if (
        viewState.warningMessage is BottomSheetErrors.CategoryNotSelected ||
        viewState.warningMessage is BottomSheetErrors.ExpenseGroupingCategoryIsNotSelected ||
        viewState.warningMessage is BottomSheetErrors.IncomeGroupingCategoryIsNotSelected
    ) {
        Box(modifier = Modifier.height(24.dp)) {
            val textRes = when (viewState.warningMessage) {
                is BottomSheetErrors.CategoryNotSelected -> BottomSheetErrors.CategoryNotSelected.error
                is BottomSheetErrors.ExpenseGroupingCategoryIsNotSelected -> BottomSheetErrors.ExpenseGroupingCategoryIsNotSelected.error
                is BottomSheetErrors.IncomeGroupingCategoryIsNotSelected -> BottomSheetErrors.IncomeGroupingCategoryIsNotSelected.error
                else -> R.string.error
            }
            ErrorText(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(id = textRes)
            )
        }
    }
}
