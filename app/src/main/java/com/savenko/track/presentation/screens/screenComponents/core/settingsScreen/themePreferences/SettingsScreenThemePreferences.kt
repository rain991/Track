package com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.themePreferences

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.viewmodels.settingsScreen.themePreferences.ThemePreferenceSettingsViewModel
import com.savenko.track.presentation.themes.Themes
import com.savenko.track.presentation.themes.blueTheme.newBlueTheme_DarkColors
import com.savenko.track.presentation.themes.blueTheme.newBlueTheme_LightColors
import com.savenko.track.presentation.themes.getThemeByName
import com.savenko.track.presentation.themes.pinkTheme.pink_dark_theme_onPrimary
import com.savenko.track.presentation.themes.pinkTheme.pink_dark_theme_primary
import com.savenko.track.presentation.themes.pinkTheme.pink_light_theme_onPrimary
import com.savenko.track.presentation.themes.pinkTheme.pink_light_theme_primary
import com.savenko.track.presentation.themes.purpleGreyTheme.purpleGreyNew_DarkColorScheme
import com.savenko.track.presentation.themes.purpleGreyTheme.purpleGreyNew_LightColorScheme
import com.savenko.track.presentation.themes.redTheme.red_dark_theme_onPrimary
import com.savenko.track.presentation.themes.redTheme.red_dark_theme_primary
import com.savenko.track.presentation.themes.redTheme.red_light_theme_onPrimary
import com.savenko.track.presentation.themes.redTheme.red_light_theme_primary
import com.savenko.track.presentation.themes.yellowTheme.yellow_dark_theme_onPrimary
import com.savenko.track.presentation.themes.yellowTheme.yellow_dark_theme_primary
import com.savenko.track.presentation.themes.yellowTheme.yellow_light_theme_onPrimary
import com.savenko.track.presentation.themes.yellowTheme.yellow_light_theme_primary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenThemePreferences(modifier: Modifier, dataStoreManager: DataStoreManager) {
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
                modifier = Modifier.padding(start = 4.dp)
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            val darkMode = isSystemInDarkTheme()
                            CircleWithBorder(
                                circleColor = if (darkMode) {
                                    purpleGreyNew_DarkColorScheme.primary
                                } else {
                                    purpleGreyNew_LightColorScheme.primary
                                },
                                isBorderEnabled = getThemeByName(preferableTheme.value) is Themes.PurpleGreyTheme,
                                borderColor = if (darkMode) {
                                    purpleGreyNew_DarkColorScheme.onPrimary
                                } else {
                                    purpleGreyNew_LightColorScheme.onPrimary
                                },
                                circleRadius = 32,
                                onClick = {
                                    coroutineScope.launch {
                                        themePreferenceSettingsViewModel.setPreferableTheme(Themes.PurpleGreyTheme)
                                    }
                                }
                            )
                            CircleWithBorder(
                                circleColor = if (darkMode) {
                                    yellow_light_theme_primary
                                } else {
                                    yellow_dark_theme_primary
                                },
                                isBorderEnabled = getThemeByName(preferableTheme.value) is Themes.YellowTheme,
                                borderColor = if (darkMode) {
                                    yellow_dark_theme_onPrimary
                                } else {
                                    yellow_light_theme_onPrimary
                                },
                                circleRadius = 32,
                                onClick = {
                                    coroutineScope.launch {
                                        themePreferenceSettingsViewModel.setPreferableTheme(Themes.YellowTheme)
                                    }
                                }
                            )
                            CircleWithBorder(
                                circleColor = if (darkMode) {
                                    pink_light_theme_primary
                                } else {
                                    pink_dark_theme_primary

                                },
                                isBorderEnabled = getThemeByName(preferableTheme.value) is Themes.PinkTheme,
                                borderColor = if (darkMode) {
                                    pink_dark_theme_onPrimary
                                } else {
                                    pink_light_theme_onPrimary
                                },
                                circleRadius = 32,
                                onClick = {
                                    coroutineScope.launch {
                                        themePreferenceSettingsViewModel.setPreferableTheme(Themes.PinkTheme)
                                    }
                                }
                            )
                            CircleWithBorder(
                                circleColor = if (darkMode) {
                                    red_light_theme_primary
                                } else {
                                    red_dark_theme_primary
                                },
                                isBorderEnabled = getThemeByName(preferableTheme.value) is Themes.RedTheme,
                                borderColor = if (darkMode) {
                                    red_dark_theme_onPrimary
                                } else {
                                    red_light_theme_onPrimary
                                },
                                circleRadius = 32,
                                onClick = {
                                    coroutineScope.launch {
                                        themePreferenceSettingsViewModel.setPreferableTheme(Themes.RedTheme)
                                    }
                                }
                            )
                            CircleWithBorder(
                                circleColor = if (darkMode) {
                                    newBlueTheme_LightColors.primary
                                } else {
                                    newBlueTheme_DarkColors.primary
                                },
                                isBorderEnabled = getThemeByName(preferableTheme.value) is Themes.BlueTheme,
                                borderColor = if (darkMode) {
                                    newBlueTheme_LightColors.onPrimary
                                } else {
                                    newBlueTheme_DarkColors.onPrimary
                                },
                                circleRadius = 32,
                                onClick = {
                                    coroutineScope.launch {
                                        themePreferenceSettingsViewModel.setPreferableTheme(Themes.BlueTheme)
                                    }
                                }
                            )
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