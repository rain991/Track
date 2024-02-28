package com.example.expensetracker.presentation.home.settingsScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.expensetracker.R
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.constants.NAME_DEFAULT

@Composable
fun Header(textModifier: Modifier, dataStoreManager: DataStoreManager) {
    val currentUserName = dataStoreManager.nameFlow.collectAsState(initial = NAME_DEFAULT)
    Text(
        text = stringResource(R.string.greetings_settings_screen, currentUserName.value),
        modifier = textModifier,
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 40.sp)
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun UserPreference(cardModifier: Modifier, dataStoreManager: DataStoreManager) {
    Card(modifier = cardModifier) {
        Text(text = stringResource(R.string.your_preferences_settings_screen), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Main currency:")
    }
}

@Composable
fun ThemePreferences() {

}