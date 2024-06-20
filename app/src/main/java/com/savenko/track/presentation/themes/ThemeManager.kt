package com.savenko.track.presentation.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.savenko.track.presentation.themes.BlueTheme.BlueTheme
import com.savenko.track.presentation.themes.PinkTheme.PinkTheme
import com.savenko.track.presentation.themes.PurpleTheme.PurpleTheme
import com.savenko.track.presentation.themes.RedTheme.RedTheme
import com.savenko.track.presentation.themes.YellowTheme.YellowTheme

@Composable
fun ThemeManager(isUsingDynamicColors: Boolean, preferableTheme: Themes, content: @Composable () -> Unit) {
    if (isUsingDynamicColors) {
        MaterialTheme {
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