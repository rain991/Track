package com.savenko.track.presentation.themes

import android.os.Build
import androidx.compose.runtime.Composable
import com.savenko.track.presentation.themes.blueTheme.BlueTheme
import com.savenko.track.presentation.themes.dynamicColorTheme.TrackerTheme
import com.savenko.track.presentation.themes.pinkTheme.PinkTheme
import com.savenko.track.presentation.themes.purpleGreyTheme.PurpleGreyTheme
import com.savenko.track.presentation.themes.redTheme.RedTheme
import com.savenko.track.presentation.themes.yellowTheme.YellowTheme


@Composable
fun ThemeManager(
    isUsingDynamicColors: Boolean,
    preferableTheme: Themes,
    content: @Composable () -> Unit
) {
    if (isUsingDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
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

            Themes.PurpleGreyTheme -> {
                PurpleGreyTheme {
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