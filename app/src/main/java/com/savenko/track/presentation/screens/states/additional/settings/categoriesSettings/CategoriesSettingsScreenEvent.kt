package com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings

sealed interface CategoriesSettingsScreenEvent {
    data class SetViewOption(val viewOption : CategoriesSettingsScreenViewOptions) : CategoriesSettingsScreenEvent
    data class SetFilteringText(val value : String) : CategoriesSettingsScreenEvent
    data class SetFilterOnlyCustomCategories(val value : Boolean) : CategoriesSettingsScreenEvent
}