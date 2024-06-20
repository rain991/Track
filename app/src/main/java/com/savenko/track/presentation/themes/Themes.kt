package com.savenko.track.presentation.themes

import com.savenko.track.data.other.constants.PREFERABLE_THEME_DEFAULT

sealed class Themes(val name : String){
    object BlueTheme : Themes("Blue")
    object PinkTheme : Themes("Pink")
    object PurpleTheme : Themes("Purple")
    object RedTheme : Themes("Red")
    object YellowTheme : Themes("Yellow")
}


fun getThemeByName(themeName: String): Themes {
    return when (themeName) {
        "Blue" -> Themes.BlueTheme
        "Pink" -> Themes.PinkTheme
        "Purple" -> Themes.PurpleTheme
        "Red" -> Themes.RedTheme
        "Yellow" -> Themes.YellowTheme
        else -> PREFERABLE_THEME_DEFAULT
    }
}