package com.savenko.track.shared.presentation.screens.states.additional.settings.categoriesSettings

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource


/**
 * Specifies types of UI to be shown in [CategoriesSettingsScreen](com.savenko.track.presentation.screens.screenComponents.additional.CategoriesSettingsScreenComponentKt.CategoriesSettingsScreenComponent)
 */
sealed class CategoriesSettingsScreenViewOptions(val nameResId: StringResource) {
    data object ListView :
        CategoriesSettingsScreenViewOptions(nameResId = Res.string.list_categories_settings_view_option)

    data object CardsView :
        CategoriesSettingsScreenViewOptions(nameResId = Res.string.cards_categories_settings_view_option)
}
