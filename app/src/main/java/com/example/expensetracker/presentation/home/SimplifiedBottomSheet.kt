package com.example.expensetracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.expensetracker.R
import com.example.expensetracker.data.SettingsData
import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.data.viewmodels.BottomSheetViewModel
import com.example.expensetracker.domain.usecases.categoriesusecases.GetCategoryListUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.AddExpensesItemUseCase
import com.example.expensetracker.presentation.other.ConfirmationButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import org.koin.compose.koinInject
import java.time.LocalDate
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SimplifiedBottomSheet(isVisible: Boolean, settingsData: SettingsData) {
    val bottomSheetViewModel = koinInject<BottomSheetViewModel>()
    val categoryList = koinInject<GetCategoryListUseCase>()
    val addExpensesItemUseCase = koinInject<AddExpensesItemUseCase>()
    val isAcceptButtonAvailable by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true })
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
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Text(text = stringResource(R.string.add_expenses), fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(12.dp))
                    AmountInput(focusRequester, currentExpenseAdded, controller, settingsData)
                    DatePicker(bottomSheetViewModel = bottomSheetViewModel)
                    SimpleOutlinedTextFieldSample(label = "Note")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CategoriesGrid(categoryList)
//                        Button(onClick = { /*Adding New Category Call*/ }) {
//                            Icon(painter = painterResource(id = R.drawable.sharp_add_24), contentDescription = stringResource(R.string.add_new_category))
//                        }
                    }
                    ConfirmationButton(
                        Modifier
                            .padding(bottom = 40.dp))
                }
            }
        }
    }
}

@Composable
private fun DatePicker(bottomSheetViewModel: BottomSheetViewModel) {
    val timePickerState = rememberUseCaseState(visible = false)
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        OutlinedButtonWithAnimation("Today")

        OutlinedButtonWithAnimation("Yesterday")

        if (selectedDate != null) {
            Text(text = selectedDate.toString(), style = MaterialTheme.typography.bodySmall)
        } else {
            DateTimeDialog(state = timePickerState, selection = DateTimeSelection.Date { date ->
                selectedDate.value = date
                timePickerState.hide()
            }, properties = DialogProperties())
        }

    }

}

@Composable
private fun SimpleOutlinedTextFieldSample(label: String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) }
    )
}

@Composable
fun OutlinedButtonWithAnimation(text: String) {
    var isSelected by remember { mutableStateOf(false) }

    Button(
        onClick = {
            isSelected = !isSelected
        },
        modifier = Modifier
            .background(
                color = if (isSelected) Color.Gray else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Text(
            text = text,
            style = if (isSelected) MaterialTheme.typography.titleSmall.copy(color = Color.White)
            else MaterialTheme.typography.titleSmall
        )
    }
}


@Composable
fun CategoryCard(category: ExpenseCategory, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .heightIn(min = 1.dp, max = 25.dp)
            .clickable { onClick }
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color(Random.nextLong(0xFFFFFFFF)))
            .padding(horizontal = 4.dp), contentAlignment = Alignment.Center
    ) {
        Text(text = category.name, style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp))
    }
}

@Composable
private fun CategoriesGrid(categoryList: GetCategoryListUseCase) {
    val lazyHorizontalState = rememberLazyStaggeredGridState()
    LazyHorizontalStaggeredGrid(
        modifier = Modifier.height(72.dp),
        rows = StaggeredGridCells.Adaptive(24.dp),
        state = lazyHorizontalState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalItemSpacing = 8.dp, contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(count = categoryList.getCategoryList().size) { index ->
            CategoryCard(category = categoryList.getCategoryList()[index]) {  /*Place for onClick for CategoryCard*/ }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AmountInput(
    focusRequester: FocusRequester,
    currentExpenseAdded: Float,
    controller: SoftwareKeyboardController?,
    settingsData: SettingsData
) {
    var currentExpenseAdded1 = currentExpenseAdded
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
            textStyle = MaterialTheme.typography.titleLarge.copy(fontSize = 52.sp, letterSpacing = 1.3.sp),
            value = currentExpenseAdded1.toString(),
            onValueChange = { newText ->
                val filteredText = newText.filter { it.isDigit() || it in setOf('.', '+', '-') }
                if (filteredText.isNotBlank()) {
                    currentExpenseAdded1 = filteredText.toFloatOrNull() ?: 0f
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    controller?.hide()
                    focusRequester.freeFocus()
                }
            ),
            maxLines = 1,

            )
        Text(text = settingsData.getCurrency(), style = MaterialTheme.typography.titleSmall)
    }
}

