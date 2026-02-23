package com.savenko.track.shared.presentation.other.composableTypes.options

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource


sealed class IdeaSelectorTypes(val nameId: StringResource) {
    object ExpenseLimit : IdeaSelectorTypes(Res.string.expense_limit)
    object IncomePlans : IdeaSelectorTypes(Res.string.income_plan)
    object Savings : IdeaSelectorTypes(Res.string.saving)
}
