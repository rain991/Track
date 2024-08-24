package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.themePreferences

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.R
import com.savenko.track.data.other.constants.PREFERABLE_THEME_DEFAULT
import com.savenko.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.savenko.track.data.other.constants.USE_SYSTEM_THEME_DEFAULT
import com.savenko.track.data.viewmodels.settingsScreen.themePreferences.ThemePreferenceSettingsViewModel
import com.savenko.track.presentation.components.customComponents.ThemeSelectorRow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenThemePreferences(modifier: Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val themePreferenceSettingsViewModel = koinViewModel<ThemePreferenceSettingsViewModel>()
    val showPageNameChecked =
        themePreferenceSettingsViewModel.showPagesNameFlow.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val useDeviceTheme =
        themePreferenceSettingsViewModel.useSystemTheme.collectAsState(initial = USE_SYSTEM_THEME_DEFAULT)
    val preferableTheme =
        themePreferenceSettingsViewModel.preferableTheme.collectAsState(initial = PREFERABLE_THEME_DEFAULT.name)
    Box(modifier = modifier) {
        Column(Modifier.wrapContentHeight()) {
            Text(
                text = stringResource(R.string.theme_settings_screen),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.use_device_theme_settings_screen),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier.padding(start = 4.dp), textAlign = TextAlign.Start
                        )
                        Switch(checked = useDeviceTheme.value, onCheckedChange = {
                            coroutineScope.launch { themePreferenceSettingsViewModel.setUseSystemTheme(it) }
                        })
                    }
                }
                if (!useDeviceTheme.value || Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.preferable_theme_settings_screen),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier.padding(start = 4.dp), textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ThemeSelectorRow(preferableTheme = preferableTheme) { theme ->
                            coroutineScope.launch {
                                themePreferenceSettingsViewModel.setPreferableTheme(theme)
                            }
                        }
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
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier.padding(start = 4.dp)
                )
                Switch(checked = showPageNameChecked.value, onCheckedChange = {
                    coroutineScope.launch {
                        themePreferenceSettingsViewModel.setShowPagesNameFlow(it)
                    }
                })
            }
        }
    }
}