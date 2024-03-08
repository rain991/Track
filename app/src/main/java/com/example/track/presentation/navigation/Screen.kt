package com.example.track.presentation.navigation

sealed class Screen(val route : String) {
    object MainScreen : Screen("main_screen")
    object LoginScreen : Screen("login_screen")
}