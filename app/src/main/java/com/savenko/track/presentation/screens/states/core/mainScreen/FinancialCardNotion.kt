package com.savenko.track.presentation.screens.states.core.mainScreen

/**
 * Financial card notion
 *
 * This notion data depends on chosen period
 *
 * @property financialsQuantity depends on UI requirements, represents quantity of financials with same category in time period
 * @property financialSummary depends on UI requirements, represents summary of financials values with same category in time period
 * @property periodAverage selected period depends on UI requirements, represents period average
 */
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
