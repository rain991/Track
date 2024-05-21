package com.example.track.presentation.screens.additional

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.track.presentation.components.screenComponents.CurrenciesSettingsScreenComponent
import com.example.track.presentation.navigation.Screen

@Composable
fun CurrenciesSettingsScreen(navController: NavHostController) {
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { navController.navigate(Screen.MainScreen.route) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back to settings screen")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Currencies",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.weight(1.36f))
            }
        }
    ) {
        CurrenciesSettingsScreenComponent(paddingValues = it)
    }
}