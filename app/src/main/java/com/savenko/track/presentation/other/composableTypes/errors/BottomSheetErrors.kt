package com.savenko.track.presentation.other.composableTypes.errors

import com.savenko.track.R

sealed class BottomSheetErrors(val name: String, val error: Int) {
    data object IncorrectInputValue : BottomSheetErrors(name = "IncorrectInputValue", error = R.string.incorrect_input_value_BS_error)
    data object CategoryNotSelected : BottomSheetErrors(name = "CategoryNotSelected", error = R.string.select_financial_category_BS_error)
}