package com.example.expensetracker.presentation.home.settingsScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun SettingsHeader(textModifier: Modifier, dataStoreManager: DataStoreManager) {
    val currentUserName = dataStoreManager.nameFlow.collectAsState(initial = NAME_DEFAULT)
    Text(
        text = stringResource(R.string.greetings_settings_screen, currentUserName.value),
        modifier = textModifier,
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 40.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun UserPreferenceCard(cardModifier: Modifier, dataStoreManager: DataStoreManager) {
    Card(modifier = cardModifier) {
        Text(
            text = stringResource(R.string.your_preferences_settings_screen),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            stringResource(R.string.preferable_currency_settings_screen),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun ThemePreferences(cardModifier: Modifier, dataStoreManager: DataStoreManager) {
    Card(modifier = cardModifier) {
        Text(
            text = stringResource(R.string.theme_settings_screen),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            stringResource(R.string.use_device_theme_settings_screen),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.show_page_name_setttings_screen),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
@Composable
private fun settingsSwitch(){

}