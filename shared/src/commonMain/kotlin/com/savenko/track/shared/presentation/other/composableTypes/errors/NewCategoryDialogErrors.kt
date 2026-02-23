@file:Suppress("SameParameterValue")

package com.savenko.track.shared.presentation.other.composableTypes.errors

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource


sealed class NewCategoryDialogErrors(val name: String, val error: StringResource) {
    data object CategoryAlreadyExist :
        NewCategoryDialogErrors(name = "CategoryAlreadyExist", error = Res.string.category_exist_warning_new_category_dialog)
}
