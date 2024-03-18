package com.example.track.presentation.navigation.Screens.Core

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.data.DataStoreManager
import com.example.track.data.constants.CURRENCY_DEFAULT
import com.example.track.data.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.data.viewmodels.settingsScreen.SettingsViewModel
import com.example.track.presentation.bottomsheets.other.ExtendedButtonExample
import com.example.track.presentation.bottomsheets.sheets.SimplifiedBottomSheet
import com.example.track.presentation.components.settingsScreen.common.ThemePreferences
import com.example.track.presentation.components.settingsScreen.components.CurrenciesSettings
import com.example.track.presentation.other.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsExpenseScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val preferableCurrencyState = settingsViewModel.preferableCurrencyStateFlow.collectAsState(initial = CURRENCY_DEFAULT)
    val firstAdditionalCurrencyState = settingsViewModel.firstAdditionalCurrencyStateFlow.collectAsState(initial = null)
    val secondAdditionalCurrencyState = settingsViewModel.secondAdditionalCurrencyStateFlow.collectAsState(initial = null)
    val thirdAdditionalCurrencyState = settingsViewModel.thirdAdditionalCurrencyStateFlow.collectAsState(initial = null)
    val fourthAdditionalCurrencyState =
        settingsViewModel.fourthAdditionalCurrencyStateFlow.collectAsState(
            initial = null
        )
    val toastState = settingsViewModel.toastStateFlow.collectAsState()
    androidx.compose.material3.Scaffold(
        topBar = {
            if (isPageNameVisible.value) Header(categoryName = stringResource(R.string.settings))
        },
        floatingActionButton = {
            ExtendedButtonExample(
                isButtonExpanded = false,
                onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            if (!isPageNameVisible.value) Spacer(modifier = Modifier.height(12.dp))

            ThemePreferences(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), dataStoreManager = settingsData
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            Spacer(modifier = Modifier.height(10.dp))
            CurrenciesSettings(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                preferableCurrency = preferableCurrencyState.value!!,
                firstAdditionalCurrency = firstAdditionalCurrencyState.value,
                secondAdditionalCurrency = secondAdditionalCurrencyState.value,
                thirdAdditionalCurrency = thirdAdditionalCurrencyState.value,
                fourthAdditionalCurrency = fourthAdditionalCurrencyState.value
            )
        }
        SimplifiedBottomSheet(dataStoreManager = settingsData)
        if (toastState.value.length > 1) {
            Toast.makeText(LocalContext.current, toastState.value, Toast.LENGTH_SHORT).show()
            settingsViewModel.clearToastMessage()
        }
    }
}