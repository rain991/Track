package com.example.track.presentation.states.componentRelated

import com.example.track.R

sealed class IdeaSelectorTypes(val nameId: Int) {
    object ExpenseLimit : IdeaSelectorTypes(R.string.expense_limit)
    object IncomePlans : IdeaSelectorTypes(R.string.income_plan)
    object Savings : IdeaSelectorTypes(R.string.saving)
}