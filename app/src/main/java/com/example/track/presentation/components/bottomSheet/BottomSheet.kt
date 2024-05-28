package com.example.track.presentation.components.bottomSheet

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.track.R
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.other.constants.MIN_SUPPORTED_YEAR
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.domain.models.abstractLayer.CategoryEntity
import com.example.track.presentation.components.bottomSheet.composables.AmountInput
import com.example.track.presentation.components.common.ui.CategoryChip
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeConfig
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(dataStoreManager: DataStoreManager) {
    val context = LocalContext.current
    val warningMessage = stringResource(id = R.string.warning_bottom_sheet_exp)
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val bottomSheetViewState = bottomSheetViewModel.expenseViewState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true })
    val currentCurrency = bottomSheetViewModel.selectedCurrency.collectAsState(initial = CURRENCY_DEFAULT)
    val isAddingExpense = bottomSheetViewState.value.isAddingExpense
    val categoryList = if (isAddingExpense) {
        bottomSheetViewModel.expenseCategoryList
    } else {
        bottomSheetViewModel.incomeCategoryList
    }
    val bottomSheetTitle = if (isAddingExpense) {
        stringResource(R.string.expense)
    } else {
        stringResource(R.string.add_income_bottom_sheet)
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
                    .fillMaxHeight(0.65f)
                    .fillMaxWidth()
            )
            {
                Box(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.add),
                                style = MaterialTheme.typography.titleMedium,
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
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                        AmountInput(focusRequester, controller, currentCurrency.value!!)
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(label = stringResource(R.string.your_note_adding_exp))
                        DatePicker()
                        CategoriesGrid(categoryList)
                        Spacer(Modifier.weight(1f))
                        val coroutineScope = rememberCoroutineScope()
                        AcceptButton {
                            if (bottomSheetViewState.value.categoryPicked != null && bottomSheetViewState.value.datePicked.isBefore(
                                    LocalDate.now().plusDays(1)
                                ) && bottomSheetViewState.value.inputExpense != null && bottomSheetViewState.value.inputExpense!! > 0
                            ) {
                                if (bottomSheetViewState.value.isAddingExpense) {
                                    coroutineScope.launch {
                                        withContext(Dispatchers.IO) {
                                            bottomSheetViewModel.addExpense()
                                        }
                                        withContext(Dispatchers.Main) {
                                            bottomSheetViewModel.setBottomSheetExpanded(false)
                                        }
                                    }
                                } else {
                                    coroutineScope.launch {
                                        withContext(Dispatchers.IO) {
                                            bottomSheetViewModel.addIncome()
                                        }
                                        withContext(Dispatchers.Main) {
                                            bottomSheetViewModel.setBottomSheetExpanded(false)
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show()
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
    val bottomSheetViewState = bottomSheetViewModel.expenseViewState.collectAsState()
    val datePickerStateFlow = bottomSheetViewState.value.timePickerState
    val datePickerState = UseCaseState(visible = datePickerStateFlow)
    var text by remember { mutableStateOf(bottomSheetViewState.value.datePicked.toString()) }
    text = if (!bottomSheetViewModel.isDateInOtherSpan(bottomSheetViewState.value.datePicked)) {
        stringResource(R.string.date)
    } else {
        bottomSheetViewState.value.datePicked.toString()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedDateButton(
            text = stringResource(R.string.today_add_exp),
            isSelected = (bottomSheetViewState.value.todayButtonActiveState)
        ) { bottomSheetViewModel.setDatePicked(LocalDate.now()) }
        OutlinedDateButton(
            text = stringResource(R.string.yesterday_add_exp),
            isSelected = (bottomSheetViewState.value.yesterdayButtonActiveState)
        ) { bottomSheetViewModel.setDatePicked(LocalDate.now().minusDays(1)) }
        Button(onClick = { bottomSheetViewModel.togglePickerState() }) {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
        DateTimeDialog(
            state = datePickerState,
            selection = DateTimeSelection.Date(selectedDate = bottomSheetViewState.value.datePicked,
                onNegativeClick = { bottomSheetViewModel.togglePickerState() },
                onPositiveClick = { date ->
                    bottomSheetViewModel.setDatePicked(date)
                    bottomSheetViewModel.togglePickerState()
                }),
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            config = DateTimeConfig(minYear = MIN_SUPPORTED_YEAR, maxYear = LocalDate.now().year - 1) // WARNING about -1
        )
    }
}
@Composable
private fun CategoriesGrid(categoryList: List<CategoryEntity>) {
    val lazyHorizontalState = rememberLazyStaggeredGridState()
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val bottomSheetViewState = bottomSheetViewModel.expenseViewState.collectAsState()
    val selected = bottomSheetViewState.value.categoryPicked // warning
    LazyHorizontalStaggeredGrid(
        modifier = Modifier.height(84.dp),
        rows = StaggeredGridCells.Fixed(2),
        state = lazyHorizontalState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalItemSpacing = 8.dp, contentPadding = PaddingValues(horizontal = 12.dp)
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
    val bottomSheetViewState = bottomSheetViewModel.expenseViewState.collectAsState()
    val text = bottomSheetViewState.value.note
   TextField(
        value = text,
        onValueChange = { bottomSheetViewModel.setNote(it) },
        label = { Text(label) },
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



@Composable
private fun AcceptButton(onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .widthIn(60.dp)
                .wrapContentHeight()
                .padding(bottom = 4.dp), shape = RoundedCornerShape(80)
        ) {
            Text(text = stringResource(R.string.add_it_button), style = MaterialTheme.typography.bodyLarge.copy(letterSpacing = 0.8.sp))
        }
    }
}
