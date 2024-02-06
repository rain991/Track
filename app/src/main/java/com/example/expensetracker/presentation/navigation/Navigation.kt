package com.example.expensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.presentation.login.LoginScreen
import com.example.expensetracker.presentation.other.ScreenManager

@Composable
fun Navigation(dataStoreManager: DataStoreManager, firstLogin: Boolean?) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (firstLogin == true) Screen.LoginScreen.route else Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            ScreenManager()
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
    }
}


