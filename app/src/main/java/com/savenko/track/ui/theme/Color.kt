package com.savenko.track.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//To be deleted when color changed in Theme.kt
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
//To be deleted when color changed in Theme.kt



// Main Theme
val md_theme_light_primary = Color(0xFFA53A2D)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFFDAD4)
val md_theme_light_onPrimaryContainer = Color(0xFF410000)
val md_theme_light_secondary = Color(0xFF775651)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFFFDAD4)
val md_theme_light_onSecondaryContainer = Color(0xFF2C1512)
val md_theme_light_tertiary = Color(0xFFA53A2D)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFFDAD4)
val md_theme_light_onTertiaryContainer = Color(0xFF410000)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF201A19)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF201A19)
val md_theme_light_surfaceVariant = Color(0xFFF5DDDA)
val md_theme_light_onSurfaceVariant = Color(0xFF534341)
val md_theme_light_outline = Color(0xFF857370)
val md_theme_light_inverseOnSurface = Color(0xFFFBEEEC)
val md_theme_light_inverseSurface = Color(0xFF362F2E)
val md_theme_light_inversePrimary = Color(0xFFFFB4A8)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFFA53A2D)
val md_theme_light_outlineVariant = Color(0xFFD8C2BE)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFFFB4A8)
val md_theme_dark_onPrimary = Color(0xFF650A05)
val md_theme_dark_primaryContainer = Color(0xFF852318)
val md_theme_dark_onPrimaryContainer = Color(0xFFFFDAD4)
val md_theme_dark_secondary = Color(0xFFE7BDB6)
val md_theme_dark_onSecondary = Color(0xFF442925)
val md_theme_dark_secondaryContainer = Color(0xFF5D3F3B)
val md_theme_dark_onSecondaryContainer = Color(0xFFFFDAD4)
val md_theme_dark_tertiary = Color(0xFFFFB4A8)
val md_theme_dark_onTertiary = Color(0xFF650A05)
val md_theme_dark_tertiaryContainer = Color(0xFF852318)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFDAD4)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF201A19)
val md_theme_dark_onBackground = Color(0xFFEDE0DD)
val md_theme_dark_surface = Color(0xFF201A19)
val md_theme_dark_onSurface = Color(0xFFEDE0DD)
val md_theme_dark_surfaceVariant = Color(0xFF534341)
val md_theme_dark_onSurfaceVariant = Color(0xFFD8C2BE)
val md_theme_dark_outline = Color(0xFFA08C89)
val md_theme_dark_inverseOnSurface = Color(0xFF201A19)
val md_theme_dark_inverseSurface = Color(0xFFEDE0DD)
val md_theme_dark_inversePrimary = Color(0xFFA53A2D)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFFFB4A8)
val md_theme_dark_outlineVariant = Color(0xFF534341)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFF570000)
val light_ = Color(0xFFA53A2D)
val light_on = Color(0xFFFFFFFF)
val light_Container = Color(0xFFFFDAD4)
val light_onContainer = Color(0xFF410000)
val dark_ = Color(0xFFFFB4A8)
val dark_on = Color(0xFF650A05)
val dark_Container = Color(0xFF852318)
val dark_onContainer = Color(0xFFFFDAD4)

val ColorScheme.focusedTextFieldText
@Composable
get() = if(isSystemInDarkTheme()) Color.White else Color.Black

val ColorScheme.unfocusedTextFieldText
    @Composable
    get() = if(isSystemInDarkTheme()) light_Container else md_theme_dark_inversePrimary

