package com.savenko.track.presentation.screens.states.core.mainScreen

data class FinancialCardNotion(
    val financialsQuantity: Int,
    val financialSummary: Float,
    val periodAverage: Float
) {
    constructor(
        financialsQuantity: Int,
        financialSummary: Float
    ) : this(financialsQuantity, financialSummary, 0.0f)
}
