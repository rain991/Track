package com.example.expensetracker.presentation.other


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.viewmodels.BottomSheetViewModel
import com.example.expensetracker.presentation.bottomsheets.ExtendedButtonExample
import com.example.expensetracker.presentation.bottomsheets.SimplifiedBottomSheet
import com.example.expensetracker.presentation.home.ExpensesLazyColumn
import com.example.expensetracker.presentation.home.MainInfoComposable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun PagerFirstScreen() { // Settings
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    androidx.compose.material3.Scaffold(
        topBar = {
            Header(categoryName = stringResource(R.string.settings))
        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = false, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) { // Settings screen
            SimplifiedBottomSheet(dataStoreManager = settingsData)
        }
    }

}

@Composable
fun PagerSecondScreen() {  // Main and Primary screen
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val expensesListRepositoryImpl = koinInject<ExpensesListRepositoryImpl>()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(categoryName = stringResource(R.string.expenses))
        },bottomBar = {

        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = true, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
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
        SimplifiedBottomSheet(dataStoreManager = settingsData)
    }
}

@Composable
fun PagerThirdScreen() {  // Statistics Screen
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    androidx.compose.material3.Scaffold(
        topBar = {
            Header(categoryName = stringResource(R.string.statistic))
        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = false, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true)  })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SimplifiedBottomSheet(dataStoreManager = settingsData)
        }
    }
}
