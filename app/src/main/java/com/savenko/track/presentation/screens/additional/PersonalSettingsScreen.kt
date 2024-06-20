package com.savenko.track.presentation.screens.additional

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.savenko.track.R
import com.savenko.track.presentation.components.screenComponents.additional.PersonalSettingsScreenComponent
import com.savenko.track.presentation.components.settingsScreen.components.SettingsSpecifiedScreenHeader
import com.savenko.track.presentation.navigation.Screen

@Composable
fun PersonalSettingsScreen(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader(stringResource(id = R.string.personal)) {
                navController.navigate(Screen.MainScreen.route)
            }
        }
    )
    {
        Column(modifier = Modifier.padding(it))
        {
            PersonalSettingsScreenComponent()
        }
    }
}