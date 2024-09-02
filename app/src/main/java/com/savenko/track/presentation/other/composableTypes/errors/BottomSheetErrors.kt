package com.savenko.track.presentation.other.composableTypes.errors

import com.savenko.track.R

sealed class BottomSheetErrors(val name: String, val error: Int) {
    data object IncorrectInputValue :
        BottomSheetErrors(name = "IncorrectInputValue", error = R.string.incorrect_input_value_BS_error)

    data object CategoryNotSelected :
        BottomSheetErrors(name = "CategoryNotSelected", error = R.string.select_financial_category_BS_error)

    data object DateNotSelected : BottomSheetErrors(name = "DateNotSelected", error = R.string.select_correct_date_error)

    data object ExpenseGroupingCategoryIsNotSelected : BottomSheetErrors(
        name = "ExpenseGroupingCategoryIsNotSelected",
        error = R.string.non_categorised_expenses_error_BS
    )

    data object IncomeGroupingCategoryIsNotSelected : BottomSheetErrors(
        name = "IncomeGroupingCategoryIsNotSelected",
        error = R.string.non_categorised_incomes_error_BS
    )
}
