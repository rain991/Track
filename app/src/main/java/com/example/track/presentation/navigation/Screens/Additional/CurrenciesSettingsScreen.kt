package com.example.track.presentation.navigation.Screens.Additional

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import com.example.track.data.DataStoreManager
import com.example.track.presentation.navigation.Screen
import org.koin.compose.koinInject

@Composable
fun CurrenciesSettingsScreen(navController: NavHostController) {
    val settingsData = koinInject<DataStoreManager>()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Button(onClick = { navController.navigate(Screen.MainScreen.route) }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back to settings screen")
            }
        },bottomBar = { }
    ) {
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp))
        {

        }
    }
}