package com.savenko.track.presentation.navigation

sealed class Screen(val route : String) {
    data object MainScreen : Screen("main_screen")
    data object LoginScreen : Screen("login_screen")
    data object PersonalSettingsScreen : Screen("personal_settings_screen")
    data object IdeasListSettingsScreen : Screen("ideas_list_settings_screen")
    data object CurrenciesSettingsScreen : Screen("currencies_settings_screen")
    data object CategoriesSettingsScreen : Screen("categories_settings_screen")
}