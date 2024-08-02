package com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings

import com.savenko.track.R

sealed class CategoriesSettingsScreenViewOptions(val nameResId: Int) {
    data object ListView : CategoriesSettingsScreenViewOptions(nameResId = R.string.list_categories_settings_view_option)
    data object CardsView : CategoriesSettingsScreenViewOptions(nameResId = R.string.cards_categories_settings_view_option)
}