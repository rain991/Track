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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.R
import com.savenko.track.data.viewmodels.settingsScreen.additionalPreferences.AdditionalPreferencesSettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenAdditionalPreferences() {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = koinViewModel<AdditionalPreferencesSettingsViewModel>()
    val state = viewModel.additionalPreferencesState.collectAsState()
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = stringResource(R.string.additional),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Allow non-categorised expenses")
                Switch(checked = state.value.nonCategorisedExpenses, onCheckedChange = {
                    coroutineScope.launch {
                        viewModel.setNonCategorisedExpenses(it)
                    }
                })
            }
            if (state.value.nonCategorisedExpenses) {
                AdditionalPreferencesCategoriesGrid(
                    listOfCategories = state.value.expenseCategories,
                    selectedCategory = state.value.expenseCategories.find { it.categoryId == state.value.groupingExpensesCategoryId }) {
                    coroutineScope.launch {
                        viewModel.setGroupingExpensesCategoryId(it.categoryId)
                    }
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Allow non-categorised incomes")
                Switch(checked = state.value.nonCategorisedIncomes, onCheckedChange = {
                    coroutineScope.launch {
                        viewModel.setNonCategorisedIncomes(it)
                    }
                })
            }
            if (state.value.nonCategorisedExpenses) {
                AdditionalPreferencesCategoriesGrid(
                    listOfCategories = state.value.incomeCategories,
                    selectedCategory = state.value.incomeCategories.find { it.categoryId == state.value.groupingIncomeCategoryId }) {
                    coroutineScope.launch {
                        viewModel.setGroupingIncomesCategoryId(it.categoryId)
                    }
                }
            }
        }
    }
}