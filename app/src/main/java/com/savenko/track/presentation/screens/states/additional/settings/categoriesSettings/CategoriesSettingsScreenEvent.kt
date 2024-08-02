package com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings

import com.savenko.track.domain.models.abstractLayer.CategoryEntity

sealed interface CategoriesSettingsScreenEvent {
    data class SetViewOption(val viewOption : CategoriesSettingsScreenViewOptions) : CategoriesSettingsScreenEvent
    data class SetFilteringText(val value : String) : CategoriesSettingsScreenEvent
    data class SetFilterOnlyCustomCategories(val value : Boolean) : CategoriesSettingsScreenEvent
    data class DeleteCategory(val category: CategoryEntity) : CategoriesSettingsScreenEvent
    data class SetSelectedCategory(val category : CategoryEntity?) : CategoriesSettingsScreenEvent
}