package com.example.expensetracker.presentation.navigation.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.expensetracker.data.viewmodels.common.BottomSheetViewModel
import com.example.expensetracker.presentation.bottomsheets.ExtendedButtonExample
import com.example.expensetracker.presentation.bottomsheets.SimplifiedBottomSheet
import com.example.expensetracker.presentation.home.mainScreen.ExpensesLazyColumn
import com.example.expensetracker.presentation.home.mainScreen.MainScreenFeed
import com.example.expensetracker.presentation.other.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun MainExpenseScreen() {  // Primary screen
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
          if(isPageNameVisible.value) Header(categoryName = stringResource(R.string.expenses))
        },bottomBar = { },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = true, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp)) //12.dp
        {
            if(!isPageNameVisible.value) Spacer(modifier = Modifier.height(12.dp))
            MainScreenFeed()
            ExpensesLazyColumn()
        }
        SimplifiedBottomSheet(dataStoreManager = settingsData)
    }
}