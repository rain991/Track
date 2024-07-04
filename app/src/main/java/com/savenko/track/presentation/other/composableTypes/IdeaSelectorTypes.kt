package com.savenko.track.presentation.other.composableTypes

import com.savenko.track.R

sealed class IdeaSelectorTypes(val nameId: Int) {
    object ExpenseLimit : IdeaSelectorTypes(R.string.expense_limit)
    object IncomePlans : IdeaSelectorTypes(R.string.income_plan)
    object Savings : IdeaSelectorTypes(R.string.saving)
}