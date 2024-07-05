package com.savenko.track.presentation.components.bottomSheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.converters.dates.convertDateToLocalDate
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.formatDateWithoutYear
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.components.customComponents.CategoryChip
import com.savenko.track.presentation.components.customComponents.GradientInputTextField
import com.savenko.track.presentation.components.dialogs.datePickerDialogs.SingleDatePickerDialog
import com.savenko.track.presentation.other.composableTypes.errors.BottomSheetErrors
import com.savenko.track.presentation.other.windowInfo.WindowInfo
import com.savenko.track.presentation.other.windowInfo.rememberWindowInfo
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet() {
    val windowInfo = rememberWindowInfo()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val bottomSheetViewState = bottomSheetViewModel.bottomSheetViewState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true })
    val currentCurrency =
        bottomSheetViewModel.selectedCurrency.collectAsState(initial = CURRENCY_DEFAULT)
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
            val controller = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.add),
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                                textAlign = TextAlign.Center
                            )
                            AnimatedContent(
                                targetState = bottomSheetTitle,
                                label = "verticalTextChange",
                                transitionSpec = {
                                    slideInVertically { it } togetherWith slideOutVertically { -it }
                                }) {
                                TextButton(
                                    onClick = { bottomSheetViewModel.toggleIsAddingExpense() }
                                ) {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }
                        BottomSheetAmountInput(
                            focusRequester = focusRequester,
                            controller = controller,
                            currentCurrency = currentCurrency.value!!,
                            hasErrors = bottomSheetViewState.value.warningMessage is BottomSheetErrors.IncorrectInputValue
                        )
                        Spacer(Modifier.weight(1f))
                        OutlinedTextField(label = stringResource(R.string.your_note_adding_exp))
                        Spacer(Modifier.height(16.dp))
                        DatePicker()
                        Spacer(Modifier.height(16.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            CategoriesGrid(
                                categoryList = categoryList,
                                hasErrors = bottomSheetViewState.value.warningMessage is BottomSheetErrors.CategoryNotSelected
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        AcceptButton {
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

@Composable
private fun DatePicker() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val bottomSheetViewState = bottomSheetViewModel.bottomSheetViewState.collectAsState()
    var text by remember { mutableStateOf(formatDateWithoutYear(convertLocalDateToDate(bottomSheetViewState.value.datePicked))) }
    text = if (!bottomSheetViewModel.isDateInOtherSpan(bottomSheetViewState.value.datePicked)) {
        stringResource(R.string.date)
    } else {
        formatDateWithoutYear(convertLocalDateToDate(bottomSheetViewState.value.datePicked))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedDateButton(
            text = stringResource(R.string.today),
            isSelected = (bottomSheetViewState.value.todayButtonActiveState)
        ) { bottomSheetViewModel.setDatePicked(LocalDate.now()) }
        OutlinedDateButton(
            text = stringResource(R.string.yesterday),
            isSelected = (bottomSheetViewState.value.yesterdayButtonActiveState)
        ) { bottomSheetViewModel.setDatePicked(LocalDate.now().minusDays(1)) }
        Button(onClick = { bottomSheetViewModel.togglePickerState() }) {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
        SingleDatePickerDialog(
            isDialogVisible = bottomSheetViewState.value.timePickerState,
            futureDatePicker = false,
            onDecline = { bottomSheetViewModel.togglePickerState() }) { date ->
            bottomSheetViewModel.setDatePicked(convertDateToLocalDate(date))
            bottomSheetViewModel.togglePickerState()
        }
    }
}

@Composable
private fun CategoriesGrid(categoryList: List<CategoryEntity>, hasErrors: Boolean) {
    val lazyHorizontalState = rememberLazyStaggeredGridState()
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val bottomSheetViewState = bottomSheetViewModel.bottomSheetViewState.collectAsState()
    val selected = bottomSheetViewState.value.categoryPicked // warning
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (hasErrors) {
            Text(
                text = stringResource(id = BottomSheetErrors.CategoryNotSelected.error),
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LazyHorizontalStaggeredGrid(
            modifier = Modifier.heightIn(min = 48.dp, max = 156.dp),
            rows = StaggeredGridCells.FixedSize(40.dp),
            state = lazyHorizontalState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalItemSpacing = 8.dp, contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(count = categoryList.size) { index ->
                val item = categoryList[index]
                CategoryChip(
                    category = item,
                    isSelected = (selected == item),
                    onSelect = { bottomSheetViewModel.setCategoryPicked(item) })
            }
        }
    }

}

@Composable
private fun OutlinedDateButton(text: String, isSelected: Boolean, onSelect: () -> Unit) {
    Button(
        onClick = onSelect
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.selected_date_add_exp),
                    modifier = Modifier.height(22.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            if (isSelected) Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}

@Composable
private fun OutlinedTextField(label: String) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val bottomSheetViewState = bottomSheetViewModel.bottomSheetViewState.collectAsState()
    val text = bottomSheetViewState.value.note
    Box(modifier = Modifier.padding(start = 8.dp)) {
        GradientInputTextField(value = text, label = label) {
            bottomSheetViewModel.setNote(it)
        }
    }
}


@Composable
private fun AcceptButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(30.dp, Dp.Infinity),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .widthIn(60.dp)
                .wrapContentHeight()
                .padding(bottom = 4.dp), shape = RoundedCornerShape(80)
        ) {
            Text(
                text = stringResource(R.string.add_it),
                style = MaterialTheme.typography.bodyLarge.copy(letterSpacing = 0.8.sp)
            )
        }
    }
}
