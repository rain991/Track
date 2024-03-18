package com.example.track.presentation.components.settingsScreen.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.track.R
import com.example.track.data.DataStoreManager
import com.example.track.data.constants.NAME_DEFAULT
import com.example.track.data.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.constants.USE_SYSTEM_THEME_DEFAULT
import com.example.track.data.viewmodels.settingsScreen.SettingsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsHeader(modifier: Modifier, dataStoreManager: DataStoreManager) {
    val currentUserName = dataStoreManager.nameFlow.collectAsState(initial = NAME_DEFAULT)
    Text(
        text = stringResource(R.string.greetings_settings_screen, currentUserName.value),
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 40.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun ThemePreferences(modifier: Modifier, dataStoreManager: DataStoreManager) {
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val showPageNameChecked = settingsViewModel.showPagesNameFlow.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val useDeviceTheme = settingsViewModel.useSystemTheme.collectAsState(initial = USE_SYSTEM_THEME_DEFAULT)
    Box(modifier = modifier) {
        Column(Modifier.wrapContentSize()) {
            Text(
                text = stringResource(R.string.theme_settings_screen),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(R.string.use_device_theme_settings_screen),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                        modifier = Modifier.padding(start = 4.dp), textAlign = TextAlign.Start
                    )
                    SettingsSwitch(checked = useDeviceTheme.value, onCheckedChange = {
                        coroutineScope.launch { settingsViewModel.setUseSystemTheme(it) }
                    })
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(visible = !useDeviceTheme.value, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(R.string.preferable_theme_settings_screen),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(start = 4.dp), textAlign = TextAlign.Start
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.show_page_name_setttings_screen),
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier.padding(start = 4.dp)
                )
                SettingsSwitch(checked = showPageNameChecked.value, onCheckedChange = {
                    coroutineScope.launch {
                        settingsViewModel.setShowPagesNameFlow(it)
                    }
                })
            }
        }
    }
}

@Composable
private fun SettingsSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(checked = checked, onCheckedChange = onCheckedChange)
}

@Composable
private fun OptionalSettingsSwitch(
    enabledFlow: Flow<Boolean>,
    checkedFlow: Flow<Boolean>,
    initial: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val enabled = enabledFlow.collectAsState(initial = false)
    val checked = checkedFlow.collectAsState(initial = initial)
    Switch(checked = checked.value, onCheckedChange = onCheckedChange, enabled = enabled.value)
}

