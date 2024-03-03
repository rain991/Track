package com.example.expensetracker.presentation.navigation.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import com.example.expensetracker.presentation.home.settingsScreen.CurrenciesSettings
import com.example.expensetracker.presentation.home.settingsScreen.ThemePreferences
import com.example.expensetracker.presentation.other.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsExpenseScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    androidx.compose.material3.Scaffold(
        topBar = {
            if(isPageNameVisible.value) Header(categoryName = stringResource(R.string.settings))
        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = false, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            if(!isPageNameVisible.value) Spacer(modifier = Modifier.height(12.dp))
//            SettingsHeader(
//                modifier = Modifier
//                    .padding(start = 12.dp)
//                    .wrapContentSize(), dataStoreManager = settingsData
//            )
//            Spacer(modifier = Modifier.height(16.dp))
            CurrenciesSettings(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), dataStoreManager = settingsData
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            Spacer(modifier = Modifier.height(10.dp))
            ThemePreferences(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), dataStoreManager = settingsData
            )
        }
        SimplifiedBottomSheet(dataStoreManager = settingsData)
    }
}