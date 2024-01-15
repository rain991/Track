package com.example.expensetracker.presentation.other


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.data.database.ExpensesDAO
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.viewmodels.MainViewModel
import com.example.expensetracker.presentation.bottomsheets.BottomSheet
import com.example.expensetracker.presentation.bottomsheets.ExtendedButtonExample
import com.example.expensetracker.presentation.bottomsheets.SimplifiedBottomSheet
import com.example.expensetracker.presentation.home.ExpensesLazyColumn
import com.example.expensetracker.presentation.home.MainInfoComposable
import org.koin.compose.koinInject

@Composable
fun PagerFirstScreen() { // Settings
    val mainViewModel = koinInject<MainViewModel>()
    val bottomSheetState = mainViewModel.isBottomSheetExpanded.collectAsState()
    androidx.compose.material3.Scaffold(
        topBar = {
            Header(categoryName = stringResource(R.string.settings))
        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = false, onClick = { mainViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) { // Settings screen
            SimplifiedBottomSheet(isVisible = bottomSheetState.value, settingsData = )
        }
    }

}

@Composable
fun PagerSecondScreen(expensesDAO: ExpensesDAO, expensesListRepositoryImpl: ExpensesListRepositoryImpl) {  // Main and Primary screen
    var isVisible by rememberSaveable { mutableStateOf(false) }

    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(categoryName = "Expenses")
        },bottomBar = {

        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = true, onClick = { isVisible = true })
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
fun PagerThirdScreen(expensesDAO: ExpensesDAO, expensesListRepositoryImpl: ExpensesListRepositoryImpl) {  // Statistics Screen
    var isVisible by rememberSaveable { mutableStateOf(false) }  // is BottomSheet visible

    androidx.compose.material3.Scaffold(
        topBar = {
            Header(categoryName = "Statistic")
        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = false, onClick = { isVisible = true })
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
