package com.savenko.track.presentation.components.customComponents

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.themePreferences.CircleWithBorder
import com.savenko.track.presentation.themes.Themes
import com.savenko.track.presentation.themes.blueTheme.blueThemeDarkColorScheme
import com.savenko.track.presentation.themes.blueTheme.blueThemeLightColorScheme
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
import com.savenko.track.presentation.themes.yellowTheme.yellow_theme_light_primaryContainer

@Composable
fun ThemeSelectorRow(
    preferableTheme: State<String>,
    onChangePreferableTheme: (Themes) -> Unit
) {
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
                onChangePreferableTheme(Themes.PurpleGreyTheme)
            }
        )
        CircleWithBorder(
            circleColor = if (darkMode) {
                yellow_dark_theme_primary
            } else {
                yellow_theme_light_primaryContainer
            },
            isBorderEnabled = getThemeByName(preferableTheme.value) is Themes.YellowTheme,
            borderColor = if (darkMode) {
                yellow_dark_theme_onPrimary
            } else {
                yellow_light_theme_onPrimary
            },
            circleRadius = 32,
            onClick = {
                onChangePreferableTheme(Themes.YellowTheme)
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
                onChangePreferableTheme(Themes.PinkTheme)
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
                onChangePreferableTheme(Themes.RedTheme)
            }
        )
        CircleWithBorder(
            circleColor = if (darkMode) {
                blueThemeLightColorScheme.primary
            } else {
                blueThemeDarkColorScheme.primary
            },
            isBorderEnabled = getThemeByName(preferableTheme.value) is Themes.BlueTheme,
            borderColor = if (darkMode) {
                blueThemeLightColorScheme.onPrimary
            } else {
                blueThemeDarkColorScheme.onPrimary
            },
            circleRadius = 32,
            onClick = {
                onChangePreferableTheme(Themes.BlueTheme)
            }
        )
    }
}