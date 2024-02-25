package com.example.expensetracker.presentation.navigation.Screens

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
import com.example.expensetracker.data.viewmodels.common.BottomSheetViewModel
import com.example.expensetracker.presentation.bottomsheets.ExtendedButtonExample
import com.example.expensetracker.presentation.bottomsheets.SimplifiedBottomSheet
import com.example.expensetracker.presentation.home.mainScreen.ExpensesLazyColumn
import com.example.expensetracker.presentation.home.mainScreen.MainInfoComposable
import com.example.expensetracker.presentation.home.mainScreen.MainScreenFeed
import com.example.expensetracker.presentation.other.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun MainExpenseScreen() {  // Primary screen
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(categoryName = stringResource(R.string.expenses))
        },bottomBar = {

        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = true, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
//        val offset = remember { mutableFloatStateOf(0f) }
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp)) //12.dp
        {
            MainScreenFeed()
            MainInfoComposable()
            ExpensesLazyColumn()
        }
        SimplifiedBottomSheet(dataStoreManager = settingsData)
    }
}