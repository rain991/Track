package com.savenko.track.presentation.screens.screenComponents.additional

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.categoriesSettings.CategoriesScreenOptionsSelector
import com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.categoriesSettings.CategoriesSettingsCardViewContent
import com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.categoriesSettings.CategoriesSettingsListViewContent
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenEvent
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenState
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenViewOptions

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesSettingsScreenComponent(
    screenState: CategoriesSettingsScreenState,
    onAction: (CategoriesSettingsScreenEvent) -> Unit
) {
    var isSearchInputFieldVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
    )
    {
        CategoriesScreenOptionsSelector(screenState = screenState, onAction = { onAction(it) })
        Spacer(modifier = Modifier.height(8.dp))
        if (screenState.viewOption is CategoriesSettingsScreenViewOptions.CardsView) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp), horizontalArrangement = Arrangement.End
            ) {
                AnimatedContent(targetState = isSearchInputFieldVisible, label = "searchInputFieldAppearance") {
                    if (!it) {
                        IconButton(onClick = { isSearchInputFieldVisible = true }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = stringResource(R.string.find_your_category_CD)
                            )
                        }
                    } else {
                        CategoriesFilterInputField(nameFilter = screenState.nameFilter) {
                            onAction(CategoriesSettingsScreenEvent.SetFilteringText(it))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            CategoriesSettingsCardViewContent(
                listOfExpenseCategories = screenState.listOfExpenseCategories,
                listOfIncomeCategories = screenState.listOfIncomeCategories,
                currentSelectedCategory = screenState.selectedCategory,
                onSelectCategory = { onAction(CategoriesSettingsScreenEvent.SetSelectedCategory(it)) }
            ) {
                onAction(CategoriesSettingsScreenEvent.DeleteCategory(it))
            }
        } else {
            CategoriesSettingsListViewContent(
                listOfExpenseCategories = screenState.listOfExpenseCategories,
                listOfIncomeCategories = screenState.listOfIncomeCategories,
                nameFilter = screenState.nameFilter,
                setFilteringText = { onAction(CategoriesSettingsScreenEvent.SetFilteringText(it)) }
            ) {
                onAction(CategoriesSettingsScreenEvent.DeleteCategory(it))
            }
        }
    }
}

@Composable
fun CategoriesFilterInputField(nameFilter: String, onFilterChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp), horizontalArrangement = Arrangement.End
    ) {
        OutlinedTextField(
            value = nameFilter,
            onValueChange = {
                onFilterChange(it)
            },
            suffix = { Icons.Filled.Search },
            placeholder = { Text(text = stringResource(R.string.filter_categories_categories_screen)) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .scale(0.9f)
                .widthIn(1.dp, Dp.Infinity)
        )
    }
}

