package com.example.expensetracker.presentation.bottomsheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.data.converters.convertLocalDateToDate
import com.example.expensetracker.data.database.ExpenseItemsDAO
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import com.example.expensetracker.domain.usecases.expenseusecases.AddExpensesItemUseCase
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.time.LocalDate


// WARNING bottomsheet is deprecated, should use simplifiedbottomsheet instead
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(isVisible: Boolean, onDismiss: () -> Unit, expenseItemsDAO: ExpenseItemsDAO, expensesListRepository: ExpensesListRepositoryImpl) {
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false, confirmValueChange = {
            when (it) {
                SheetValue.Expanded -> {
                    false
                }

                else -> {
                    true
                }
            }
        })
    val composableScope = rememberCoroutineScope()
    val addExpensesItemUseCase = koinInject<AddExpensesItemUseCase>()

    var currentExpenseAdded by remember { mutableFloatStateOf(0.0F) } // Expense adding value
    val scope = rememberCoroutineScope()
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
            ) { //previously fillMaxSize
                Box(
                    modifier = Modifier.weight(3.5F)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp, end = 8.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = currentExpenseAdded.toString(),
                                textAlign = TextAlign.Center,
                                fontSize = 26.sp
                            )
                        }
                        Row(  // 7, 8, 9 Row
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { currentExpenseAdded = currentExpenseAdded * 10 + 9.0f }
                            ) {
                                Text(text = "9", fontSize = 32.sp)
                            }
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { currentExpenseAdded = currentExpenseAdded * 10 + 8.0f }
                            ) {
                                Text(text = "8", fontSize = 32.sp)
                            }
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { currentExpenseAdded = currentExpenseAdded * 10 + 7.0f }
                            ) {
                                Text(text = "7", fontSize = 32.sp)
                            }

                        }
                        Row(  // 4, 5, 6 Row
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { currentExpenseAdded = currentExpenseAdded * 10 + 6.0f }
                            ) {
                                Text(text = "6", fontSize = 32.sp)
                            }
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(
                                        Brush.horizontalGradient(
                                            colorStops = arrayOf(
                                                0.0f to MaterialTheme.colorScheme.primary,
                                                0.5f to MaterialTheme.colorScheme.onPrimaryContainer,
                                                1f to MaterialTheme.colorScheme.primary
                                            )
                                        )
                                    )
                                    .height(56.dp),
                                onClick = { currentExpenseAdded = currentExpenseAdded * 10 + 5.0f }
                            ) {
                                Text(text = "5", fontSize = 32.sp)
                            }
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { currentExpenseAdded = currentExpenseAdded * 10 + 4.0f }
                            ) {
                                Text(text = "4", fontSize = 32.sp)
                            }
                        }
                        Row(   // 1, 2, 3 Row
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { currentExpenseAdded = currentExpenseAdded * 10 + 3.0f }
                            ) {
                                Text(text = "3", fontSize = 32.sp)
                            }
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { currentExpenseAdded = currentExpenseAdded * 10 + 2.0f }
                            ) {
                                Text(text = "2", fontSize = 32.sp)
                            }
                            // Smaller button
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { currentExpenseAdded = currentExpenseAdded * 10 + 1.0f }
                            ) {
                                Text(text = "1", fontSize = 32.sp)
                            }
                        }
                        Row(  // 0 and . row
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {

                            // Bigger button
                            Button(
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(8.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { currentExpenseAdded *= 10 }
                            ) {
                                Text(text = "0", fontSize = 32.sp)
                            }

                            // Smaller button
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .height(56.dp),
                                onClick = { /* Handle button click */ }
                            ) {
                                Text(
                                    text = ".",
                                    modifier = Modifier.fillMaxSize(),
                                    fontSize = 40.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                    }
                }
                Box(   // Right Block Box
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                                .weight(1f),  // Right box height parameters to be changed
                            onClick = {
                                //Adding new expense
                                val currentExpense = ExpenseItem(
                                    note = "NewName",
                                    date = convertLocalDateToDate(LocalDate.now()) , enabled = false,
                                    value = currentExpenseAdded,
                                    categoryId = 2
                                )
                                composableScope.launch { addExpensesItemUseCase.addExpensesItem(currentExpense) }
                            }
                        ) {
                            Text(
                                text = "Add",
                                modifier = Modifier.fillMaxSize(),
                                fontSize = 100.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Button(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                                .weight(1f),
                            onClick = {
                                //Adding new expense
                                val currentExpense = ExpenseItem(
                                    note = "NewName",
                                    date = convertLocalDateToDate(LocalDate.now()), enabled = false,
                                    value = currentExpenseAdded,
                                    categoryId = 3
                                )
//                                expensesListRepository.getExpensesList()
//                                    .add(currentExpense)  // TO BE RESTRUCTURED using ExpensesListRepositoryImpl methods
//                                addToDB(currentExpense)


                            }
                        ) {
                            Text(
                                text = "Add",
                                modifier = Modifier.fillMaxSize(),
                                fontSize = 40.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Button(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                                .weight(1f),
                            onClick = {
                                //Adding new expense
                                val currentExpense = ExpenseItem(
                                    note = "NewName",
                                    date = convertLocalDateToDate(LocalDate.now()), enabled = false,
                                    value = currentExpenseAdded,
                                    categoryId = 4
                                )
//                                expensesListRepository.getExpensesList().add(currentExpense)
//                                addToDB(currentExpense)
                            }

                        ) {
                            Text(
                                text = "Add",
                                modifier = Modifier.fillMaxSize(),
                                fontSize = 40.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }


    }
}