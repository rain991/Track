package com.example.expensetracker.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.presentation.login.LoginScreen
import com.example.expensetracker.presentation.other.ScreenManager

@Composable
fun Navigation(dataStoreManager: DataStoreManager) {
    val navController = rememberNavController()

    val loginCount by dataStoreManager.loginCountFlow.collectAsState(initial = 0)
    val startDestination = rememberUpdatedState(
        if (loginCount > 0) {
            Screen.MainScreen.route
        } else {
            Screen.LoginScreen.route
        }
    )
    LaunchedEffect(startDestination.value) {
        navController.navigate(startDestination.value) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
    Log.d("MyLog", loginCount.toString())
    NavHost(navController = navController, startDestination = startDestination.value) {
        composable(route = Screen.MainScreen.route) {
            ScreenManager()
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen()
        }
    }
}

