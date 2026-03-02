package com.savenko.track.shared.presentation.screens.additional.settingsScreens

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.savenko.track.shared.Platform
import com.savenko.track.shared.PlatformTarget
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.AddToSavingIdeaDialogViewModel
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.NewIdeaDialogViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.ideas.IdeasSettingsScreenViewModel
import com.savenko.track.shared.presentation.components.dialogs.newIdeaDialog.NewIdeaDialog
import com.savenko.track.shared.presentation.components.screenRelated.SettingsSpecifiedScreenHeader
import com.savenko.track.shared.presentation.navigation.Screen
import com.savenko.track.shared.presentation.screens.screenComponents.additional.IdeasSettingsScreenComponent
import org.koin.compose.viewmodel.koinViewModel

/**
 * Part of Track [settings screen](com.savenko.track.presentation.screens.core.SettingsTrackScreenKt.SettingsTrackScreen)
 *
 * Shows user created Ideas.
 *
 * Supports few filters.
 */
@Composable
fun IdeasSettingsScreen(navController: NavHostController) {
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    val addToSavingIdeaDialogViewModel = koinViewModel<AddToSavingIdeaDialogViewModel>()
    val ideasSettingsScreenViewModel = koinViewModel<IdeasSettingsScreenViewModel>()
    val isNewIdeaDialogVisible = newIdeaDialogViewModel.isNewIdeaDialogVisible.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader(stringResource(Res.string.ideas), hasBackButton = Platform.type == PlatformTarget.Android) {
                navController.navigate(Screen.MainScreen.route)
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = false,
                onClick = { newIdeaDialogViewModel.setIsNewIdeaDialogVisible(true) },
                icon = { Icon(Icons.Filled.Add, stringResource(Res.string.add_new_idea)) },
                text = { Text(text = stringResource(Res.string.add_new_idea)) },
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            )
        }
    ) {
        if (isNewIdeaDialogVisible.value) {
            NewIdeaDialog(newIdeaDialogViewModel)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            IdeasSettingsScreenComponent(
                addToSavingIdeaDialogViewModel = addToSavingIdeaDialogViewModel,
                ideasSettingsScreenViewModel = ideasSettingsScreenViewModel
            )
        }
    }
}
