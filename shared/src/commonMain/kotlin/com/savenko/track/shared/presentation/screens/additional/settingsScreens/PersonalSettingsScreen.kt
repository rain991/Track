package com.savenko.track.shared.presentation.screens.additional.settingsScreens

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.navigation.NavHostController
import com.savenko.track.shared.presentation.screens.screenComponents.additional.PersonalSettingsScreenComponent
import com.savenko.track.shared.presentation.components.screenRelated.SettingsSpecifiedScreenHeader
import com.savenko.track.shared.presentation.navigation.Screen

@Composable
fun PersonalSettingsScreen(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader(stringResource(Res.string.personal)) {
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
