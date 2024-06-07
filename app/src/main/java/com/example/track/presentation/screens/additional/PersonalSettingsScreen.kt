package com.example.track.presentation.screens.additional

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.presentation.components.settingsScreen.components.SettingsSpecifiedScreenHeader
import com.example.track.presentation.navigation.Screen
import org.koin.compose.koinInject

@Composable
fun PersonalSettingsScreen(navController: NavHostController) {
    val settingsData = koinInject<DataStoreManager>()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader("Personal"){
                navController.navigate(Screen.MainScreen.route)
            }
        }
    )
    {
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp))
        {

        }
    }
}