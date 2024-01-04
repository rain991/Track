package com.example.expensetracker.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesListRepositoryImpl

@Composable
fun FirstScreen(expensesDAO: ExpensesDAO, expensesListRepositoryImpl: ExpensesListRepositoryImpl) { // Settings
    var isVisible by rememberSaveable { mutableStateOf(false) }
    androidx.compose.material3.Scaffold(
        topBar = {
            Header(categoryName = "Settings")
        },
        floatingActionButton = {
            ExtendedButtonExample(isExpanded = false, onClick = { isVisible = true })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) { // Settings screen
            BottomSheet(
                isVisible = isVisible,
                onDismiss = { isVisible = false }, expensesDAO = expensesDAO, expensesListRepository =  expensesListRepositoryImpl
            )
        }
    }

}

@Composable
fun SecondScreen(expensesDAO: ExpensesDAO,expensesListRepositoryImpl: ExpensesListRepositoryImpl) {  // Main and Primary screen
    var isVisible by rememberSaveable { mutableStateOf(false) }

    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(categoryName = "Expenses")
        },bottomBar = {

        },
        floatingActionButton = {
            ExtendedButtonExample(isExpanded = true, onClick = { isVisible = true })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp))
                {
                   MainInfoComposable()
                   ExpensesLazyColumn(expenses = expensesListRepositoryImpl.getExpensesList())
        }
        BottomSheet(isVisible = isVisible, onDismiss = { isVisible = false }, expensesDAO, expensesListRepository = expensesListRepositoryImpl)
    }
}

@Composable
fun ThirdScreen(expensesDAO: ExpensesDAO,expensesListRepositoryImpl: ExpensesListRepositoryImpl) {  // Statistics Screen
    var isVisible by rememberSaveable { mutableStateOf(false) }  // is BottomSheet visible

    androidx.compose.material3.Scaffold(
        topBar = {
            Header(categoryName = "Statistic")
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
            BottomSheet(isVisible = isVisible, onDismiss = { isVisible = false }, expensesDAO, expensesListRepository = expensesListRepositoryImpl)
        }
    }
}
