package com.example.visualisationexpensestracker.Presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.presentation.BottomSheet
import com.example.expensetracker.presentation.ExpensesCardTypeSimple
import com.example.expensetracker.presentation.ExtendedButtonExample
import com.example.expensetracker.presentation.Header

@Composable
fun FirstScreen(expensesDAO: ExpensesDAO) { // Settings
    var isVisible by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) { // Settings screen
        Header(categoryName = "Settings", isMenuButton =  false, isSearchButton = false)
        ExpensesCardTypeSimple()
        ExpensesCardTypeSimple()
        ExpensesCardTypeSimple()
        ExtendedButtonExample (false,onClick = { isVisible = true })
        BottomSheet(isVisible = isVisible,
            onDismiss = { isVisible = false }, expensesDAO =  expensesDAO
        )
    }
}

@Composable
fun SecondScreen(expensesDAO: ExpensesDAO) {  // Main and Primary screen
    var isVisible by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {// Column of Main Screen //
        Header(categoryName = "Expenses", isMenuButton =  true, isSearchButton = true)
        ExpensesCardTypeSimple()
        ExpensesCardTypeSimple()
        ExtendedButtonExample (true,onClick = { isVisible = true })
        BottomSheet(isVisible = isVisible, onDismiss = { isVisible = false },expensesDAO)
    }
}

@Composable
fun ThirdScreen(expensesDAO : ExpensesDAO) {  // Statistics Screen
    var isVisible by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Header("Statistics", true, false)
        ExpensesCardTypeSimple()
        ExtendedButtonExample (false,onClick = { isVisible = true })
        BottomSheet(isVisible = isVisible, onDismiss = { isVisible = false },expensesDAO)
    }
}