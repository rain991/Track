package com.savenko.track.shared.domain.models.abstractLayer

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource


sealed class CategoriesTypes(val nameStringRes: StringResource) {
    data object IncomeCategory : CategoriesTypes(nameStringRes = Res.string.income_category)
    data object ExpenseCategory : CategoriesTypes(nameStringRes = Res.string.expense_category)
}
