package com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.additionalPreferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.R
import com.savenko.track.data.other.constants.GROUPING_CATEGORY_ID_DEFAULT
import com.savenko.track.data.viewmodels.settingsScreen.additionalPreferences.AdditionalPreferencesSettingsViewModel
import com.savenko.track.presentation.components.customComponents.CategoryChip
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenAdditionalPreferences() {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = koinViewModel<AdditionalPreferencesSettingsViewModel>()
    val state = viewModel.additionalPreferencesState.collectAsState()
    var isExpenseCategorySelectionVisible by remember { mutableStateOf(false) }
    var isIncomeCategorySelectionVisible by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.padding(start = 4.dp)) {
            Text(
                text = stringResource(R.string.additional),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Allow non-categorised expenses",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                    textAlign = TextAlign.Start
                )
                Switch(checked = state.value.nonCategorisedExpenses, onCheckedChange = {
                    coroutineScope.launch {
                        viewModel.setNonCategorisedExpenses(it)
                    }
                })
            }
            val selectedExpenseCategory =
                state.value.expenseCategories.find { state.value.groupingExpensesCategoryId == it.categoryId }
            if (state.value.nonCategorisedExpenses && !isExpenseCategorySelectionVisible && state.value.groupingExpensesCategoryId != GROUPING_CATEGORY_ID_DEFAULT && selectedExpenseCategory != null) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Show as",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    CategoryChip(
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                        category = selectedExpenseCategory,
                        isSelected = false,
                        onSelect = { isExpenseCategorySelectionVisible = true }
                    )
                }
            }
            if (state.value.nonCategorisedExpenses && (isExpenseCategorySelectionVisible || state.value.groupingExpensesCategoryId == GROUPING_CATEGORY_ID_DEFAULT)) {
                AdditionalPreferencesCategoriesGrid(
                    listOfCategories = state.value.expenseCategories,
                    selectedCategory = state.value.expenseCategories.find { it.categoryId == state.value.groupingExpensesCategoryId }) {
                    coroutineScope.launch {
                        viewModel.setGroupingExpensesCategoryId(it.categoryId)
                        isExpenseCategorySelectionVisible = false
                    }
                }
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Allow non-categorised incomes",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                    textAlign = TextAlign.Start
                )
                Switch(checked = state.value.nonCategorisedIncomes, onCheckedChange = {
                    coroutineScope.launch {
                        viewModel.setNonCategorisedIncomes(it)
                    }
                })
            }
            val selectedIncomeCategory =
                state.value.incomeCategories.find { state.value.groupingIncomeCategoryId == it.categoryId }
            if (state.value.nonCategorisedIncomes && !isIncomeCategorySelectionVisible && state.value.groupingIncomeCategoryId != GROUPING_CATEGORY_ID_DEFAULT && selectedIncomeCategory != null) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Show as",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    CategoryChip(
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                        category = selectedIncomeCategory,
                        isSelected = false,
                        onSelect = { isIncomeCategorySelectionVisible = true }
                    )
                }
            }

            if (state.value.nonCategorisedIncomes && (isIncomeCategorySelectionVisible || state.value.groupingIncomeCategoryId == GROUPING_CATEGORY_ID_DEFAULT)) {
                AdditionalPreferencesCategoriesGrid(
                    listOfCategories = state.value.incomeCategories,
                    selectedCategory = state.value.incomeCategories.find { it.categoryId == state.value.groupingIncomeCategoryId }) {
                    coroutineScope.launch {
                        viewModel.setGroupingIncomesCategoryId(it.categoryId)
                        isIncomeCategorySelectionVisible = false
                    }
                }
            }
        }
    }
}