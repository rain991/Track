package com.savenko.track.shared.presentation.screens.additional.settingsScreens

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.navigation.NavHostController
import com.savenko.track.shared.Platform
import com.savenko.track.shared.PlatformTarget
import com.savenko.track.shared.data.viewmodels.settingsScreen.currencies.CurrenciesSettingsViewModel
import com.savenko.track.shared.presentation.components.screenRelated.SettingsSpecifiedScreenHeader
import com.savenko.track.shared.presentation.navigation.Screen
import com.savenko.track.shared.presentation.screens.screenComponents.additional.CurrenciesSettingsScreenComponent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

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
            SettingsSpecifiedScreenHeader(stringResource(Res.string.currencies), hasBackButton = Platform.type == PlatformTarget.Android) {
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
