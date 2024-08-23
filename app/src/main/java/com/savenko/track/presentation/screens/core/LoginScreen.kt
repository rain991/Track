package com.savenko.track.presentation.screens.core

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.savenko.track.R
import com.savenko.track.data.viewmodels.login.LoginScreenViewModel
import com.savenko.track.presentation.screens.loginScreen.LoginHeader
import com.savenko.track.presentation.screens.loginScreen.LoginScreenContent
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(navController: NavController) {
    val localContext = LocalContext.current
    val loginScreenViewModel = koinViewModel<LoginScreenViewModel>()
    LaunchedEffect(key1 = Unit) {
        loginScreenViewModel.setFirstNameStateFlow(localContext.getString(R.string.user))
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        val useDarkTheme = isSystemInDarkTheme()
        val view = LocalView.current
        val color = MaterialTheme.colorScheme.primary.toArgb()
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = color
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                    useDarkTheme
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()//LoginScreenHeader()
            Spacer(modifier = Modifier.weight(1f))
            LoginScreenContent(loginScreenViewModel, navController)
            Spacer(modifier = Modifier.weight(1.2f))
        }
    }
}