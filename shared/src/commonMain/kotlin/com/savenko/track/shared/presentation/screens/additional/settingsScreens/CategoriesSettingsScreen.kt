package com.savenko.track.shared.presentation.screens.additional.settingsScreens

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.savenko.track.shared.data.viewmodels.settingsScreen.category.CategoriesSettingsScreenViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.category.NewCategoryViewModel
import com.savenko.track.shared.presentation.components.dialogs.newCategoryDialog.NewCategoryDialog
import com.savenko.track.shared.presentation.components.screenRelated.SettingsSpecifiedScreenHeader
import com.savenko.track.shared.presentation.navigation.Screen
import com.savenko.track.shared.presentation.screens.screenComponents.additional.CategoriesSettingsScreenComponent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

/**
 * Categories settings screen
 *
 * Part of Track settings screen
 */
@Composable
fun CategoriesSettingsScreen(navController: NavHostController) {
    val viewModel = koinViewModel<CategoriesSettingsScreenViewModel>()
    val screenState = viewModel.screenState.collectAsState()
    val newCategoryViewModel = koinViewModel<NewCategoryViewModel>()
    val newCategoryDialogState = newCategoryViewModel.newCategoryDialogState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader(stringResource(Res.string.categories)) {
                navController.navigate(Screen.MainScreen.route)
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = false,
                onClick = { newCategoryViewModel.setDialogVisibility(true) },
                icon = { Icon(Icons.Filled.Add, stringResource(Res.string.cd_add_new_category)) },
                text = { Text(text = stringResource(Res.string.cd_add_new_category)) },
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            )
        }
    ) { paddingValues ->
        if (newCategoryDialogState.value.isDialogVisible) {
            NewCategoryDialog(
                onDismissRequest = { newCategoryViewModel.setDialogVisibility(false) },
                error = newCategoryDialogState.value.dialogError
            ) { categoryName, categoryType, rawCategoryColor ->
                coroutineScope.launch {
                    newCategoryViewModel.addNewFinancialCategory(
                        categoryName = categoryName,
                        categoryType = categoryType,
                        rawCategoryColor = rawCategoryColor
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            CategoriesSettingsScreenComponent(screenState = screenState.value){
                viewModel.onAction(it)
            }
        }
    }
}
