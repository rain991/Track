package com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings

import com.savenko.track.presentation.other.composableTypes.errors.NewCategoryDialogErrors

data class NewCategoryDialogState(
    val isDialogVisible : Boolean,
    val dialogErrors: NewCategoryDialogErrors?
)
