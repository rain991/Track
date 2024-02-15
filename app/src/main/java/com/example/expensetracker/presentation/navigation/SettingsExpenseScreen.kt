package com.example.expensetracker.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.expensetracker.R
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.datastore
import com.example.expensetracker.data.viewmodels.common.BottomSheetViewModel
import com.example.expensetracker.presentation.bottomsheets.ExtendedButtonExample
import com.example.expensetracker.presentation.bottomsheets.SimplifiedBottomSheet
import com.example.expensetracker.presentation.other.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsExpenseScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val dataStore = datastore()
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