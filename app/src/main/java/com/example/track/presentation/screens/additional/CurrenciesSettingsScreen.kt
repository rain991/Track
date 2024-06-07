package com.example.track.presentation.screens.additional

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.track.presentation.components.screenComponents.additional.CurrenciesSettingsScreenComponent
import com.example.track.presentation.components.settingsScreen.components.SettingsSpecifiedScreenHeader
import com.example.track.presentation.navigation.Screen

@Composable
fun CurrenciesSettingsScreen(navController: NavHostController) {
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader("Currencies"){
                navController.navigate(Screen.MainScreen.route)
            }
        }
    ) {
        CurrenciesSettingsScreenComponent(paddingValues = it)
    }
}