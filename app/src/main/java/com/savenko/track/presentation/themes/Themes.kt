package com.savenko.track.presentation.themes

import com.savenko.track.data.other.constants.PREFERABLE_THEME_DEFAULT

sealed class Themes(val name : String){
    data object PurpleGreyTheme : Themes("PurpleGrey")
    data object BlueTheme : Themes("Blue")
    data object PinkTheme : Themes("Pink")
    data object RedTheme : Themes("Red")
    data object YellowTheme : Themes("Yellow")
}

fun getThemeByName(themeName: String): Themes {
    return when (themeName) {
        "Blue" -> Themes.BlueTheme
        "Pink" -> Themes.PinkTheme
        "PurpleGrey" -> Themes.PurpleGreyTheme
        "Red" -> Themes.RedTheme
        "Yellow" -> Themes.YellowTheme
        else -> PREFERABLE_THEME_DEFAULT
    }
}