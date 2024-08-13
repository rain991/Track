package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.categoriesPreferences

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS
import com.savenko.track.data.other.constants.LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.presentation.components.customComponents.CategorySettingsChip
import com.savenko.track.presentation.other.colors.parseColor
import com.savenko.track.presentation.screens.screenComponents.additional.CategoriesFilterInputField

@Composable
fun CategoriesSettingsListViewContent(
    listOfExpenseCategories: List<ExpenseCategory>,
    listOfIncomeCategories: List<IncomeCategory>,
    nameFilter: String,
    setFilteringText: (String) -> Unit,
    onDeleteCategory: (CategoryEntity) -> Unit
) {
    val listState = rememberLazyListState()
    val listOfAllCategories = listOfExpenseCategories + listOfIncomeCategories
    val firstVisibleIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (listOfAllCategories.isNotEmpty() && listOfAllCategories[firstVisibleIndex.value] is IncomeCategory) {
                Text(
                    text = stringResource(id = R.string.income_categories),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                )
            } else {
                Text(
                    text = stringResource(R.string.expense_categories_categories_settings_screen),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            CategoriesFilterInputField(nameFilter = nameFilter) {
                setFilteringText(it)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            state = listState, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            itemsIndexed(
                items = listOfAllCategories
            ) { index: Int, category: CategoryEntity ->
                CategoryRowCategoriesSettingsScreen(categoryEntity = category) {
                    onDeleteCategory(it)
                }
            }
        }
    }

}

@Composable
private fun CategoryRowCategoriesSettingsScreen(
    categoryEntity: CategoryEntity,
    onDeleteCategory: (CategoryEntity) -> Unit
) {
    val categoryColor = parseColor(categoryEntity.colorId)
    Card(
        modifier = Modifier.padding(8.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            focusedElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategorySettingsChip(
                category = categoryEntity,
                isSelected = false,
                borderColor = MaterialTheme.colorScheme.onPrimary
            ) {

            }
            if (categoryEntity is ExpenseCategory && !LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS.contains(categoryEntity.categoryId)) {
                Box(modifier = Modifier.padding(end = 8.dp)) {
                    DeleteIcon(categoryColor = categoryColor) {
                        onDeleteCategory(categoryEntity)
                    }
                }
            } else if (categoryEntity is ExpenseCategory) {
                DefaultCard()
            }

            if (categoryEntity is IncomeCategory && !LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS.contains(categoryEntity.categoryId)) {
                Box(modifier = Modifier.padding(end = 8.dp)) {
                    DeleteIcon(categoryColor = categoryColor) {
                        onDeleteCategory(categoryEntity)
                    }
                }
            } else if (categoryEntity is IncomeCategory) {
                DefaultCard()
            }
        }
    }
}

@Composable
private fun DeleteIcon(categoryColor: Color, onDeleteCategory: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(CircleShape)
            .background(categoryColor)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_financial_item_CD),
            modifier = Modifier
                .scale(0.7f)
                .clickable {
                    onDeleteCategory()
                },
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun DefaultCard() {
    Card(
        modifier = Modifier.scale(0.8f), elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            focusedElevation = 8.dp
        ), colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(text = "Default", modifier = Modifier.padding(8.dp))
    }
}