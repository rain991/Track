package com.savenko.track.presentation.screens.screenComponents.additional

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
//    var isContextMenuVisible by rememberSaveable {
//        mutableStateOf(false)
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        CategoriesScreenOptionsSelector(screenState = screenState, onAction = { onAction(it) })
        Spacer(modifier = Modifier.height(8.dp))
        if (screenState.viewOption is CategoriesSettingsScreenViewOptions.CardsView) {
            CategoriesSettingsCardViewContent(
                listOfExpenseCategories = screenState.listOfExpenseCategories,
                listOfIncomeCategories = screenState.listOfIncomeCategories,
                currentSelectedCategory = screenState.selectedCategory,
                onSelectCategory = { onAction(CategoriesSettingsScreenEvent.SetSelectedCategory(it)) }
            ) {
                onAction(CategoriesSettingsScreenEvent.DeleteCategory(it))
            }
        } else {
            CategoriesSettingsListViewContent()
        }
    }
}