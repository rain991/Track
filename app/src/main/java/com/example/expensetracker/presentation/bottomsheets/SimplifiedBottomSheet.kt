package com.example.expensetracker.presentation.bottomsheets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.expensetracker.R
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import com.example.expensetracker.data.viewmodels.BottomSheetViewModel
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeConfig
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SimplifiedBottomSheet(dataStoreManager: DataStoreManager) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    bottomSheetViewModel.setDatePicked(LocalDate.now())
    val isVisible = bottomSheetViewModel.isBottomSheetExpanded.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true })
    if (isVisible.value) {
        ModalBottomSheet(
            onDismissRequest = {
                bottomSheetViewModel.setBottomSheetExpanded(false)
            },
            sheetState = sheetState
        ) {
            val controller = LocalSoftwareKeyboardController.current
            val focusRequester = remember { FocusRequester() }
            Column(   // All content
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
            )
            {
                Box(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                        Text(
                            text = stringResource(R.string.add_expenses),
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        AmountInput(focusRequester, controller, dataStoreManager)
                        Spacer(Modifier.height(12.dp))
                        SimpleOutlinedTextFieldSample(label = "Note")
                        DatePicker()
                        CategoriesGrid()
                        //  Spacer(Modifier.weight(1f))
                        val coroutineScope = rememberCoroutineScope()
                        AcceptButton {
                            bottomSheetViewModel.setIsAddingNewExpense(true)
                            coroutineScope.launch {
                                withContext(Dispatchers.IO){
                                    bottomSheetViewModel.addExpense()
                                }
                                withContext(Dispatchers.Main) {
                                    bottomSheetViewModel.setBottomSheetExpanded(false)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun CategoryCard(category: ExpenseCategory) {  // should be refactored for other ViewModel usages
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val activeCategory = bottomSheetViewModel.categoryPicked.collectAsState()

    Row(
        modifier = Modifier
            .heightIn(min = 1.dp, max = 25.dp)
            .clickable {
                if (activeCategory.value != category) {
                    bottomSheetViewModel.setCategoryPicked(category)
                } else bottomSheetViewModel.setCategoryPicked(null)
                Log.d("MyLog", "input : {${bottomSheetViewModel.inputExpense.value}}")
                Log.d("MyLog", "today : {${bottomSheetViewModel.todayButtonActiveState.value}}")
                Log.d("MyLog", "yesterday : {${bottomSheetViewModel.yesterdayButtonActiveState.value}}")
                Log.d("MyLog", "date : {${bottomSheetViewModel.datePicked.value}}")
                Log.d("MyLog", "active category : {${activeCategory}}")
            }
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color(Random.nextLong(0xFFFFFFFF)))
            .padding(horizontal = 4.dp)
    ) {
        if (activeCategory.value != null) {
            if (activeCategory.value == category) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = null, tint = Color.Red)
            }
        }
        Text(text = category.name, style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp))

    }
}

@Composable
private fun CategoriesGrid() {
    val lazyHorizontalState = rememberLazyStaggeredGridState()
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val categoryList = bottomSheetViewModel.categoryList
    LazyHorizontalStaggeredGrid(
        modifier = Modifier.height(72.dp),
        rows = StaggeredGridCells.Adaptive(24.dp),
        state = lazyHorizontalState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalItemSpacing = 8.dp, contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(count = categoryList.size) { index ->
            val item = categoryList[index]
            CategoryCard(category = item)
        }
    }
}

@Composable
private fun DatePicker() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val datePickerStateFlow by bottomSheetViewModel.timePickerState.collectAsState()
    val datePickerState = UseCaseState(visible = datePickerStateFlow)
    val selectedDate by bottomSheetViewModel.datePicked.collectAsState()
    var text by remember { mutableStateOf(selectedDate.toString()) }
    if (!bottomSheetViewModel.isDateInOtherSpan(selectedDate)) {
        text = stringResource(R.string.other)
    } else {
        text = selectedDate.toString()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        OutlinedDateButton(OutlinedButtonText.TODAY) { bottomSheetViewModel.setDatePicked(LocalDate.now()) }
        OutlinedDateButton(OutlinedButtonText.YESTERDAY) { bottomSheetViewModel.setDatePicked(LocalDate.now().minusDays(1)) }

        Button(onClick = { bottomSheetViewModel.togglePickerState() }) {
            Text(text = text, style = MaterialTheme.typography.titleSmall)
        }
        DateTimeDialog(
            state = datePickerState,
            selection = DateTimeSelection.Date(selectedDate = selectedDate, onNegativeClick = { bottomSheetViewModel.togglePickerState() },
                onPositiveClick = { date ->
                    bottomSheetViewModel.setDatePicked(date)
                    bottomSheetViewModel.togglePickerState()
                }),
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            config = DateTimeConfig(minYear = 2000, maxYear = LocalDate.now().year - 1) // WARNING about -1
        )
    }
}


enum class OutlinedButtonText {
    TODAY, YESTERDAY
}

@Composable
private fun OutlinedDateButton(type: OutlinedButtonText, onClick: () -> Unit) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    lateinit var text: String
    lateinit var state: State<Boolean>
    when (type) {
        OutlinedButtonText.TODAY -> {
            text = stringResource(id = R.string.today)
            state = bottomSheetViewModel.todayButtonActiveState.collectAsState()
        }

        OutlinedButtonText.YESTERDAY -> {
            text = stringResource(id = R.string.yesterday)
            state = bottomSheetViewModel.yesterdayButtonActiveState.collectAsState()
        }
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .background(
                color = if (state.value) Color.Black else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Text(
            text = text,
            style = if (state.value) MaterialTheme.typography.titleSmall.copy(color = Color.White)
            else MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun SimpleOutlinedTextFieldSample(label: String) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val text by bottomSheetViewModel.note.collectAsState()
    OutlinedTextField(
        value = text,
        onValueChange = { bottomSheetViewModel.setNote(it) },
        label = { Text(label) }, modifier = Modifier.padding(horizontal = 8.dp),
        maxLines = 2
    )
}




@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AmountInput(
    focusRequester: FocusRequester,
    controller: SoftwareKeyboardController?,
    dataStoreManager: DataStoreManager
) {
    val focusManager = LocalFocusManager.current
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val currentExpense = bottomSheetViewModel.inputExpense.collectAsState()
    val currentCurrency = dataStoreManager.currencyFlow.collectAsState(initial = "USD")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .width(IntrinsicSize.Min)
                .padding(horizontal = 12.dp),
            textStyle = MaterialTheme.typography.titleLarge.copy(fontSize = 54.sp, letterSpacing = 1.3.sp),
            value = currentExpense.value.toString(),
            onValueChange = { newText ->
                bottomSheetViewModel.setInputExpense(newText.toFloat())
                Log.d("MyLog", "input22: ${newText.toFloat()}")
                Log.d("MyLog", "VM: ${bottomSheetViewModel.inputExpense.value}")
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
        Text(text = currentCurrency.value, style = MaterialTheme.typography.titleSmall)
    }
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
                .wrapContentHeight(), shape = RoundedCornerShape(80)
        ) {

            Text(text = stringResource(R.string.add_it_button))
        }
    }
}
