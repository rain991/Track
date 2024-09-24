package com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings

import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenEvent.DeleteCategory
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenEvent.SetViewOption

/**
 * Specifies actions to be performed in [CategoriesSettingsScreen](com.savenko.track.presentation.screens.additional.settingsScreens.CategoriesSettingsScreenKt.CategoriesSettingsScreen)
 *
 * [SetViewOption] sets type of view to be shown
 *
 * [DeleteCategory] objects specifies which category should be deleted
 *
 * @See CategoriesSettingsScreenViewOptions
 */
sealed interface CategoriesSettingsScreenEvent {
    data class SetViewOption(val viewOption : CategoriesSettingsScreenViewOptions) : CategoriesSettingsScreenEvent
    data class SetFilteringText(val value : String) : CategoriesSettingsScreenEvent
    data class SetFilterOnlyCustomCategories(val value : Boolean) : CategoriesSettingsScreenEvent
    data class DeleteCategory(val category: CategoryEntity) : CategoriesSettingsScreenEvent
    data class SetSelectedCategory(val category : CategoryEntity?) : CategoriesSettingsScreenEvent
}