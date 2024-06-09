package com.example.track.presentation.screens.additional

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.example.track.R
import com.example.track.data.viewmodels.settingsScreen.CategoriesSettingsScreenViewModel
import com.example.track.data.viewmodels.settingsScreen.NewCategoryViewModel
import com.example.track.domain.models.abstractLayer.CategoriesTypes
import com.example.track.presentation.components.screenComponents.additional.CategoriesSettingsScreenComponent
import com.example.track.presentation.components.settingsScreen.components.NewCategoryDialog
import com.example.track.presentation.components.settingsScreen.components.SettingsSpecifiedScreenHeader
import com.example.track.presentation.navigation.Screen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesSettingsScreen(navController: NavHostController) {
    val viewModel = koinViewModel<CategoriesSettingsScreenViewModel>()
    val listOfIncomeCategories = viewModel.listOfIncomesCategories
    val listOfExpensesCategories = viewModel.listOfExpensesCategories
    val newCategoryViewModel = koinViewModel<NewCategoryViewModel>()
    var isAddingNewCategoryDialogVisible by remember { mutableStateOf(false) }
    var categoryAlreadyExistDialogError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsSpecifiedScreenHeader("Categories") {
                navController.navigate(Screen.MainScreen.route)
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = false,
                onClick = { isAddingNewCategoryDialogVisible = true },
                icon = { Icon(Icons.Filled.Add, stringResource(R.string.cd_add_new_category)) },
                text = { Text(text = stringResource(R.string.cd_add_new_category)) },
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            )
        }
    ) { paddingValues ->
        if (isAddingNewCategoryDialogVisible) {
            NewCategoryDialog(
                onDismissRequest = { isAddingNewCategoryDialogVisible = false },
                categoryAlreadyExistError = categoryAlreadyExistDialogError
            ) { categoryName, categoryType, rawCategoryColor ->
                val processedCategoryColor = rawCategoryColor.substring(4)
                if ((categoryType is CategoriesTypes.ExpenseCategory && !listOfExpensesCategories.map { it.note }
                        .contains(categoryName)) || (categoryType is CategoriesTypes.IncomeCategory && !listOfIncomeCategories.map { it.note }
                        .contains(categoryName))) {
                    categoryAlreadyExistDialogError = false
                    coroutineScope.launch {
                        newCategoryViewModel.addNewFinancialCategory(
                            name = categoryName,
                            categoryType = categoryType,
                            processedColor = processedCategoryColor
                        )
                    }
                    isAddingNewCategoryDialogVisible = false
                } else {
                    categoryAlreadyExistDialogError = true
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