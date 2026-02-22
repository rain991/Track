package com.savenko.track.shared.presentation.screens.states.additional.settings.categoriesSettings

import com.savenko.track.shared.presentation.other.composableTypes.errors.NewCategoryDialogErrors

/**
 * New category dialog state
 *
 * @property isDialogVisible dialog visibility
 * @property dialogError current dialog error
 */
data class NewCategoryDialogState(
    val isDialogVisible : Boolean,
    val dialogError: NewCategoryDialogErrors?
)
