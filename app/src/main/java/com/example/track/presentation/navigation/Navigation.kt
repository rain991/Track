package com.example.track.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.track.data.DataStoreManager
import com.example.track.presentation.login.LoginScreen
import com.example.track.presentation.other.ScreenManager

@Composable
fun Navigation(dataStoreManager: DataStoreManager) {
    val navController = rememberNavController()
    val loginCount = dataStoreManager.loginCountFlow.collectAsState(initial = 0)
    LaunchedEffect(key1 = loginCount) {
        Log.d("MyLog", "Login count: $loginCount")
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
            ScreenManager()
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
    }
}


