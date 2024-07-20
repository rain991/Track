package com.savenko.track.presentation.other.composableTypes.errors

import com.savenko.track.R

sealed class NewCategoryDialogErrors(val name: String, val error: Int) {
    data object CategoryAlreadyExist :
        NewCategoryDialogErrors(name = "CategoryAlreadyExist", error = R.string.category_exist_warning_new_category_dialog)
}