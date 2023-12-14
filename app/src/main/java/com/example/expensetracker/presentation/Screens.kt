package com.example.visualisationexpensestracker.Presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Scaffold
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesListRepositoryImpl
import com.example.expensetracker.domain.ExpenseItem
import com.example.expensetracker.presentation.BottomSheet
import com.example.expensetracker.presentation.ExpensesCardTypeSimple
import com.example.expensetracker.presentation.ExpensesLazyColumn
import com.example.expensetracker.presentation.ExtendedButtonExample
import com.example.expensetracker.presentation.Header

@Composable
fun FirstScreen(expensesDAO: ExpensesDAO) { // Settings
    var isVisible by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) { // Settings screen
        Header(categoryName = "Settings")


        ExtendedButtonExample(false, onClick = { isVisible = true })
        BottomSheet(
            isVisible = isVisible,
            onDismiss = { isVisible = false }, expensesDAO = expensesDAO
        )
    }
}

@Composable
fun SecondScreen(expensesDAO: ExpensesDAO) {  // Main and Primary screen
    var isVisible by rememberSaveable { mutableStateOf(false) }

    androidx.compose.material3.Scaffold(
        topBar = {
            Header(categoryName = "Expenses")
        },
        floatingActionButton = {
            ExtendedButtonExample(isExpanded = false, onClick = { isVisible = true })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ExpensesLazyColumn(expenses = ExpensesListRepositoryImpl.getExpensesList())
            ExtendedButtonExample(true, onClick = { isVisible = true })
            BottomSheet(isVisible = isVisible, onDismiss = { isVisible = false }, expensesDAO)
        }
    }

}

@Composable
fun ThirdScreen(expensesDAO: ExpensesDAO) {  // Statistics Screen
    var isVisible by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Header("Statistics")



        ExtendedButtonExample(false, onClick = { isVisible = true })
        BottomSheet(isVisible = isVisible, onDismiss = { isVisible = false }, expensesDAO)
    }

}