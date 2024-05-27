package com.example.track.presentation.screens.Core

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.track.R
import com.example.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.data.viewmodels.settingsScreen.CurrenciesSettingsViewModel
import com.example.track.presentation.components.bottomSheet.BottomSheet
import com.example.track.presentation.components.common.ui.Header
import com.example.track.presentation.components.other.ExtendedButtonExample
import com.example.track.presentation.components.screenComponents.SettingsScreenComponent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsExpenseScreen(navHostController: NavHostController) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val currenciesSettingsViewModel = koinViewModel<CurrenciesSettingsViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val toastState = currenciesSettingsViewModel.toastStateFlow.collectAsState()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(R.string.settings))
        },
        floatingActionButton = {
            ExtendedButtonExample(
                isButtonExpanded = false,
                onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        BottomSheet(dataStoreManager = settingsData)
        if (toastState.value.length > 1) {
            Toast.makeText(LocalContext.current, toastState.value, Toast.LENGTH_SHORT).show()
            currenciesSettingsViewModel.clearToastMessage()
        }
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            SettingsScreenComponent(
                paddingValues = it,
                navHostController = navHostController,
                isPageNameVisible = isPageNameVisible.value,
                settingsData = settingsData
            )
        }
    }
}
