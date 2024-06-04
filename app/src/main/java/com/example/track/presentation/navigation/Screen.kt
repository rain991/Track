package com.example.track.presentation.navigation

sealed class Screen(val route : String) {
    object MainScreen : Screen("main_screen")
    object LoginScreen : Screen("login_screen")
    object PersonalSettingsScreen : Screen("personal_settings_screen")
    object IdeasListSettingsScreen : Screen("ideas_list_settings_screen")
    object CurrenciesSettingsScreen : Screen("currencies_settings_screen")
    object CategoriesSettingsScreen : Screen("categories_settings_screen")
    object IdeaListSettingsScreen : Screen ("idea_list_settings_screen")
}