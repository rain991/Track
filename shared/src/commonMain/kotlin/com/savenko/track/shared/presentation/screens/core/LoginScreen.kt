package com.savenko.track.shared.presentation.screens.core

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.savenko.track.shared.data.viewmodels.login.LoginScreenViewModel
import com.savenko.track.shared.presentation.screens.loginScreen.LoginScreenContent
import com.savenko.track.shared.presentation.screens.loginScreen.LoginScreenHeader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * LoginScreen contains [LoginScreenHeader] and [LoginScreenContent]
 */
@Composable
fun LoginScreen(navController: NavController) {
    val loginScreenViewModel = koinViewModel<LoginScreenViewModel>()
    val defaultUserName = stringResource(Res.string.user)
    LaunchedEffect(key1 = Unit) {
        loginScreenViewModel.setFirstNameStateFlow(defaultUserName)
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginScreenHeader(modifier = Modifier.padding(top = 64.dp))
            Spacer(modifier = Modifier.weight(1f))
            LoginScreenContent(loginScreenViewModel, navController)
            Spacer(modifier = Modifier.weight(1.2f))
        }
    }
}
