package com.savenko.track.presentation.screens.core

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.savenko.track.R
import com.savenko.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.data.viewmodels.settingsScreen.CurrenciesSettingsViewModel
import com.savenko.track.presentation.components.bottomSheet.BottomSheet
import com.savenko.track.presentation.components.common.ui.Header
import com.savenko.track.presentation.components.other.ExtendedButtonExample
import com.savenko.track.presentation.components.screenComponents.core.SettingsScreenComponent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsTrackScreen(navHostController: NavHostController) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val currenciesSettingsViewModel = koinViewModel<CurrenciesSettingsViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val toastState = currenciesSettingsViewModel.toastStateFlow.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(R.string.settings))
        },
        floatingActionButton = {
            ExtendedButtonExample(
                isButtonExpanded = false,
                onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        BottomSheet()
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
