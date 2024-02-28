package com.example.expensetracker.presentation.navigation.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.viewmodels.common.BottomSheetViewModel
import com.example.expensetracker.presentation.bottomsheets.ExtendedButtonExample
import com.example.expensetracker.presentation.bottomsheets.SimplifiedBottomSheet
import com.example.expensetracker.presentation.home.settingsScreen.SettingsHeader
import com.example.expensetracker.presentation.home.settingsScreen.ThemePreferences
import com.example.expensetracker.presentation.home.settingsScreen.UserPreferenceCard
import com.example.expensetracker.presentation.other.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsExpenseScreen() {
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
                .verticalScroll(rememberScrollState())
        ) {
            SettingsHeader(
                textModifier = Modifier
                    .padding(start = 12.dp)
                    .wrapContentSize(), dataStoreManager = settingsData
            )
            Spacer(modifier = Modifier.height(16.dp))
            UserPreferenceCard(
                cardModifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .wrapContentHeight(), dataStoreManager = settingsData
            )
            Spacer(modifier = Modifier.height(20.dp))
            ThemePreferences(
                cardModifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .wrapContentHeight(), dataStoreManager = settingsData
            )
        }
        SimplifiedBottomSheet(dataStoreManager = settingsData)
    }
}