package com.savenko.track.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.savenko.track.data.viewmodels.common.TrackScreenManagerViewModel
import com.savenko.track.presentation.navigation.Screen.MainScreen
import com.savenko.track.presentation.screens.additional.settingsScreens.CategoriesSettingsScreen
import com.savenko.track.presentation.screens.additional.settingsScreens.CurrenciesSettingsScreen
import com.savenko.track.presentation.screens.additional.settingsScreens.IdeasListSettingsScreen
import com.savenko.track.presentation.screens.additional.settingsScreens.PersonalSettingsScreen
import com.savenko.track.presentation.screens.core.LoginScreen
import com.savenko.track.presentation.screens.core.TrackScreenManager
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(currentLoginCount: Int) {
    val navController = rememberNavController()
    val trackScreenManagerViewModel = koinViewModel<TrackScreenManagerViewModel>()
    NavHost(
        navController = navController,
        startDestination = if (currentLoginCount != 0) {
            MainScreen.route
        } else {
            Screen.LoginScreen.route
        }
    ) {
        composable(route = MainScreen.route) {
            TrackScreenManager(navHostController = navController, viewModel = trackScreenManagerViewModel)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.CurrenciesSettingsScreen.route) {
            CurrenciesSettingsScreen(navController)
        }
        composable(route = Screen.IdeasListSettingsScreen.route) {
            IdeasListSettingsScreen(navController)
        }
        composable(route = Screen.PersonalSettingsScreen.route) {
            PersonalSettingsScreen(navController)
        }
        composable(route = Screen.CategoriesSettingsScreen.route) {
            CategoriesSettingsScreen(navController)
        }
    }
}