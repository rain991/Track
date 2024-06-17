package com.example.track.presentation.screens.additional

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.track.R
import com.example.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel
import com.example.track.presentation.components.mainScreen.dialogs.NewIdeaDialog
import com.example.track.presentation.components.screenComponents.additional.IdeasListSettingsScreenComponent
import com.example.track.presentation.components.settingsScreen.components.SettingsSpecifiedScreenHeader
import com.example.track.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun IdeasListSettingsScreen(navController: NavHostController) {
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    val isNewIdeaDialogVisible = newIdeaDialogViewModel.isNewIdeaDialogVisible.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader("Ideas"){
                navController.navigate(Screen.MainScreen.route)
            }
        },
        floatingActionButton = {
                ExtendedFloatingActionButton(
                    expanded = false,
                    onClick = { newIdeaDialogViewModel.setIsNewIdeaDialogVisible(true) },
                    icon = { Icon(Icons.Filled.Add, stringResource(R.string.add_new_idea)) },
                    text = { Text(text = stringResource(R.string.add_new_idea)) }, modifier = Modifier.padding(end = 16.dp, bottom = 16.dp))
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
            IdeasListSettingsScreenComponent()
        }
    }
}