package com.example.expensetracker.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import com.example.expensetracker.data.ExpensesDAO
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpensesListRepositoryImpl
import com.example.expensetracker.data.ExpensesListRepositoryImpl.autoIncrementId

import com.example.expensetracker.domain.ExpenseItem
import kotlinx.coroutines.launch
import java.time.LocalDate


@Composable
fun ExtendedButtonExample(
    isExpanded: Boolean,
    onClick: () -> Unit
) {  // ALL fill-maxsize should be checked in final version

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp, end = 16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            ExtendedFloatingActionButton(
                expanded = isExpanded,
                onClick = onClick, //TBC
                icon = { Icon(Icons.Filled.Edit, "Extended floating action button.") },
                text = { Text(text = "Extended FAB") })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(isVisible: Boolean, onDismiss: () -> Unit, expensesDAO: ExpensesDAO) {


    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false,confirmValueChange = {
            when (it) {
                SheetValue.Expanded -> {
                    false
                }

                else -> {
                    true
                }
            }

        })

    var currentExpenseAdded by remember { mutableFloatStateOf(0.0F) } // Expense adding value
    val scope = rememberCoroutineScope()
    val addToDB: (currentExpense: ExpenseItem) -> Unit = {
        scope.launch {
            expensesDAO.insertItem(it)
        }
    }

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState) {
            Row(modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth()) { //previously fillMaxSize
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
                                    .background( Brush.horizontalGradient(colorStops = arrayOf(0.0f to MaterialTheme.colorScheme.primary,
                                        0.5f to MaterialTheme.colorScheme.onPrimaryContainer,
                                        1f to MaterialTheme.colorScheme.primary))
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
                                    id = autoIncrementId,
                                    name = "NewName",
                                    date = LocalDate.now().toString(),
                                    enabled = true,
                                    value = currentExpenseAdded
                                )
                                ExpensesListRepositoryImpl.getExpensesList().add(currentExpense)  // TO BE RESTRUCTURED using ExpensesListRepositoryImpl methods
                                addToDB(currentExpense)


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
                                    id = autoIncrementId,
                                    name = "NewName",
                                    date = LocalDate.now().toString(),
                                    enabled = true,
                                    value = currentExpenseAdded
                                )
                                ExpensesListRepositoryImpl.getExpensesList().add(currentExpense)  // TO BE RESTRUCTURED using ExpensesListRepositoryImpl methods
                                addToDB(currentExpense)


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
                                    id = autoIncrementId,
                                    name = "NewName",
                                    date = LocalDate.now().toString(),
                                    enabled = true,
                                    value = currentExpenseAdded
                                )
                                ExpensesListRepositoryImpl.getExpensesList().add(currentExpense)  // TO BE RESTRUCTURED using ExpensesListRepositoryImpl methods
                                addToDB(currentExpense)


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

@Composable
fun ExpensesCardTypeSimple() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = stringResource(R.string.Lorem))
    }
}


@Composable
fun TwoButtonsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Button(
            modifier = Modifier
                .weight(2f)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary)
                .height(56.dp),
            onClick = { /* Handle button click */ }
        ) {
            Text(text = "0")
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary)
                .height(56.dp),
            onClick = { /* Handle button click */ }
        ) {
            Text(text = ".")
        }
    }
}



