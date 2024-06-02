package com.example.track.presentation.themes

sealed class Themes(val name : String){
    object BlueTheme : Themes("Blue")
    object PinkTheme : Themes("Pink")
    object PurpleTheme : Themes("Purple")
    object RedTheme : Themes("Red")
    object YellowTheme : Themes("Yellow")
}