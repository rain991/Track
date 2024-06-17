package com.example.track.presentation.components.screenComponents.additional

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.track.data.viewmodels.settingsScreen.CategoriesSettingsScreenViewModel
import com.example.track.domain.models.abstractLayer.CategoryEntity
import com.example.track.presentation.components.common.parser.parseColor

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
        Text(text = "Create, delete and find your categories", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, focusedElevation = 8.dp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Expense categories", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                }
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOfExpensesCategories.forEach { currentExpenseCategory ->
                        CategorySettingsChip(category = currentExpenseCategory) {
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
                                        text = "delete",
                                        modifier = Modifier.pointerInput(key1 = true) { viewModel.deleteCategory(currentExpenseCategory) })
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
        Card(
           elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, focusedElevation = 8.dp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Income categories", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                }
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOfIncomeCategories.forEach { currentIncomeCategory ->
                        CategorySettingsChip(category = currentIncomeCategory) {
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
                                        text = "delete",
                                        modifier = Modifier.pointerInput(key1 = true) { viewModel.deleteCategory(currentIncomeCategory) })
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


@Composable
fun CategorySettingsChip(
    category: CategoryEntity,
    onSelect: (CategoryEntity) -> Unit
) {
    Button(
        modifier = Modifier
            .wrapContentHeight()
            .scale(0.9f),
        onClick = { onSelect(category) },
        colors = ButtonColors(
            containerColor = parseColor(hexColor = category.colorId),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = parseColor(hexColor = category.colorId),
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = category.note,
                style = if (category.note.length < 12) {
                    MaterialTheme.typography.bodyMedium
                } else {
                    MaterialTheme.typography.bodySmall
                },
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}