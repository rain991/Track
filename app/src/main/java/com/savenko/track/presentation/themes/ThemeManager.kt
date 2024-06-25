package com.savenko.track.presentation.themes

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.savenko.track.presentation.themes.BlueTheme.BlueTheme
import com.savenko.track.presentation.themes.PinkTheme.PinkTheme
import com.savenko.track.presentation.themes.PurpleTheme.PurpleTheme
import com.savenko.track.presentation.themes.RedTheme.RedTheme
import com.savenko.track.presentation.themes.YellowTheme.YellowTheme
import com.savenko.track.themes.theme.TrackerTheme


@Composable
fun ThemeManager(
    isUsingDynamicColors: Boolean,
    preferableTheme: Themes,
    content: @Composable () -> Unit
) {
    val localContext = LocalContext.current
    val colorScheme: ColorScheme? = null
    if (isUsingDynamicColors) {
        TrackerTheme {
            content()
        }
    } else {
        when (preferableTheme) {
            is Themes.BlueTheme -> {
                BlueTheme {
                    content()
                }
            }

            Themes.PinkTheme -> {
                PinkTheme {
                    content()
                }
            }

            Themes.PurpleTheme -> {
                PurpleTheme {
                    content()
                }
            }

            Themes.RedTheme -> {
                RedTheme {
                    content()
                }
            }

            Themes.YellowTheme -> {
                YellowTheme {
                    content()
                }
            }
        }
    }
}