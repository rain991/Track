package com.savenko.track.shared.presentation.themes

import androidx.compose.runtime.Composable
import com.savenko.track.shared.presentation.themes.blueTheme.BlueTheme
import com.savenko.track.shared.presentation.themes.dynamicColorTheme.TrackerTheme
import com.savenko.track.shared.presentation.themes.pinkTheme.PinkTheme
import com.savenko.track.shared.presentation.themes.purpleGreyTheme.PurpleGreyTheme
import com.savenko.track.shared.presentation.themes.redTheme.RedTheme
import com.savenko.track.shared.presentation.themes.yellowTheme.YellowTheme

/**
 * Theme manager is wrapper of Track theme that handles theme needed to be used by user preferences.
 *
 * User can change preferable colorscheme or enable Dynamic Colors when platform supports it.
 */
@Composable
fun ThemeManager(
    isUsingDynamicColors: Boolean,
    preferableTheme: Themes,
    content: @Composable () -> Unit
) {
    if (isUsingDynamicColors && PlatformTheme.supportsDynamicColors()) {
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
