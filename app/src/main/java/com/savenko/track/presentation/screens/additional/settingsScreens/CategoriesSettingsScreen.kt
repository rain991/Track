package com.savenko.track.presentation.screens.additional.settingsScreens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.savenko.track.R
import com.savenko.track.data.viewmodels.settingsScreen.category.CategoriesSettingsScreenViewModel
import com.savenko.track.data.viewmodels.settingsScreen.category.NewCategoryViewModel
import com.savenko.track.presentation.components.dialogs.newCategoryDialog.NewCategoryDialog
import com.savenko.track.presentation.components.screenRelated.SettingsSpecifiedScreenHeader
import com.savenko.track.presentation.navigation.Screen
import com.savenko.track.presentation.screens.screenComponents.additional.CategoriesSettingsScreenComponent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesSettingsScreen(navController: NavHostController) {
    val viewModel = koinViewModel<CategoriesSettingsScreenViewModel>()
    val newCategoryViewModel = koinViewModel<NewCategoryViewModel>()
    val newCategoryDialogState = newCategoryViewModel.newCategoryDialogState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader(stringResource(id = R.string.categories)) {
                navController.navigate(Screen.MainScreen.route)
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = false,
                onClick = { newCategoryViewModel.setDialogVisibility(true) },
                icon = { Icon(Icons.Filled.Add, stringResource(R.string.cd_add_new_category)) },
                text = { Text(text = stringResource(R.string.cd_add_new_category)) },
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            )
        }
    ) { paddingValues ->
        if (newCategoryDialogState.value.isDialogVisible) {
            NewCategoryDialog(
                onDismissRequest = { newCategoryViewModel.setDialogVisibility(false) },
                error = newCategoryDialogState.value.dialogErrors
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
            CategoriesSettingsScreenComponent(viewModel)
        }
    }
}