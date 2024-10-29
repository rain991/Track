package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.categoriesPreferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.presentation.components.customComponents.CategorySettingsChip


/**
 * Categories settings screen content in list view
 *
 * @param listOfExpenseCategories list of filtered user expense categories
 * @param listOfIncomeCategories list of filtered user incomes categories
 * @param currentSelectedCategory selected category, on such categories could be performed actions, e.g : deleting category
 *
 * @see [CategoriesSettingsScreen](com.savenko.track.presentation.screens.screenComponents.additional.CategoriesSettingsScreenComponentKt)
 */
@Composable
fun CategoriesSettingsCardViewContent(
    listOfExpenseCategories: List<ExpenseCategory>,
    listOfIncomeCategories: List<IncomeCategory>,
    currentSelectedCategory: CategoryEntity?,
    onSelectCategory: (CategoryEntity) -> Unit,
    onDeleteCategory: (CategoryEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CategoriesCard<ExpenseCategory>(
            categoriesList = listOfExpenseCategories,
            currentSelectedCategoryEntity = currentSelectedCategory,
            onSelectCategory = { onSelectCategory(it) }) {
            onDeleteCategory(it)
        }
        CategoriesCard<IncomeCategory>(
            categoriesList = listOfIncomeCategories,
            currentSelectedCategoryEntity = currentSelectedCategory,
            onSelectCategory = { onSelectCategory(it) }) {
            onDeleteCategory(it)
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private inline fun <reified T : CategoryEntity> CategoriesCard(
    categoriesList: List<T>,
    currentSelectedCategoryEntity: CategoryEntity?,
    crossinline onSelectCategory: (CategoryEntity) -> Unit,
    crossinline onDeleteCategory: (CategoryEntity) -> Unit
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            focusedElevation = 8.dp
        ),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp).scale(0.96f)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = when (T::class) {
                        ExpenseCategory::class -> {
                            stringResource(R.string.expense_categories_categories_settings_screen)
                        }

                        IncomeCategory::class -> {
                            stringResource(id = R.string.income_categories)
                        }

                        else -> {
                            stringResource(id = R.string.unknown)
                        }
                    },
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            if (categoriesList.isEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.no_categories_for_specified_filters_categories_settings), textAlign = TextAlign.Center)
                }
            } else {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    categoriesList.forEach { currentFinancialCategory ->
                        val isSelected = mutableStateOf(currentSelectedCategoryEntity == currentFinancialCategory)
                        CategorySettingsChip(
                            category = currentFinancialCategory,
                            isSelected = isSelected.value,
                            borderColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            isContextMenuVisible = true
                            onSelectCategory(it)
                        }
                        if (currentSelectedCategoryEntity == currentFinancialCategory) {
                            Box {
                                DropdownMenu(
                                    expanded = isContextMenuVisible,
                                    onDismissRequest = { isContextMenuVisible = false },
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.delete),
                                        modifier = Modifier.pointerInput(key1 = true) {
                                            onDeleteCategory(currentFinancialCategory)
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