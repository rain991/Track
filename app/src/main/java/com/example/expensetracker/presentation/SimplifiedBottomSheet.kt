package com.example.expensetracker.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpenseItem
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesListRepositoryImpl
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimplifiedBottomSheet(isVisible: Boolean, onDismiss: () -> Unit, expensesDAO: ExpensesDAO, expensesListRepositoryImpl : ExpensesListRepositoryImpl) {
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = {
            true
        })

    var currentExpenseAdded by remember { mutableFloatStateOf(0.0F) } // Expense adding value
    val scope = rememberCoroutineScope()
    val addToDB: (currentExpense: ExpenseItem) -> Unit = {
        expensesListRepositoryImpl.getExpensesList().add(it)
        scope.launch {
            expensesDAO.insertItem(it)
        }
    }

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            Column(   // All content
                modifier = Modifier
                    .fillMaxHeight(0.65f)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = stringResource(R.string.add_expenses), fontSize = 24.sp, style=MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(40.dp))
                   // TextField(value = currentExpenseAdded, onValueChange = {}, suffix = { Text(text = "")})
                }

            }
        }
    }
}
