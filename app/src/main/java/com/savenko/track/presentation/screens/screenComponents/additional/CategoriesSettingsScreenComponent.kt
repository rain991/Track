package com.savenko.track.presentation.screens.screenComponents.additional

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.viewmodels.settingsScreen.category.CategoriesSettingsScreenViewModel
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.components.customComponents.CategorySettingsChip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesSettingsScreenComponent(viewModel: CategoriesSettingsScreenViewModel) {
    val listOfIncomeCategories = viewModel.listOfIncomesCategories
    val listOfExpensesCategories = viewModel.listOfExpensesCategories
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var currentSelectedCategory by remember {
        mutableStateOf<CategoryEntity?>(null)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.message_categories_settings_screen),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                focusedElevation = 8.dp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.expense_categories_categories_settings_screen),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOfExpensesCategories.forEach { currentExpenseCategory ->
                        CategorySettingsChip(
                            category = currentExpenseCategory,
                            borderColor = MaterialTheme.colorScheme.primary
                        ) {
                            isContextMenuVisible = true
                            currentSelectedCategory = currentExpenseCategory
                        }
                        if (currentSelectedCategory == currentExpenseCategory) {
                            Box {
                                DropdownMenu(
                                    expanded = isContextMenuVisible,
                                    onDismissRequest = { isContextMenuVisible = false },
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.delete),
                                        modifier = Modifier.pointerInput(key1 = true) {
                                            viewModel.deleteCategory(
                                                currentExpenseCategory
                                            )
                                        })
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                focusedElevation = 8.dp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.income_categories),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOfIncomeCategories.forEach { currentIncomeCategory ->
                        CategorySettingsChip(
                            category = currentIncomeCategory,
                            borderColor = MaterialTheme.colorScheme.primary
                        ) {
                            isContextMenuVisible = true
                            currentSelectedCategory = currentIncomeCategory
                        }
                        if (currentSelectedCategory == currentIncomeCategory) {
                            Box {
                                DropdownMenu(
                                    expanded = isContextMenuVisible,
                                    onDismissRequest = { isContextMenuVisible = false },
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.delete),
                                        modifier = Modifier.pointerInput(key1 = true) {
                                            viewModel.deleteCategory(
                                                currentIncomeCategory
                                            )
                                        })
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}


