package com.example.track.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.presentation.components.login.LoginScreen
import com.example.track.presentation.other.TrackScreenManager
import com.example.track.presentation.screens.Additional.CategoriesSettingsScreen
import com.example.track.presentation.screens.Additional.CurrenciesSettingsScreen
import com.example.track.presentation.screens.Additional.IdeasListSettingsScreen
import com.example.track.presentation.screens.Additional.PersonalSettingsScreen

@Composable
fun Navigation(dataStoreManager: DataStoreManager) {
    val navController = rememberNavController()
    val loginCount = dataStoreManager.loginCountFlow.collectAsState(initial = 0)
    LaunchedEffect(key1 = loginCount) {
        if (loginCount.value == 0) {
            navController.navigate(Screen.LoginScreen.route)
        } else {
            navController.navigate(Screen.MainScreen.route)
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            TrackScreenManager(navController)
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
        composable(route = Screen.IdeaListSettingsScreen.route) {
            CategoriesSettingsScreen(navController)
        }
    }
}


