package com.savenko.track.presentation.screens.additional.settingsScreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.savenko.track.R
import com.savenko.track.data.viewmodels.settingsScreen.currencies.CurrenciesSettingsViewModel
import com.savenko.track.presentation.components.screenRelated.SettingsSpecifiedScreenHeader
import com.savenko.track.presentation.navigation.Screen
import com.savenko.track.presentation.screens.screenComponents.additional.CurrenciesSettingsScreenComponent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * Part of Track [settings screen](com.savenko.track.presentation.screens.core.SettingsTrackScreenKt.SettingsTrackScreen)
 *
 * Handles user currencies preferences and shows all available Track currencies
 */
@Composable
fun CurrenciesSettingsScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val currenciesSettingsScreenViewModel = koinViewModel<CurrenciesSettingsViewModel>()
    val screenState = currenciesSettingsScreenViewModel.currenciesSettingsScreenState.collectAsState()
    val filteredCurrencies = currenciesSettingsScreenViewModel.filteredCurrencies.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader(stringResource(id = R.string.currencies)) {
                navController.navigate(Screen.MainScreen.route)
            }
        }
    ) {
        CurrenciesSettingsScreenComponent(
            paddingValues = it,
            state = screenState.value,
            filteredLazyListCurrencies = filteredCurrencies.value
        ) {
            coroutineScope.launch {
                currenciesSettingsScreenViewModel.onEvent(it)
            }
        }
    }
}