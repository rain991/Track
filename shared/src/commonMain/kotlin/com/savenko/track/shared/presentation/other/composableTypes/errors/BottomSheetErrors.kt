package com.savenko.track.shared.presentation.other.composableTypes.errors

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource


sealed class BottomSheetErrors(val name: String, val error: StringResource) {
    data object IncorrectInputValue :
        BottomSheetErrors(name = "IncorrectInputValue", error = Res.string.incorrect_input_value_BS_error)

    data object CategoryNotSelected :
        BottomSheetErrors(name = "CategoryNotSelected", error = Res.string.select_financial_category_BS_error)

    data object DateNotSelected : BottomSheetErrors(name = "DateNotSelected", error = Res.string.select_correct_date_error)

    data object ExpenseGroupingCategoryIsNotSelected : BottomSheetErrors(
        name = "ExpenseGroupingCategoryIsNotSelected",
        error = Res.string.non_categorised_expenses_error_BS
    )

    data object IncomeGroupingCategoryIsNotSelected : BottomSheetErrors(
        name = "IncomeGroupingCategoryIsNotSelected",
        error = Res.string.non_categorised_incomes_error_BS
    )
}
