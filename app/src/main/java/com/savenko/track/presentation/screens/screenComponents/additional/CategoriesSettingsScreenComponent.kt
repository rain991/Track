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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.viewmodels.settingsScreen.category.CategoriesSettingsScreenViewModel
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.components.customComponents.CategorySettingsChip
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenEvent
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenState
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenViewOptions

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesSettingsScreenComponent(
    screenState: CategoriesSettingsScreenState,
    onAction: (CategoriesSettingsScreenEvent) -> Unit
) {
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
                        val isSelected = mutableStateOf(currentSelectedCategory == currentExpenseCategory)
                        CategorySettingsChip(
                            category = currentExpenseCategory,
                            isSelected = isSelected.value,
                            borderColor = MaterialTheme.colorScheme.onPrimary
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
                        val isSelected = mutableStateOf(currentSelectedCategory == currentIncomeCategory)
                        CategorySettingsChip(
                            category = currentIncomeCategory,
                            isSelected = isSelected.value,
                            borderColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            isContextMenuVisible = true
                            currentSelectedCategory = currentIncomeCategory
                        }
                        if (currentSelectedCategory == currentIncomeCategory) {
                            Box {
                                DropdownMenu(
                                    expanded = isContextMenuVisible,
                                    onDismissRequest = {
                                        currentSelectedCategory = null
                                        isContextMenuVisible = false
                                    },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriesScreenOptionsSelector(
    screenState: CategoriesSettingsScreenState,
    onAction: (CategoriesSettingsScreenEvent) -> Unit
) {
    val viewOptions =
        listOf(CategoriesSettingsScreenViewOptions.CardsView, CategoriesSettingsScreenViewOptions.ListView)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            SingleChoiceSegmentedButtonRow {
                viewOptions.forEachIndexed { index, currentViewOption ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = viewOptions.size
                        ),
                        onClick = {
                            onAction(CategoriesSettingsScreenEvent.SetViewOption(currentViewOption))
                        },
                        selected = screenState.viewOption.nameResId == currentViewOption.nameResId
                    ) {
                        Text(
                            text = stringResource(id = currentViewOption.nameResId),
                            maxLines = 1,
                            style = MaterialTheme.typography.labelMedium,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Show only custom categories")
            Switch(checked = screenState.filterOnlyCustomCategories, onCheckedChange = {
                onAction(CategoriesSettingsScreenEvent.SetFilterOnlyCustomCategories(it))
            })
        }
    }
}