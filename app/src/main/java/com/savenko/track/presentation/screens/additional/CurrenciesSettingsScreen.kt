package com.savenko.track.presentation.screens.additional

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.savenko.track.R
import com.savenko.track.presentation.components.screenComponents.additional.CurrenciesSettingsScreenComponent
import com.savenko.track.presentation.components.settingsScreen.components.SettingsSpecifiedScreenHeader
import com.savenko.track.presentation.navigation.Screen

@Composable
fun CurrenciesSettingsScreen(navController: NavHostController) {
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader(stringResource(id = R.string.currencies)) {
                navController.navigate(Screen.MainScreen.route)
            }
        }
    ) {
        CurrenciesSettingsScreenComponent(paddingValues = it)
    }
}